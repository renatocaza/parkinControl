package com.api.parkingcontrol.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {
	
	@Autowired
	ParkingSpotRepository parkingSpostRepository;
	
	@Transactional
	public ResponseEntity<Object> save(ParkingSpotDto parkingSpotDto) {
		
		
		
		if(existsBylicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plante Car is already in use!");
		}
		if(existsBySpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot Number is already in use!");
		}
		if(existsByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking apartament or block is already in use!");
		}
		
		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		parkingSpotModel = parkingSpostRepository.save(parkingSpotModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotModel);
	}
	
	public boolean existsBylicensePlateCar(String licensePlateCar) {
		return parkingSpostRepository.existsByLicensePlateCar(licensePlateCar);
	}
	
	public boolean existsBySpotNumber(String spotNumber) {
		return parkingSpostRepository.existsByParkingSpotNumber(spotNumber);
	}
	
	public boolean existsByApartamentAndBlock(String apartament, String block) {
		return parkingSpostRepository.existsByApartamentAndBlock(apartament, block);
	}
	
}
