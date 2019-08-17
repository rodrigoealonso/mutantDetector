package ar.com.meli.mutantDetector.service.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.com.meli.mutantDetector.dao.impl.DBServiceDAOImpl;
import ar.com.meli.mutantDetector.model.Human;
import ar.com.meli.mutantDetector.model.Mutant;
import ar.com.meli.mutantDetector.service.impl.MutantServiceImpl;
import junit.framework.TestCase;

/**
 * MutantServiceImplTest
 */
public class MutantServiceImplTest {
	
	@InjectMocks
	private MutantServiceImpl mutantService;
	@Mock
	private DBServiceDAOImpl dbServiceDAO;
	/**
	 * valid mutant dna with only horizontal combination
	 */
	private String[] mutantDNAOnlyHorizontal = new String[] {"TTTTGA","CAGTGC","TTATGG","AGAAGG","CCCCTA","TCGCTG"};
	/**
	 * valid mutant dna with only vertical combination
	 */
	private String[] mutantDNAOnlyVertical = new String[] {"GTGCGA","CACTGC","TCATGG","CGATGG","CCCTTA","TCTCCG"};
	/**
	 * valid mutant dna with only diagonal combination
	 */
	private String[] mutantDNAOnlyDiagonal = new String[] {"TTGCGA","CCCTAC","TCGTGT","CGAGTG","CCCTGA","TCTCTG"};
	/**
	 * valid mutant dna with mixed combination
	 */
	private String[] mutantDNAMixed = new String[] {"GTGCGA","CGCTAC","TCGTGG","CGAGTG","CCCCTA","TCTCTG"};
	/**
	 * valid human dna (option 1)
	 */
	private String[] humanDNAOp1 = new String[] {"TGGCGA","ACATAC","TCCTGG","CGAAAG","CCCGTA","TCGCTG"};
	/**
	 * valid human dna (option 2)
	 */
	private String[] humanDNAOp2 = new String[] {"GTGCGA","CACTGC","TCATGG","CGAAAG","CCCGTA","TTTATG"};
	/**
	 * dna with invalid pattern
	 */
	private String[] dnaWithInvalidPattern = new String[] {"JXGCGA","CACTGC","TCATGG","CGAAAG","CCCGTA","TCGCTG"};
	/**
	 * dna witn invalid dimension
	 */
	private String[] dnaWithInvalidDimension = new String[] {"CACTGC","CACTGC","TCATGG","CGAAAG","CCCGTA"};
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * This test is to validate the right response of isMutant Service after analyze 
	 * a valid mutant dna with only horizontal combination
	 */
	@Test
	public void testValidMutantDNAOnlyHorizontal() throws Exception {

		// create a valid mutant dna and insert it into mock DB
		String[] dna = mutantDNAOnlyHorizontal;
		Mutant mutant = new Mutant(dna);
		Mockito.doNothing().when(dbServiceDAO).insert(Mockito.eq(mutant));

		// invoke isMutant Service with this dna
		boolean result = mutantService.isMutant(dna);

		// validate that isMutant returns true
		TestCase.assertTrue(result);
	}
	/**
	 * This test is to validate the right response of isMutant Service after analyze 
	 * a valid mutant dna with only vertical combination
	 */
	@Test
	public void testValidMutantDNAOnlyVertical() throws Exception {

		// create a valid mutant dna and insert it into mock DB
		String[] dna = mutantDNAOnlyVertical;
		Mutant mutant = new Mutant(dna);
		Mockito.doNothing().when(dbServiceDAO).insert(Mockito.eq(mutant));

		// invoke isMutant Service with this dna
		boolean result = mutantService.isMutant(dna);

		// validate that isMutant returns true
		TestCase.assertTrue(result);
	}
	/**
	 * This test is to validate the right response of isMutant Service after analyze 
	 * a valid mutant dna with only diagonal combination
	 */
	@Test
	public void testValidMutantDNAOnlyDiagonal() throws Exception {

		// create a valid mutant dna and insert it into mock DB
		String[] dna = mutantDNAOnlyDiagonal;
		Mutant mutant = new Mutant(dna);
		Mockito.doNothing().when(dbServiceDAO).insert(Mockito.eq(mutant));

		// invoke isMutant Service with this dna
		boolean result = mutantService.isMutant(dna);

		// validate that isMutant returns true
		TestCase.assertTrue(result);
	}
	/**
	 * This test is to validate the right response of isMutant Service after analyze 
	 * a valid mutant dna with mixed combination
	 */
	@Test
	public void testValidMutantDNAMixed() throws Exception {

		// create a valid mutant dna and insert it into mock DB
		String[] dna = mutantDNAMixed;
		Mutant mutant = new Mutant(dna);
		Mockito.doNothing().when(dbServiceDAO).insert(Mockito.eq(mutant));

		// invoke isMutant Service with this dna
		boolean result = mutantService.isMutant(dna);

		// validate that isMutant returns true
		TestCase.assertTrue(result);
	}
	/**
	 * This test is to validate the right response of isMutant Service after analyze 
	 * a valid human dna (option 1)
	 */
	@Test
	public void testValidHumanDNAOp1() throws Exception {
		// create a valid human dna and insert it into mock DB
		String[] dna = humanDNAOp1;
		Human human = new Human(dna);
		Mockito.doNothing().when(dbServiceDAO).insert(Mockito.eq(human));

		// invoke isMutant Service with this dna
		boolean result = mutantService.isMutant(dna);

		// validate that isMutant returns false
		TestCase.assertFalse(result);
	}
	/**
	 * This test is to validate the right response of isMutant Service after analyze 
	 * a valid human dna (option 2)
	 */
	@Test
	public void testValidHumanDNAOp2() throws Exception {
		// create a valid human dna and insert it into mock DB
		String[] dna = humanDNAOp2;
		Human human = new Human(dna);
		Mockito.doNothing().when(dbServiceDAO).insert(Mockito.eq(human));

		// invoke isMutant Service with this dna
		boolean result = mutantService.isMutant(dna);

		// validate that isMutant returns false
		TestCase.assertFalse(result);
	}
	/**
	 * This test is to validate that isMutant Service throws an exception if the dna has an invalid pattern
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testInvalidDNAPattern() throws Exception {
		// set a dna with invalid pattern
		String[] dna = dnaWithInvalidPattern;
		
		// invoke isMutant Service with this dna. The service have to throws an Exception
		mutantService.isMutant(dna);
	}
	/**
	 * This test is to validate that isMutant Service throws an exception if the dna has an invalid dimension
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testInvalidDNADimension() throws Exception {
		// set a dna with invalid dimension
		String[] dna = dnaWithInvalidDimension;
		
		// invoke isMutant Service with this dna. The service have to throws an Exception
		mutantService.isMutant(dna);
	}
}
