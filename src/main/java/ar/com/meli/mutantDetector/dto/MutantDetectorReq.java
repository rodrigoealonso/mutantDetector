package ar.com.meli.mutantDetector.dto;

/**
 * DTO Request of isMutant Service
 */
public class MutantDetectorReq {
	private String [] dna;

	public String [] getDna() {
		return dna;
	}

	public void setDna(String [] dna) {
		this.dna = dna;
	}
}
