package com.pcb.pcborderbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CombinationService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void generateAndSaveCombinations(Map<String, Object> request) {
        String collectionName = request.get("template_name").toString();

        Query checkTemplate = new Query(Criteria.where("name").is(collectionName));
        boolean exists = mongoTemplate.exists(checkTemplate, "pcb_templates");
        if (!exists) {
            throw new IllegalArgumentException("模板名称 " + collectionName + " 不存在于 pcb_templates 集合中");
        }

        Map<String, List<String>> fields = new HashMap<>();
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            if (entry.getKey().equals("template_name")) continue;
            Object value = entry.getValue();
            if (value instanceof List<?> list) {
                List<String> stringList = list.stream().map(Object::toString).collect(Collectors.toList());
                fields.put(entry.getKey(), stringList);
            }
        }

        List<Map<String, String>> combinations = cartesianProduct(fields);

        for (Map<String, String> combination : combinations) {
            combination.put("template_name", collectionName);
            mongoTemplate.insert(combination, collectionName);
        }
    }

    public Map<String, Object> getCombinationById(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        Map result = mongoTemplate.findOne(query, Map.class, collectionName);
        if (result == null) {
            throw new RuntimeException("未找到对应的组合规则");
        }
        return result;
    }


    public Map<String, Object> queryValidOptions(Map<String, Object> request) {
        String collectionName = request.get("template_name").toString();

        Query query = new Query();
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            if (!entry.getKey().equals("template_name")) {
                query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue().toString()));
            }
        }

        List<Map> matched = mongoTemplate.find(query, Map.class, collectionName);

        Map<String, Set<String>> fieldOptions = new HashMap<>();
        for (Map doc : matched) {
            for (Object keyObj : doc.keySet()) {
                String key = keyObj.toString();
                if (key.equals("_id") || key.equals("template_name")) continue;
                String value = doc.get(key).toString();
                fieldOptions.computeIfAbsent(key, k -> new HashSet<>()).add(value);
            }
        }

        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : fieldOptions.entrySet()) {
            result.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return result;
    }

    public void deleteCombinationById(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.remove(query, collectionName);
    }

    public void deleteCombinationsByFieldList(List<Map<String, Object>> combinations) {
        for (Map<String, Object> request : combinations) {
            String collectionName = request.get("template_name").toString();
            Query query = new Query();
            for (Map.Entry<String, Object> entry : request.entrySet()) {
                if (!entry.getKey().equals("template_name")) {
                    query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
                }
            }
            mongoTemplate.remove(query, collectionName);
        }
    }

    private List<Map<String, String>> cartesianProduct(Map<String, List<String>> input) {
        List<Map<String, String>> result = new ArrayList<>();
        cartesianHelper(input, new LinkedHashMap<>(), result);
        return result;
    }

    private void cartesianHelper(Map<String, List<String>> input,
                                 Map<String, String> current,
                                 List<Map<String, String>> result) {
        if (current.size() == input.size()) {
            result.add(new LinkedHashMap<>(current));
            return;
        }

        String nextKey = new ArrayList<>(input.keySet()).get(current.size());
        for (String value : input.get(nextKey)) {
            current.put(nextKey, value);
            cartesianHelper(input, current, result);
            current.remove(nextKey);
        }
    }
}
