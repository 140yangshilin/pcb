package com.pcb.pcborderbackend.controller;

import com.pcb.pcborderbackend.service.CombinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/combinations")
public class CombinationController {

    @Autowired
    private CombinationService combinationService;

    /**
     * 前端传入字段值列表，后端进行笛卡尔积生成所有合法组合并保存
     * 请求格式：{
     *   "template_id": "xxx",
     *   "字段A": ["a1", "a2"],
     *   "字段B": ["b1"],
     *   "字段C": ["c1", "c2"]
     * }
     */
    @PostMapping("/generate")
    public ResponseEntity<?> generateCombinations(@RequestBody Map<String, Object> request) {
        try {
            combinationService.generateAndSaveCombinations(request);
            return ResponseEntity.ok("组合生成并保存成功");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("服务端错误: " + e.getMessage());
        }
    }

    @PostMapping("/getById")
    public ResponseEntity<?> getCombinationById(@RequestBody Map<String, String> request) {
        String templateName = request.get("templateName");
        String id = request.get("id");

        try {
            Map<String, Object> rule = combinationService.getCombinationById(templateName, id);
            return ResponseEntity.ok(rule);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("查询失败: " + e.getMessage());
        }
    }


    /**
     * 查询接口：根据部分字段值，返回其余字段的合法选项合集
     * 请求体要求包含 template_name 和若干字段键值对
     */
    @PostMapping("/query")
    public ResponseEntity<?> queryCombinations(@RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> result = combinationService.queryValidOptions(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("查询失败: " + e.getMessage());
        }
    }


    /**
     * 删除某个组合规则（根据集合名和 _id）
     */
    @DeleteMapping("/delete/{templateName}/{id}")
    public ResponseEntity<?> deleteCombinationById(
            @PathVariable("templateName") String templateName,
            @PathVariable("id") String id
    ) {
        try {
            combinationService.deleteCombinationById(templateName, id);
            return ResponseEntity.ok("规则已删除");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }

    /**
     * 删除某个组合规则（根据集合名和组合）
     */
    @DeleteMapping("/delete-by-combination")
    public ResponseEntity<?> deleteCombinationsByFields(@RequestBody List<Map<String, Object>> combinationList) {
        try {
            combinationService.deleteCombinationsByFieldList(combinationList);
            return ResponseEntity.ok("批量规则已删除");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败: " + e.getMessage());
        }
    }
}
