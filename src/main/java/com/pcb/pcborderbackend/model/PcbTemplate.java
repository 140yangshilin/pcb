package com.pcb.pcborderbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document("pcb_templates")
@Data
public class PcbTemplate {
    @Id
    private String id;
    private String name;
    private String description;
    private List<Category> categories;
    private LocalDateTime createdAt;

    @Data
    public static class Category {
        private String name;
        private List<Field> fields;
    }

    @Data
    public static class Field {
        private String key;
        private String type; // checkbox, radio, input
        private List<String> options;
        private String defaultValue;
        private Integer order; // 字段排序序号，越小越靠前
    }
}
