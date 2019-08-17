package ar.com.meli.mutantDetector.dto;

/**
 * DTO Response of getStats Service
 *
 */
public class StatsResp {
	private long count_mutant_dna;
	private long count_human_dna;
	private double ratio;
	
	public StatsResp(long countMutantDNA, long countHumanDNA, double ratio) {
		this.setCount_mutant_dna(countMutantDNA);
		this.setCount_human_dna(countHumanDNA);
		this.setRatio(ratio);
	}

	public long getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(long count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	public long getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(long count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
}
