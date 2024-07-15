package com.DataSense.DataSenseApi.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DataSense.DataSenseApi.model.EquipmentData;

/**
 * Repository interface for EquipmentData entity.
 */
public interface EquipmentDataRepository extends JpaRepository<EquipmentData, Long> {
    /**
     * Search for an EquipmentData entry by its equipmentId.
     *
     * @param equipmentId the ID of the equipment
     * @return the EquipmentData entry
     */
	Optional<EquipmentData> findByEquipmentId(String equipmentId);
    List<EquipmentData> findAllByEquipmentIdAndTimestampBetween(String equipmentId, OffsetDateTime startDate, OffsetDateTime endDate);
}
