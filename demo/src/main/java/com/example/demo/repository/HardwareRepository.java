package com.example.demo.repository;

import com.example.demo.entity.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HardwareRepository extends JpaRepository<Hardware, Long> {
    List<Hardware> findByType(String type);
}
