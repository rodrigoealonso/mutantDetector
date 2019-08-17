package ar.com.meli.mutantDetector.service;

import ar.com.meli.mutantDetector.dto.StatsResp;

/**
 * StatsService
 *
 */
public interface StatsService {
	
	/**
	 * getStats
	 * 
	 * @return StatsResp
	 * @throws Exception
	 */
	public StatsResp getStats() throws Exception;
}
