package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Order;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface OrderDao extends JpaRepository<Order, Long> {
    Order findById(Integer id);
    Page<Order> findAllBySeller(User seller, Pageable pageable);
    Page<Order> findAllBySellerAndTimestampBetween(User seller, Timestamp startTime, Timestamp endTime, Pageable pageable);
    Page<Order> findAllBySellerAndTimestampBefore(User seller, Timestamp startTime, Pageable pageable);
    Page<Order> findAllBySellerAndTimestampAfter(User seller, Timestamp endTime, Pageable pageable);
    Page<Order> findAllByBuyer(User buyer, Pageable pageable);
    Page<Order> findAllByBuyerAndTimestampBetween(User buyer, Timestamp startTime, Timestamp endTime, Pageable pageable);
    Page<Order> findAllByBuyerAndTimestampBefore(User buyer, Timestamp startTime, Pageable pageable);
    Page<Order> findAllByBuyerAndTimestampAfter(User buyer, Timestamp endTime, Pageable pageable);
}

