package com.pcb.pcborderbackend.repository;

import com.pcb.pcborderbackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // 查：按用户名查
    Optional<Admin> findByUsername(String username);
}
