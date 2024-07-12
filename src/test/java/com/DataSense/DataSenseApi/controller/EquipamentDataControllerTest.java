package com.DataSense.DataSenseApi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.DataSense.DataSenseApi.model.EquipmentData;
import com.DataSense.DataSenseApi.repository.EquipamentDataRepository;


public class EquipamentDataControllerTest {

	@Mock
    private EquipamentDataRepository equipamentDataRepository;

    @InjectMocks
    private EquipamentDataController equipamentDataController;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testgetEquipmentDataNotFound() {
		ResponseEntity<?> response = equipamentDataController.getEquipmentDataById(999999L);
		assertEquals(404, response.getStatusCodeValue());
		assertEquals("Equipment with id 999999 was not found", response.getBody());
	}
	
	@Test
    public void testGetEquipmentDataById() {
        EquipmentData equipmentData = new EquipmentData(1L, "EQ-12495", OffsetDateTime.parse("2023-02-15T01:30:00.000-05:00"), 78.42 );
        
        when(equipamentDataRepository.findById(1L)).thenReturn(Optional.of(equipmentData));

        ResponseEntity<?> response = equipamentDataController.getEquipmentDataById(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(equipmentData);
    }
	
	@Test
    public void testReceiveEquipamentData() {
		EquipmentData equipmentData = new EquipmentData(1L, "EQ-12495", OffsetDateTime.parse("2023-02-15T01:30:00.000-05:00"), 78.42 );;

        when(equipamentDataRepository.save(equipmentData)).thenReturn(equipmentData);

        EquipmentData response = equipamentDataController.receiveEquipamentData(equipmentData);

        assertThat(response).isEqualTo(equipmentData);
    }

}
