package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterRepository extends JpaRepository<com.example.demo.models.Master, Long> {
    List<com.example.demo.models.Master> findByTitle(String title);
}