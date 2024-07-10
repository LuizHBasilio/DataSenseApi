package com.DataSense.DataSenseApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping
    public List<EquipmentData> getAllEquipmentData() {
        return equipamentDataRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentDataById(@PathVariable Long id) {
		
        EquipmentData equipmentData = equipamentDataRepository.findById(id).orElse(null);
        
        if (equipmentData != null) {
            return ResponseEntity.ok(equipmentData);
        } else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment with id " + id + " was not found");
        }
    }

    @PostMapping
    public EquipmentData receiveEquipamentData(@RequestBody EquipmentData equipmentData) {
        return equipamentDataRepository.save(equipmentData);
    }
}
