package com.example.demo.controller;

import com.example.demo.entity.Hardware;
import com.example.demo.service.HardwareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hardware")
public class HardwareController {
    private final HardwareService hardwareService;

    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping("/{type}")
    public List<Hardware> getHardwareByType(@PathVariable String type) {
        return hardwareService.getHardwareByType(type);
    }
}
