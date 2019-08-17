package ar.com.meli.mutantDetector.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.meli.mutantDetector.dao.DBServiceDAO;
import ar.com.meli.mutantDetector.dto.StatsResp;
import ar.com.meli.mutantDetector.service.StatsService;

/**
 * Stats Service
 *
 */
@Service
public class StatsServiceImpl implements StatsService {
	
	@Autowired
	private DBServiceDAO dbServiceDAO;
	
	/**
	 * getStats returns a StatsResp object with the count of human dna, the count of mutant dna, and the ratio.
	 * This throws an Exception in case of any error with DB.
	 */
	@Override
	public StatsResp getStats() throws Exception {

		long countMutantDNA = 0;
		long countHumanDNA = 0;
		try {
			//get mutant dna count from DB
			countMutantDNA = dbServiceDAO.getMutantsCount();
			//get human dna count from DB
			countHumanDNA = dbServiceDAO.getHumansCount();
		} catch (Exception ex) {
			throw new Exception("Exception produced on DB.");
		}

		double ratio = 0;
		if (countHumanDNA != 0) {
			//countMutantDNA is converted to double in order to get a double in the division calculation
			ratio = new Double(countMutantDNA)/countHumanDNA;
		}
		//This is to round ratio to two decimals
		ratio = Math.round(ratio*100.0)/100.0;	
	
		return new StatsResp(countMutantDNA, countHumanDNA, ratio);
	}
}
