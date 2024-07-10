package com.DataSense.DataSenseApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DataSense.DataSenseApi.model.EquipmentData;
import com.DataSense.DataSenseApi.repository.EquipamentDataRepository;

@RestController
@RequestMapping("/datasense/api/v1/equipments")
public class EquipamentDataController {

	@Autowired
    private EquipamentDataRepository equipamentDataRepository;

    @PostMapping
    public EquipmentData receiveEquipamentData(@RequestBody EquipmentData equipmentData) {
        return equipamentDataRepository.save(equipmentData);
    }
}
