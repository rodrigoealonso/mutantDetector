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

import ar.com.meli.mutantDetector.controller.MutantController;
import ar.com.meli.mutantDetector.service.impl.MutantServiceImpl;
/**
 * MutantControllerTest
 *
 */
public class MutantControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private MutantController mutantController;
	@Mock
	private MutantServiceImpl mutantService;
	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String URI = "/mutant";
	/**
	 * String[] for a valid mutant dna
	 */
	private String[] dnaMutant = new String[] {"AAAAGA","CAGTGC","TTATCG","AGATGG","CCCGTA","TCGCTG"};
	/**
	 * String[] for a valid human dna
	 */
	private String[] dnaHuman = new String[] {"ATGCCA","CAGTGC","TTCTGG","AGAAGG","CCCGTA","TCGCTG"};
	/**
	 * String[] with invalid dimension dna
	 */
	private String[] invalidDNA = new String[] {"ATGCCA","CAGTGC","TTCTGG","AGAAGG","TCGCTG"};
	/**
	 * request for a valid mutant dna
	 */
	private String mockMutantRequest = "{\"dna\":[\"AAAAGA\",\"CAGTGC\",\"TTATCG\",\"AGATGG\",\"CCCGTA\",\"TCGCTG\"]}";
	/**
	 * request for a valid human dna
	 */
	private String mockHumanRequest = "{\"dna\":[\"ATGCCA\",\"CAGTGC\",\"TTCTGG\",\"AGAAGG\",\"CCCGTA\",\"TCGCTG\"]}";
	/**
	 * request with invalid dimension
	 */
	private String mockInvalidRequest = "{\"dna\":[\"ATGCCA\",\"CAGTGC\",\"TTCTGG\",\"AGAAGG\",\"TCGCTG\"]}";

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mutantController).build();
	}
	
	/**
	 * This test is to validate the right response after analyze a valid mutant dna
	 */
	@Test
	public void testValidMutantDNADetection() throws Exception {
		
		//invoke mockIsMutant Service to analyze dna. Response is true because this is a mutant dna
		mockIsMutantService(dnaMutant, true);

		//call /mutant endpoint
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(CONTENT_TYPE_JSON).content(mockMutantRequest));

		//validate that the http status code is 200 and that the response is empty
		MvcResult result = resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(content.isEmpty(), "Response have to be empty");
	}
	
	/**
	 * This test is to validate the right response after analyze a valid human dna
	 */
	@Test
	public void testValidHumanDNADetection() throws Exception {
		
		//invoke mockIsMutant Service to analyze dna. Response is false because this is a human dna
		mockIsMutantService(dnaHuman, false);
		
		//call /mutant endpoint
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(CONTENT_TYPE_JSON).content(mockHumanRequest));
		
		//validate that the http status code is 403 and that the response is empty
		MvcResult result = resultActions.andExpect(MockMvcResultMatchers.status().isForbidden()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(content.isEmpty(), "Response have to be empty");
	}
	
	/**
	 * This test is to validate the right response after analyze a dna with invalid dimension
	 */
	@Test
	public void testInvalidDNADetection() throws Exception {
		
		//Throw a mock exception
		Mockito.doThrow(new Exception("exception")).when(mutantService).isMutant(invalidDNA);
		
		//call /mutant endpoint
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URI).contentType(CONTENT_TYPE_JSON).content(mockInvalidRequest));
		
		//validate that the http status code is 400 and that the response is empty
		MvcResult result = resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		String content = result.getResponse().getContentAsString();
		Assert.isTrue(content.isEmpty(), "Response have to be empty");
	}
	
	/**
	 * mock for isMutant service. This returns the expectedResult sent by param
	 * 
	 * @param dna
	 * @param expectedResult
	 * @throws Exception
	 */
	private void mockIsMutantService(String[] dna, boolean expectedResult) throws Exception {
		Mockito.when(mutantService.isMutant(dna)).thenReturn(expectedResult);
	}

}

