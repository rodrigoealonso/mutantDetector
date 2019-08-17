package ar.com.meli.mutantDetector.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import ar.com.meli.mutantDetector.controller.StatsController;
import ar.com.meli.mutantDetector.dto.StatsResp;
import ar.com.meli.mutantDetector.service.impl.StatsServiceImpl;
/**
 * StatsControllerTest
 */
public class StatsControllerTest {
	
	private MockMvc mockMvc;
	@InjectMocks
	private StatsController statsController;
	@Mock
	private StatsServiceImpl statsService;
	private static final String URI = "/stats";

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(statsController).build();
	}
	
	/**
	 * This test is to validate the right response after get the stats
	 * @throws Exception
	 */
	@Test
	public void testInvocation() throws Exception {
		//invoke getStats Service
		Mockito.when(statsService.getStats()).thenReturn(new StatsResp(40, 100, 0.4));

		//call /stats endpoint
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(URI));

		//validate that the http status code is 200 and that the response is not empty
		MvcResult result = resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(!content.isEmpty(), "HTTP Response have to be 200 and the body has not empty");
	}
	/**
	 * This test is to validate the response in case of an Exception on DB
	 * @throws Exception
	 */
	@Test
	public void testExceptionOnDB() throws Exception {
		//invoke getStats Service
		Mockito.when(statsService.getStats()).thenThrow(new Exception());

		//call /stats endpoint
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(URI));

		//validate that the http status code is 200 and that the response is not empty
		MvcResult result = resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(!content.isEmpty(), "HTTP Response have to be 200 and the body has not empty");
	}
}
