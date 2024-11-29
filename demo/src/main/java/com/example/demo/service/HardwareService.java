package com.example.demo.service;

import com.example.demo.entity.Hardware;
import com.example.demo.repository.HardwareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareService {
    private final HardwareRepository hardwareRepository;

    public HardwareService(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    public List<Hardware> getHardwareByType(String type) {
        return hardwareRepository.findByType(type);
    }
}
