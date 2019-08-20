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
	 * if mutanSequence is equal to MUTANT_SEQUENCES_NEEDED, so this is a mutant dna
	 */
	private int mutantSequences;
	/**
	 * DNA Pattern: only A, T, C and G are valid characters
	 */
	private static final String PATTERN = "[atcg]+";
	/**
	 * Type of analysis of bi-dimensional array: Horizontal or Vertical
	 */
	private enum Type {
		HORIZONTAL, VERTICAL, DIAGONAL
	}
	/**
	 * Type of read of Diagonal analysis: Left to Right or Rigth to Left
	 */
	private enum Direction {
		LEFT_TO_RIGHT, RIGHT_TO_LEFT
	}
	/**
	 * Number of mutantSequences needed to consider the dna like a mutant dna
	 */
	private static final int MUTANT_SEQUENCES_NEEDED = 2;
	/**
	 * Number of equal chars to consider the sequence like a mutant sequence
	 */
	private static final int CONSECUTIVE_EQUAL_CHARS_NEEDED = 4;
		
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
		if(dimension < CONSECUTIVE_EQUAL_CHARS_NEEDED) {
			// invalid dimension
			throw new Exception("Invalid dimension. This should be greater than " + CONSECUTIVE_EQUAL_CHARS_NEEDED + ".");
		}
		
		Pattern pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE);
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
		mutantSequences = 0;
		
		//HORIZONTAL OR VERTICAL
		for (int i = 0; i < dnaSeq.length; i++) {
			if(analyzeDNA(dnaSeq, i, 0, Type.HORIZONTAL, null)) {
				return true;
			}
			
			if(analyzeDNA(dnaSeq, 0, i, Type.VERTICAL, null)) {
				return true;
			}
		}
		
		//DIAGONAL - below main diagonal including this
		for (int i = 0; i <= dnaSeq.length - CONSECUTIVE_EQUAL_CHARS_NEEDED; i++) {
			// left to right
			if(analyzeDNA(dnaSeq, i, 0, Type.DIAGONAL, Direction.LEFT_TO_RIGHT)) {
				return true;
			}
			// right to left
			if(analyzeDNA(dnaSeq, i, 0, Type.DIAGONAL, Direction.RIGHT_TO_LEFT)) {
				return true;
			}
		}
		
		//DIAGONAL - above main diagonal without this
		for (int i = 1; i <= dnaSeq.length - CONSECUTIVE_EQUAL_CHARS_NEEDED; i++) {
			// left to right
			if(analyzeDNA(dnaSeq, 0, i, Type.DIAGONAL, Direction.LEFT_TO_RIGHT)) {
				return true;
			}
			// right to left
			if(analyzeDNA(dnaSeq, 0, i, Type.DIAGONAL, Direction.RIGHT_TO_LEFT)) {
				return true;
			}
		}

		//this is a human dna, so return false
		return false;
	}
	
	/**
	 * Analyze the dnaSeq bi-dimensional array
	 *
	 * @param dnaSeq
	 * @param offsetRow: offset to apply to row
	 * @param offsetColumn: offset to apply to column
	 * @param type: Horizontal, Vertical or Diagonal
	 * @param direction: Left to Right or Right to Left (only for Diagonal type)
	 * @return true if this is a mutant dna (mutanSequence is equal to MUTANT_SEQUENCES_NEEDED)
	 */
	private boolean analyzeDNA(char[][] dnaSeq, int offsetRow, int offsetColumn, Type type, Direction direction) {
		int N = dnaSeq.length - 1;
		// lastChar starts in 0
		char lastChar = 0;
		int countOfConsecutiveEquealChars = 0;
		
		int limit = dnaSeq.length;
		if (type.equals(Type.DIAGONAL)) {
			limit = dnaSeq.length - offsetRow - offsetColumn;
		}
		
		for (int j = 0; j < limit; j++) {
			
			int indexRow = 0;
			int indexColumn = 0;

			if(type.equals(Type.HORIZONTAL)) {

				indexRow = offsetRow;
				indexColumn = j;
				
			} else if(type.equals(Type.VERTICAL)) {
				
				indexRow = j;
				indexColumn = offsetColumn;

			} else {
				//DIAGONAL
				indexRow = j+offsetRow;
				indexColumn = direction.equals(Direction.LEFT_TO_RIGHT)? j+offsetColumn : N-j-offsetColumn;
			}
			
			char currentChar = dnaSeq[indexRow][indexColumn];
			
			if(lastChar == 0) {
				// first time, the currentChar is saved as lastChar
				lastChar = currentChar;
			}
			if (currentChar == lastChar) {
				// the currentChar is equal to lastChar, so increments countOfConsecutiveEquealChars
				countOfConsecutiveEquealChars++;
				if(countOfConsecutiveEquealChars == CONSECUTIVE_EQUAL_CHARS_NEEDED) {
					// countOfConsecutiveEquealChars is equal to CONSECUTIVE_EQUAL_CHARS_NEEDED, so reset this flag and increments mutantSequence
					countOfConsecutiveEquealChars = 0;
					mutantSequences++;
					if(mutantSequences == MUTANT_SEQUENCES_NEEDED) {
						// mutanSequence is equal to MUTANT_SEQUENCES_NEEDED, so this is a mutant dna
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
		return false;
	}
}
