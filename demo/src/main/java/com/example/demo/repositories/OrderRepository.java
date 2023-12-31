package com.example.demo.repositories;


import com.example.transportcompany.models.Contract;
import com.example.transportcompany.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByUser(User user);
}