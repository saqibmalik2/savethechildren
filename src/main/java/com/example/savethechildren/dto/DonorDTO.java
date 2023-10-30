package com.example.savethechildren.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  A DTO class to shield exposure of the Entity class from the client.
 *  
 */


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DonorDTO {
	
	private long membershipId;
	@NotNull
	private String firstName;
	@NotNull
	private String surname;
	private String middleName1;
	private String middleName2;
	private String middleName3;
	@NotNull
	private String nationality;
	@NotNull
	private String address;
	@NotNull
	private LocalDate dateOfBirth;

}
