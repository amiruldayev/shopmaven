package com.example.shopmaven.repo;

import com.example.shopmaven.Orders;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
