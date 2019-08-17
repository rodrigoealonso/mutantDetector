package ar.com.meli.mutantDetector.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Model of Mutant table on DynamoDB
 * 
 */
@DynamoDBTable(tableName="Mutant")
public class Mutant {
	
	private String dnaSequence;
	
	public Mutant(String[] dnaSequence) {
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