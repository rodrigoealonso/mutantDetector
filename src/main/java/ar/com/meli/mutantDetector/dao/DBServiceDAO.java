package ar.com.meli.mutantDetector.dao;

import ar.com.meli.mutantDetector.model.Human;
import ar.com.meli.mutantDetector.model.Mutant;

/**
 * DBServiceDAO
 *
 */
public interface DBServiceDAO {
	
	/**
	 * insert a mutant dna on DB
	 */
	public void insert(Mutant mutant) throws Exception;
	
	/**
	 * insert a human dna on DB
	 */
	public void insert(Human human) throws Exception;
	
	/**
	 * get the humans dna count from DB
	 */
	public int getHumansCount() throws Exception;
	
	/**
	 * get the mutants dna count from DB
	 */
	public int getMutantsCount() throws Exception;
}