package com.pcb.pcborderbackend.controller;

import com.pcb.pcborderbackend.model.PcbTemplate;
import com.pcb.pcborderbackend.service.PcbTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/template")
public class PcbTemplateController {

    @Autowired
    private PcbTemplateService templateService;
    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping("/create")
    public ResponseEntity<?> createTemplate(@RequestBody PcbTemplate template) {
        System.out.println("request for create");
        try {
            return ResponseEntity.ok(templateService.create(template));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTemplates() {
        System.out.println("request for getAllTemplates");
        return ResponseEntity.ok(templateService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PcbTemplate> getTemplate(@PathVariable String id) {
        System.out.println("request for getTemplate by id and id is "+id);
        return templateService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTemplate(@RequestBody PcbTemplate updated) {
        return templateService.updateTemplateByIdOrName(updated)
                .<ResponseEntity<?>>map(template -> {
                    String templateName = template.getName();
                    if (mongoTemplate.collectionExists(templateName)) {
                        mongoTemplate.dropCollection(templateName);
                    }
                    return ResponseEntity.ok(template);
                })
                .orElseGet(() -> ResponseEntity.status(400).body("未找到匹配模板"));
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemplate(@PathVariable String id) {
        boolean deleted = templateService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("模版已删除");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
