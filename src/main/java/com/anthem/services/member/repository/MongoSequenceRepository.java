package com.anthem.services.member.repository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.anthem.services.member.model.Sequence;


@Repository
public class MongoSequenceRepository implements SequenceRepository {

	private final MongoOperations operations;
	
	@Autowired
	public MongoSequenceRepository(MongoOperations operations) {

		Assert.notNull(operations);
		this.operations = operations;
	}

	public BigInteger getNextSequence(String collectionName){
		
		Sequence sequence = operations.findAndModify(
				query(where("_id").is(collectionName)),
				new Update().inc("seq", 1),
				options().returnNew(true),
				Sequence.class);
				return sequence.getSeq();
	}

	
}
