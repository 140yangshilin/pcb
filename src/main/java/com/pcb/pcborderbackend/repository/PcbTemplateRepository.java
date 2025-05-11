package com.pcb.pcborderbackend.repository;

import com.pcb.pcborderbackend.model.PcbTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PcbTemplateRepository extends MongoRepository<PcbTemplate, String> {
    boolean existsByName(String name);
    void deleteById(String id);

}
