package com.DataSense.DataSenseApi.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EquipmentData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String equipmentId;
	private OffsetDateTime timestamp;
	private double value;
	
	public EquipmentData() {
	    }
		
	public EquipmentData(long id, String equipmentId, OffsetDateTime timestamp, double value) {
		this.id = id;
		this.equipmentId = equipmentId;
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEquipmentId() {
		return equipmentId;
	}
	
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public OffsetDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}	
}
