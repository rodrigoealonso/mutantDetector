package ar.com.meli.mutantDetector.dao.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import ar.com.meli.mutantDetector.dao.DBServiceDAO;
import ar.com.meli.mutantDetector.dao.DynamoDAO;
import ar.com.meli.mutantDetector.model.Human;
import ar.com.meli.mutantDetector.model.Mutant;

/**
 * DBServiceDAO
 *
 */
@Service
public class DBServiceDAOImpl extends DynamoDAO implements DBServiceDAO {
	
	public DBServiceDAOImpl() {
		super();
	}
	
	/**
	 * insert a mutant dna on DB
	 */
	@Override
	public void insert(Mutant mutant) {
		mapper.save(mutant);
	}
	
	/**
	 * insert a human dna on DB
	 */
	@Override
	public void insert(Human human) {
		mapper.save(human);
	}
	
	/**
	 * get the humans dna count from DB
	 */
	@Override
	public int getHumansCount() {
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
		return mapper.count(Human.class, dbScanExpression);
	}

	/**
	 * get the mutants dna count from DB
	 */
	@Override
	public int getMutantsCount() {
		DynamoDBScanExpression dbScanExpression = new DynamoDBScanExpression();
		return mapper.count(Mutant.class, dbScanExpression);
	}
}
