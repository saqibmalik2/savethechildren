package com.example.savethechildren.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.savethechildren.model.Donor;
import com.example.savethechildren.model.Nationality;

/**
 * 
 * A simple Spring Data JPA CrudRepository class. Automatically provides the basic methods and I've added a couple of
 * native queries as well.
 * 
 */

@Repository
public interface DonorsRepository extends CrudRepository<Donor, Long>{
		
		@Query(value = "SELECT * FROM donor", nativeQuery = true)
		List<Donor> retrieveDonors();
		
		@Query(value = "SELECT d FROM donor d WHERE d.nationality = ?1", nativeQuery = true)
		List<Donor> retrieveDonorsByNationality(Nationality nationality);
		
		Optional<Donor> findByMembershipId(Long membershipId);
		
		long deleteByMembershipId(Long membershipId);
		
}
