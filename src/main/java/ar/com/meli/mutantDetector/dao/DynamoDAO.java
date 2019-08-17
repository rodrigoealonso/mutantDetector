package ar.com.meli.mutantDetector.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * DynamoDAO provides the connection with Cloud DynamoDB on AWS
 * 
 */
public abstract class DynamoDAO {
	protected AmazonDynamoDB client;
	protected DynamoDB dynamoDB;
	protected DynamoDBMapper mapper;
	
	public DynamoDAO() {
		
	    client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.US_EAST_2)
				.build();
	   
	    mapper = new DynamoDBMapper(client);
	}
}
