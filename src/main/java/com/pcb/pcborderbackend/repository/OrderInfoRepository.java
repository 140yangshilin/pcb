package com.pcb.pcborderbackend.repository;

import com.pcb.pcborderbackend.model.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

    // 查：某用户的所有订单
    List<OrderInfo> findByUserId(Long userId);

    // 删：物理删除
    void deleteById(Long id);
}
