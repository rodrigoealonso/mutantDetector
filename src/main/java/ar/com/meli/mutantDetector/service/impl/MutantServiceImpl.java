package ar.com.meli.mutantDetector.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.meli.mutantDetector.dao.DBServiceDAO;
import ar.com.meli.mutantDetector.model.Human;
import ar.com.meli.mutantDetector.model.Mutant;
import ar.com.meli.mutantDetector.service.MutantService;

/**
 * Mutant Service
 *
 */
@Service
public class MutantServiceImpl implements MutantService {

	@Autowired	
	private DBServiceDAO dbServiceDAO;
	
	/**
	 * isMutant analyze a dna an return if this is a mutant dna or a human dna
	 * 
	 * @param dna
	 * @return boolean
	 * @throws Exception
	 */
	@Override
	public boolean isMutant(String[] dna) throws Exception {
		
		char[][] dnaSeq = validateAndConvertDNA(dna);
		
		boolean isMutant = false;
		isMutant = analyzeDNA(dnaSeq);
		
		if(isMutant) {
			//this is a mutant, so creates a Mutant with this dna and insert into DB
			Mutant mutant = new Mutant(dna);
			dbServiceDAO.insert(mutant);
		} else {
			//this is a human, so creates a Human with this dna and insert into DB
			Human human = new Human(dna);
			dbServiceDAO.insert(human);
		}
		
		return isMutant;
	}
	
	/**
	 * Util to convert the String[] to char[][]<br>
	 * Also this validates the pattern (only A, T, C and G are valid characters) and 
	 * check the dimension (String[] must be converted to bi-dimensional char array (char[][]). 
	 * 
	 * @param dna
	 * @return char[][]
	 * @throws Exception
	 */
	private char[][] validateAndConvertDNA(String[] dna) throws Exception {
		//dimension is determinated by the number of dna into String[]
		int dimension = dna.length;
		Pattern pattern = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);
		char[][] dnaSeq = new char[dimension][dimension];
		
		for (int i = 0; i < dimension ; i++) {
			
				if(dna[i].length() != dimension) {
					// invalid dimension
					throw new Exception("Invalid dimension. The number of rows (count of dna) has to be equal to the number of columns (dna length).");
				} else if(!pattern.matcher(dna[i]).matches()){
					// invalid pattern
					throw new Exception("Invalid pattern. Only A, T, C and G are valid characters.");
				} else {
					//populate char[][] and normalize to upper case
					dnaSeq[i] = dna[i].toUpperCase().toCharArray();
				}
				
		}
			
		return dnaSeq;
	}
	
	/**
	 * Analyze the dnaSeq bi-dimensional array looking for two mutantSequence to consider 
	 * that the dnaSeq is a mutant dna
	 * 
	 * @param dnaSeq
	 * @return true if this is a mutant dna / false if this is a human dna
	 */
	private boolean analyzeDNA(char[][] dnaSeq) {
		// if mutanSequence is equal to 2, so this is a mutant dna
		int mutantSequence = 0;
		
		//HORIZONTAL
		for (char[] row : dnaSeq) {
			// lastChar starts in 0
			char lastChar=0;
			int countOfConsecutiveEquealChars = 0;
			
			for (char currentChar : row) {
				if(lastChar==0) {
					// first time, the currentChar is saved as lastChar
					lastChar=currentChar;
				}
				if (currentChar==lastChar) {
					// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
					countOfConsecutiveEquealChars++;
					if(countOfConsecutiveEquealChars==4) {
						// countOfConsecutiveEquealChars is equal to 4, so reset this flag and increments mutantSequence
						countOfConsecutiveEquealChars=0;
						mutantSequence++;
						if(mutantSequence==2) {
							// mutanSequence is equal to 2, so this is a mutant dna
							return true;
						}
					}
				} else {
					// the currentChar is not equal to lastChar, so save the currentChar as lastChar 
					// and reset countOfConsecutiveEquealChars
					lastChar = currentChar;
					countOfConsecutiveEquealChars=1;
				}
			}
		}
		
		//VERTICAL -> dnaSecuence[row][column]
		for (int i = 0; i < dnaSeq.length; i++) {
			// lastChar starts in 0
			char lastChar=0;
			int countOfConsecutiveEquealChars = 0;
			
			for (int j = 0; j < dnaSeq.length; j++) {
				char currentChar = dnaSeq[j][i];
				
				if(lastChar==0) {
					// first time, the currentChar is saved as lastChar
					lastChar=currentChar;
				}
				if (currentChar==lastChar) {
					// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
					countOfConsecutiveEquealChars++;
					if(countOfConsecutiveEquealChars==4) {
						// countOfConsecutiveEquealChars is equal to 4, so reset this flag and increments mutantSequence
						countOfConsecutiveEquealChars=0;
						mutantSequence++;
						if(mutantSequence==2) {
							// mutanSequence is equal to 2, so this is a mutant dna
							return true;
						}
					}
				} else {
					// the currentChar is not equal to lastChar, so save the currentChar as lastChar 
					// and reset countOfConsecutiveEquealChars
					lastChar = currentChar;
					countOfConsecutiveEquealChars = 1;
				}
			}
		}
		
		//DIAGONAL top to bottom and left to right
		for (int i = 0; i <= dnaSeq.length-4; i++) {
			// lastChar starts in 0
			char lastChar=0;
			int countOfConsecutiveEquealChars = 0;
			
			for (int j = 0; j < dnaSeq.length-i; j++) {
				char currentChar = dnaSeq[i+j][j];
				
				if(lastChar==0) {
					// first time, the currentChar is saved as lastChar
					lastChar=currentChar;
				}
				if (currentChar==lastChar) {
					// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
					countOfConsecutiveEquealChars++;
					if(countOfConsecutiveEquealChars==4) {
						// countOfConsecutiveEquealChars is equal to 4, so reset this flag and increments mutantSequence
						countOfConsecutiveEquealChars=0;
						mutantSequence++;
						if(mutantSequence==2) {
							// mutanSequence is equal to 2, so this is a mutant dna
							return true;
						}
					}
				} else {
					// the currentChar is not equal to lastChar, so save the currentChar as lastChar 
					// and reset countOfConsecutiveEquealChars
					lastChar = currentChar;
					countOfConsecutiveEquealChars = 1;
				}
			}
		}
		
		//DIAGONAL top to bottom and rigth to left
		for (int i = 0; i <= dnaSeq.length-4; i++) {
			// lastChar starts in 0
			char lastChar=0;
			int countOfConsecutiveEquealChars = 0;
			
			for (int j = dnaSeq.length-1; j >= i; j--) {
				char currentChar = dnaSeq[dnaSeq.length-1-j+i][j];
				
				if(lastChar==0) {
					// first time, the currentChar is saved as lastChar
					lastChar=currentChar;
				}
				if (currentChar==lastChar) {
					// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
					countOfConsecutiveEquealChars++;
					if(countOfConsecutiveEquealChars==4) {
						// countOfConsecutiveEquealChars is equal to 4, so reset this flag and increments mutantSequence
						countOfConsecutiveEquealChars=0;
						mutantSequence++;
						if(mutantSequence==2) {
							// mutanSequence is equal to 2, so this is a mutant dna
							return true;
						}
					}
				} else {
					// the currentChar is not equal to lastChar, so save the currentChar as lastChar 
					// and reset countOfConsecutiveEquealChars
					lastChar = currentChar;
					countOfConsecutiveEquealChars = 1;
				}
			}
		}
			
		//DIAGONAL bottom to top and left to right (without main diagonal)
		for (int i = dnaSeq.length-1-1; i > 2; i--) {
			// lastChar starts in 0
			char lastChar=0;
			int countOfConsecutiveEquealChars = 0;
			
			for (int j = 0; j <= i; j++) {
				char currentChar = dnaSeq[i-j][j];
				
				if(lastChar==0) {
					// first time, the currentChar is saved as lastChar
					lastChar=currentChar;
				}
				if (currentChar==lastChar) {
					// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
					countOfConsecutiveEquealChars++;
					if(countOfConsecutiveEquealChars==4) {
						// countOfConsecutiveEquealChars is equal to 4, so reset this flag and increments mutantSequence
						countOfConsecutiveEquealChars=0;
						mutantSequence++;
						if(mutantSequence==2) {
							// mutanSequence is equal to 2, so this is a mutant dna
							return true;
						}
					}
				} else {
					// the currentChar is not equal to lastChar, so save the currentChar as lastChar 
					// and reset countOfConsecutiveEquealChars
					lastChar = currentChar;
					countOfConsecutiveEquealChars = 1;
				}
			}
		}
		
		//DIAGONAL bottom to top and right to left (without main diagonal)
		for (int i = dnaSeq.length-1-1; i >= 0; i--) {
			// lastChar starts in 0
			char lastChar=0;
			int countOfConsecutiveEquealChars = 0;
			
			for (int j = dnaSeq.length-1; j >= dnaSeq.length-1-i; j--) {
				char currentChar = dnaSeq[i-(dnaSeq.length-1-j)][j];
				
				if(lastChar==0) {
					// first time, the currentChar is saved as lastChar
					lastChar=currentChar;
				}
				if (currentChar==lastChar) {
					// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
					countOfConsecutiveEquealChars++;
					if(countOfConsecutiveEquealChars==4) {
						// countOfConsecutiveEquealChars is equal to 4, so reset this flag and increments mutantSequence
						countOfConsecutiveEquealChars=0;
						mutantSequence++;
						if(mutantSequence==2) {
							// mutanSequence is equal to 2, so this is a mutant dna
							return true;
						}
					}
				} else {
					// the currentChar is not equal to lastChar, so save the currentChar as lastChar 
					// and reset countOfConsecutiveEquealChars
					lastChar = currentChar;
					countOfConsecutiveEquealChars = 1;
				}
			}
		}
		
		//this is a human dna, so return false
		return false;
	}
}
