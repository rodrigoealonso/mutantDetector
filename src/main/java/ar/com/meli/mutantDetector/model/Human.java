package ar.com.meli.mutantDetector.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Model of Human table on DynamoDB
 * 
 */
@DynamoDBTable(tableName="Human")
public class Human {
	
	private String dnaSequence;
	
	public Human(String[] dnaSequence) {
		//join the dna sequences into a unique string
		this.dnaSequence = String.join("", dnaSequence);
	}
	
	@DynamoDBHashKey(attributeName="dnaSequence")
	public String getDnaSequence() {
		return dnaSequence;
	}

	public void setDnaSequence(String dnaSequence) {
		this.dnaSequence = dnaSequence;
	}
}