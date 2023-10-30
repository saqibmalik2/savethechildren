package com.example.savethechildren.exceptions;


/**
 * An example of a custom exception to handle the case where the required Donor resource isn't found.
 */

@SuppressWarnings("serial")
public class DonorNotFoundException extends RuntimeException {
	
	public DonorNotFoundException(Long membershipId) {
		super("Couldn't find donor with membershipId: " + membershipId);
	}
	
}

