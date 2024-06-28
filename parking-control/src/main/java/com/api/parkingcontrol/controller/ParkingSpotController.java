package com.api.parkingcontrol.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //Permite que seja acessado de qualquer fonte
@RequestMapping("/parking-spot") 
public class ParkingSpotController {

	final ParkingSpotService parkingSpotService;

	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	} 
	
	// O @Valid é importante ser colocado para as "validações"(@NotBlank) do DTO serem executadas. 
	//Como o @RequestMapping esta nivel aplicação quando chamado metodo post ele assume.
	
	@PostMapping 
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){	
		return parkingSpotService.save(parkingSpotDto);
	}
}
