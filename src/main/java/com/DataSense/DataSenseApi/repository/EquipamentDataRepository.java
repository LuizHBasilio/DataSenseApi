package com.DataSense.DataSenseApi.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DataSense.DataSenseApi.model.EquipmentData;

public interface EquipamentDataRepository extends JpaRepository<EquipmentData, Long> {
	Optional<EquipmentData> findByEquipmentId(String equipmentId);
    List<EquipmentData> findAllByEquipmentIdAndTimestampBetween(String equipmentId, OffsetDateTime startDate, OffsetDateTime endDate);
}
