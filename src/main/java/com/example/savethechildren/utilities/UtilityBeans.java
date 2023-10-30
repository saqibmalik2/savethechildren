package com.example.savethechildren.utilities;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.savethechildren.dto.DonorDTO;
import com.example.savethechildren.model.Donor;


@Configuration
public class UtilityBeans {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DonorDTO.class, Donor.class);
		return modelMapper;
	}

}
