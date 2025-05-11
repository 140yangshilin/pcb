package com.pcb.pcborderbackend.repository;

import com.pcb.pcborderbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户（用于登录）
     */
    Optional<User> findByUsername(String username);

    /**
     * 判断邮箱是否已注册
     */
    boolean existsByEmail(String email);

    /**
     * 查询所有未逻辑删除的用户
     */

    List<User> findAllByDeletedFalse();

    Page<User> findAllByDeletedFalse(Pageable pageable);

}
