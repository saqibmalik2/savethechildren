package com.example.savethechildren.controllers;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.savethechildren.dto.DonorDTO;
import com.example.savethechildren.services.DonorsService;


/**
 * The main Rest controller class that handles incoming api requests.
 */

@RestController
@RequestMapping("/api/v1/donors")
public class DonorController {
	
	@Autowired
	private DonorsService donorsService;
	
	@PostMapping
	public ResponseEntity<String> createNewDonor(@Valid @RequestBody DonorDTO donorDTO, HttpServletResponse response){
		String nameAndMembershipNo = donorsService.saveDonor(donorDTO);
		return new ResponseEntity<String>(nameAndMembershipNo, HttpStatus.CREATED);
	}
	
	@GetMapping("/{membershipId}")
	public DonorDTO getDonorDetailsByMembershipId(@PathVariable Long membershipId) {
		return donorsService.getDonorByMembershipId(membershipId);
	}
	
	@GetMapping
	public List<DonorDTO> getAllDonorDetails() {
		return donorsService.retrieveDonors();
	}
	
//	@DeleteMapping("/officers/{officerForceId}")
//	public long deleteOfficer(@PathVariable String officerForceId) {
//		return officerService.deleteOfficerByForceId(officerForceId);
//	}

			
}
