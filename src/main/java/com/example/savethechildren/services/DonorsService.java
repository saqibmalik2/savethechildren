package com.example.savethechildren.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.savethechildren.dto.DonorDTO;
import com.example.savethechildren.exceptions.DonorNotFoundException;
import com.example.savethechildren.model.Donor;
import com.example.savethechildren.repositories.DonorsRepository;

/**
 * 
 * Simple Donor service layer. Handles the DTO - Entity object conversions via the ModelMapper class.
 * 
 */

@Service
public class DonorsService {
	
	@Autowired 	
	private DonorsRepository donorsRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Transactional
	public String saveDonor(DonorDTO donorDTO) {
		Donor donor = donorsRepo.save(convertToEntity(donorDTO));
		String nameAndMembershipNo = donor.getFirstName() + donor.getMembershipId();
		return nameAndMembershipNo;
	}
	
	public DonorDTO getDonorByMembershipId(Long membershipId) {
		Optional<Donor> donor = donorsRepo.findByMembershipId(membershipId);
		if (donor.isPresent()) return convertFromEntity(donor.get());
		
		throw new DonorNotFoundException(membershipId);
	}
	
	public List<DonorDTO> retrieveDonors() {
		List<Donor> listOfDonorsEntity = donorsRepo.retrieveDonors();
		if (listOfDonorsEntity != null) {
			List<DonorDTO> listOfDonorsDto = new ArrayList<>();
			for (Donor donor:listOfDonorsEntity) {
				listOfDonorsDto.add(convertFromEntity(donor));
			}
			return listOfDonorsDto;
		}
		else {
			return null;
		}
	}
	
//	@Transactional
//	public long deleteOfficerByForceId(String officerForceId) {
//		return officerRepo.deleteByOfficerForceId(officerForceId);
//	}
	
//	public DonorDTO getOfficerByIdAndForceName(String forceName, String officerForceId) {
//		Officer officer = officerRepo.findByForceNameAndOfficerForceId(Nationality.valueOf(forceName), officerForceId);
//		if (officer != null) {
//			return convertFromEntity(officer);
//		}
//		else {
//			throw new OfficerNotFoundException(forceName+officerForceId);
//		}
//	}
	
//	public List<DonorDTO> getAllOfficers() {
//		List<Officer> officers = officerRepo.retrieveOfficers();
//		return officers.stream().map(officer -> convertFromEntity(officer)).collect(Collectors.toList());
//	}
//	
//	
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public String updateOfficer(DonorDTO officerDTO) {
//		Officer officer = officerRepo.findByForceNameAndOfficerForceId(Nationality.valueOf(officerDTO.getForceName()), officerDTO.getOfficerForceId());
//		modelMapper.map(officerDTO, officer);
//		officerRepo.save(officer);
//		return officer.getFirstName() + ":" + officer.getOfficerForceId() + " has been updated.";
//	}
	
	private Donor convertToEntity(DonorDTO donorDto) {
		Donor donor = modelMapper.map(donorDto, Donor.class);
		return donor;
	}
	
	private DonorDTO convertFromEntity(Donor donor) {
		DonorDTO donorDTO = modelMapper.map(donor, DonorDTO.class);
		return donorDTO;
	}

	
}
