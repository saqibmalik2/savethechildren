package com.example.savethechildren;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.savethechildren.dto.DonorDTO;
import com.example.savethechildren.model.Donor;
import com.example.savethechildren.services.DonorsService;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Import(value = {DonorsService.class, ModelMapper.class})
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EncryptionTest {
	
    private static final String FIRST_NAME = "Saqib";
    private static final String SURNAME = "Malik";
    private static final String MIDDLE_NAME1 = "Naveed";
    private static final String NATIONALITY = "UnitedKingdom";
    private static final String ADDRESS = "528 Kents Hill Road North, Benfleet, Essex SS7 4AA";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1977, 8, 15);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DonorsService donorService;
    
    
    /**
     * Create a Donor row in the database.
     */
    @BeforeAll
    public void setUp() {
    	DonorDTO donarDto = DonorDTO.builder().firstName(FIRST_NAME).surname(SURNAME)
    			.middleName1(MIDDLE_NAME1).nationality(NATIONALITY).address(ADDRESS)
    			.dateOfBirth(DATE_OF_BIRTH).build();
    	String result = donorService.saveDonor(donarDto);
    	log.info(result);
    }
    
    
    /**
     * Test to make sure the decryption converters on the entity object work. When we retrieve the row it should be decrypted
     * or the comparison with the plain text String FIRST_NAME will fail.
     */
    
    @Test
    void readDecrypted() {
    	DonorDTO donarDtoDecrypted = donorService.getDonorByMembershipId(1L);
    	assertThat(donarDtoDecrypted.getFirstName()).isEqualTo(FIRST_NAME);
    }
    
    
    /**
     * Testing to see if the data is actually stored in encrypted form. Use jdbcTemplate to read the values directly
     * from the DB (bypassing the decryption converters on the Donor entity object). Compare to the plaintext String
     * FIRST_NAME and if they don't match then we can be assured the text is encrypted.
     */
    @Test
    void readEncrypted() {
    	Donor donor = jdbcTemplate.queryForObject(
    			"SELECT * FROM donor WHERE membership_id = ?",
	    		(resultSet, i) -> {
	    				Donor result = new Donor();
	    				result.setMembershipId(resultSet.getLong("membership_id"));
	    				result.setFirstName(resultSet.getString("first_name"));
	    				result.setAddress(resultSet.getString("address"));
	    				return result;
	    			},
    				1L
    			);
    	assertThat(donor.getFirstName()).isNotEqualTo(FIRST_NAME);
    	log.info("Encrypted first name in DB is {}", donor.getFirstName());
    }
    


	
	

}
