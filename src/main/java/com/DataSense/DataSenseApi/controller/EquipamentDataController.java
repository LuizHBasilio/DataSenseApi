package com.DataSense.DataSenseApi.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DataSense.DataSenseApi.model.EquipmentData;
import com.DataSense.DataSenseApi.repository.EquipamentDataRepository;

/**
 * Equipment data controller.
 */
@RestController
@RequestMapping("/datasense/api/v1/equipments")
public class EquipamentDataController {

	@Autowired
	private EquipamentDataRepository equipamentDataRepository;

    /**
     * Get all equipment data entries.
     *
     * @return list of all equipment data
     */
	@GetMapping
	public List<EquipmentData> getAllEquipmentData() {
		return equipamentDataRepository.findAll();
	}

	/**
     * Get a specific equipment data entry by ID.
     *
     * @param id the ID of the equipment data
     * @return the equipment data entry or a 404 status if not found
     */
	@GetMapping("/{id}")
	public ResponseEntity<?> getEquipmentDataById(@PathVariable Long id) {

		EquipmentData equipmentData = equipamentDataRepository.findById(id).orElse(null);

		if (equipmentData != null) {
			return ResponseEntity.ok(equipmentData);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment with id " + id + " was not found");
		}
	}
	
	/**
     * Receive and save a new equipment data entry.
     *
     * @param equipmentData the equipment data to save
     * @return the saved equipment data
     */
	@PostMapping
    public EquipmentData receiveEquipamentData(@RequestBody EquipmentData equipmentData) {
        return equipamentDataRepository.save(equipmentData);
    }

	/**
     * Receive and process a CSV file containing equipment data.
     *
     * @param file the CSV file to process
     * @return a response indicating success or failure
     */
	@PostMapping("/upload")
	public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return new ResponseEntity<>("The file is empty", HttpStatus.BAD_REQUEST);
		}

		try (InputStream inputStream = file.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

			String line;
			List<EquipmentData> equipmentDataList = new ArrayList<>();
			DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

			reader.readLine();

			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length == 3) {
					String equipmentId = values[0].trim();
					OffsetDateTime timestamp = OffsetDateTime.parse(values[1].trim(), formatter);
					Double value = Double.parseDouble(values[2].trim());
					
					EquipmentData equipmentData = new EquipmentData();
					equipmentData.setEquipmentId(equipmentId);
					equipmentData.setTimestamp(timestamp);
					equipmentData.setValue(value);

					equipmentDataList.add(equipmentData);
				}
			}

			equipamentDataRepository.saveAll(equipmentDataList);

			return new ResponseEntity<>("CSV file was been processed", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error in CSV file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/average")
    public Map<String, Double> getAverageValues(
            @RequestParam("period") String period,
            @RequestParam("equipmentId") String equipmentId) {

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime startDate = now;

        switch (period.toLowerCase()) {
            case "24h":
                startDate = now.minusHours(24);
                break;
            case "48h":
                startDate = now.minusHours(48);
                break;
            case "1w":
                startDate = now.minusWeeks(1);
                break;
            case "1m":
                startDate = now.minusMonths(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        List<EquipmentData> data = equipamentDataRepository.findAllByEquipmentIdAndTimestampBetween(equipmentId, startDate, now);
        double average = data.stream().mapToDouble(EquipmentData::getValue).average().orElse(0.0);

        Map<String, Double> result = new HashMap<>();
        result.put("average", average);

        return result;
    }
}
