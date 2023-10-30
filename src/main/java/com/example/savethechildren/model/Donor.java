package com.example.savethechildren.model;

import java.time.LocalDate;


import com.example.savethechildren.utilities.AttributeEncryptor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 
 * Entity object for the Donor resource. Note the convertors which handle the encyrption/decryption to/from the DB.
 * 
 */


@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table
public class Donor {
	@Id
	@GeneratedValue
	@Column
	private long membershipId;
	@Column
	@Convert(converter = AttributeEncryptor.class)
	private String firstName;
	@Column
	@Convert(converter = AttributeEncryptor.class)
	private String surname;
	@Column
	@Convert(converter = AttributeEncryptor.class)
	private String middleName1;
	@Column
	@Convert(converter = AttributeEncryptor.class)
	private String middleName2;
	@Column
	@Convert(converter = AttributeEncryptor.class)
	private String middleName3;
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = AttributeEncryptor.class)
	private Nationality nationality;
	@Column
	@Convert(converter = AttributeEncryptor.class)
	private String address;
	@Column(columnDefinition = "DATE")
	private LocalDate dateOfBirth;
}
