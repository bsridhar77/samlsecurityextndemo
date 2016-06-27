package com.anthem.services.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anthem.services.member.model.Member;

public interface MemberRepository extends MongoRepository<Member, String> {

	Page<Member> findByCreatedBy(String createdBy,Pageable pageable);
	
	@Query("{ 'member.desc' : ?0 }")
	Page<Member> findByDesc(String desc,Pageable pageable);
	
	Member findByDesc(String desc);
	
	long deleteByDesc(String desc);
	
	
}
