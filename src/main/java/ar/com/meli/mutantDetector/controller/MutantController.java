package ar.com.meli.mutantDetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.meli.mutantDetector.dto.MutantDetectorReq;
import ar.com.meli.mutantDetector.service.MutantService;

/**
 * MutantController
 */
@RestController
public class MutantController {
	
	@Autowired
	private MutantService mutantService;
	
	/**
	 * REST Service to detect if a human is a mutant or not.
	 * <br><br>
	 * Endpoint:<br>
	 * POST → /mutant/
	 * <br><br>
	 * Request JSON - example:<br>
	 * {<br>
	 * “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]<br>
	 * }
	 * <br><br>
	 * @param request
	 * @return The Service returns HTTP 200 (OK) if this is a mutant dna and HTTP 403 (FORBIDDEN) if this is a human dna.
	 * BAD Request is returned in case of a malformed dna.
	 * @throws Exception
	 */
	@RequestMapping(value="/mutant", method=RequestMethod.POST)
	public ResponseEntity<String> mutantDetector(@RequestBody MutantDetectorReq request) throws Exception {

		try {
			if(mutantService.isMutant(request.getDna())) {
				// this is a mutant dna
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// this is a human dna
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception ex) {
			//return a BAD Request in case of any exception in service
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		} 		
	}
}
