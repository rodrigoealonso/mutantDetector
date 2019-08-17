package ar.com.meli.mutantDetector.service;

/**
 * Mutant Service
 *
 */
public interface MutantService {
	
	/**
	 * isMutant
	 * 
	 * @param dna
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isMutant(String[] dna) throws Exception;
}
