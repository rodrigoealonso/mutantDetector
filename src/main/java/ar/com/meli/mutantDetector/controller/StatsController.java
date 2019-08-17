package ar.com.meli.mutantDetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.meli.mutantDetector.dto.StatsResp;
import ar.com.meli.mutantDetector.service.StatsService;

/**
 * StatsController
 */
@RestController
public class StatsController {
	
	@Autowired
	private StatsService statsService;
	
	/**
	 * REST Service to get stats related with the count of human dna, the count of mutant dna, and the ratio.
	 * <br><br>
	 * Endpoint:<br>
	 * GET → /stats/
	 * <br><br>
	 * Request is an empty JSON
	 * <br><br>
	 * This is an example of Response:<br>
	 * {<br>
	 * "count_mutant_dna": 40,<br>
	 * "count_human_dna": 100,<br>
	 * "ratio": 0.4<br>
	 * }
	 * <br>
	 * @return The Service returns the count of human dna, the count of mutant dna and a ratio (count of mutant dna / count of human dna).
	 * @throws Exception -> in case of any exception the service returns zero in all fields
	 */
	@RequestMapping(value="/stats", method=RequestMethod.GET)
	public StatsResp getStats() {

		StatsResp stats;
		try {
			stats = statsService.getStats();
		} catch (Exception ex) {
			return new StatsResp(0,0,0);
		}
		
		return stats;
	}
}
