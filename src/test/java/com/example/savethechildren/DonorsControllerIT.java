package com.example.savethechildren;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.savethechildren.dto.DonorDTO;
import com.example.savethechildren.services.DonorsService;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * Integration tests for the Donor Controller.
 * 
 */


@SpringBootTest(classes = {SavethechildrenApplication.class, DonorDTO.class, DonorsService.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
@DisplayName("Donors Controller Test")
public class DonorsControllerIT {

	private static final String FIRST_NAME = "Saqib";
    private static final String SURNAME = "Malik";
    private static final String MIDDLE_NAME1 = "Naveed";
    private static final String NATIONALITY = "UnitedKingdom";
    private static final String ADDRESS = "528 Kents Hill Road North, Benfleet, Essex SS7 4AA";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1977, 8, 15);
    
	@LocalServerPort
	int randomServerPort;
	
	@Autowired
	DonorsService donorService;
	
	/**
	 *  Tests the creation of a new donor resource. 
	 * 
	 * @throws URISyntaxException
	 */
	
	@Test
	public void testCreateDonor() throws URISyntaxException {
			
			final String baseUrl = "http://localhost:"+randomServerPort+"/api/v1/donors";
			
			DonorDTO donarDto = DonorDTO.builder().firstName(FIRST_NAME).surname(SURNAME)
    			.middleName1(MIDDLE_NAME1).nationality(NATIONALITY).address(ADDRESS)
    			.dateOfBirth(DATE_OF_BIRTH).build();
			
		    RestTemplate restTemplate = new RestTemplate();
	        
	        URI uri = new URI(baseUrl);
	        HttpEntity<DonorDTO> request = new HttpEntity<>(donarDto);
	        
	        try {
	        	ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
	        	log.info(result.getBody());
	        	log.info(result.getStatusCode().toString());
	        	assertThat(result.getBody()).isEqualTo(FIRST_NAME + 1);
	        	assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	        }
	        catch (HttpClientErrorException ex) {
	        	 log.info(ex.getMessage());
	        }
	}
	
	/**
	 * Create a test donor in the database using the DonorService class. As this is the only row it will have a membership id of 1.
	 * Then retrieve the row relating to membership id 1 via a REST call to check that it matches the values we inserted.
	 * @throws URISyntaxException
	 */

	@Test
	public void testRetrieveDonorByMembershipId() throws URISyntaxException {
		
		String firstName = "Ziyad";
		LocalDate dateOfBirth = LocalDate.of(2004, 8, 15);
		
		DonorDTO donarDto = DonorDTO.builder().firstName(firstName).surname(SURNAME)
    			.middleName1(MIDDLE_NAME1).nationality(NATIONALITY).address(ADDRESS)
    			.dateOfBirth(dateOfBirth).build();
    	String savedDonor = donorService.saveDonor(donarDto);
    	log.info(savedDonor);
		
		//retrieve for membership id 1
		final String baseUrl = "http://localhost:"+randomServerPort+"/api/v1/donors/1";
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(baseUrl);
		
		try {
			DonorDTO result = restTemplate.getForObject(uri, DonorDTO.class);
	        log.info(result.getFirstName());
	        assertThat(result.getFirstName()).isEqualTo("Ziyad");
	        assertThat(result.getDateOfBirth()).isEqualTo(dateOfBirth);
	    }
	    catch (HttpClientErrorException ex) {
	        log.info(ex.getMessage());
	    }
		
	}
	
	
}
