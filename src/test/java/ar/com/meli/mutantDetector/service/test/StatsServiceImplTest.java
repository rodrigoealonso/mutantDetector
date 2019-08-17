package ar.com.meli.mutantDetector.service.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.com.meli.mutantDetector.dao.impl.DBServiceDAOImpl;
import ar.com.meli.mutantDetector.dto.StatsResp;
import ar.com.meli.mutantDetector.service.impl.StatsServiceImpl;
import junit.framework.TestCase;

/**
 * StatsServiceImplTest
 */
public class StatsServiceImplTest {

	@InjectMocks
	private StatsServiceImpl statsService;
	@Mock
	private DBServiceDAOImpl dbServiceDAO;	
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * This test is to validate that the stats object received after invoke getStats service is not null
	 */
	@Test
	public void testStatsServiceInvocation() throws Exception {
		// retrive the mutants count and humans count from mock DB
		Mockito.when(dbServiceDAO.getMutantsCount()).thenReturn(40);
		Mockito.when(dbServiceDAO.getHumansCount()).thenReturn(100);

		// invoke getStats Service
		StatsResp stats = statsService.getStats();

		// validate that the stats object received is not null
		TestCase.assertNotNull(stats);
	}
	/**
	 * This test is to validate that the ratio received after invoke getStats service has the right value
	 */
	@Test
	public void testRatioValueReceived() throws Exception {
		int mockMutantCount = 40;
		int mockHumanCount = 100;
		//this is the right ratio value expected
		double expectedRatioValue = 0.4;
		
		// retrive the mutants count and humans count from mock DB
		Mockito.when(dbServiceDAO.getMutantsCount()).thenReturn(mockMutantCount);
		Mockito.when(dbServiceDAO.getHumansCount()).thenReturn(mockHumanCount);

		// invoke getStats Service
		StatsResp stats = statsService.getStats();
		
		// validate that the ratio received has the right value
		TestCase.assertEquals(expectedRatioValue, stats.getRatio());
	}
	/**
	 * This test is to validate that a possible exception on DB is catched by the service
	 */
	@Test(expected = Exception.class)
	public void testExceptionOnDB() throws Exception {
		// an Exception is produced trying to retrive the mutants count from mock DB 
		Mockito.when(dbServiceDAO.getMutantsCount()).thenThrow(new Exception());
	}
}
