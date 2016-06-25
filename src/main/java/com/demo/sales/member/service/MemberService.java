package com.demo.sales.member.service;

import java.math.BigInteger;
import java.net.URI;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.sales.member.exception.ResourceNotFoundException;
import com.demo.sales.member.model.Member;
import com.demo.sales.member.repository.MemberRepository;
import com.demo.sales.member.repository.MongoSequenceRepository;
import com.demo.sales.member.util.PageResource;

@RestController
@RequestMapping("/member")
public class MemberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);
	@Autowired
	MemberRepository memberRepository;

	
	@Autowired
	MongoSequenceRepository mongoSequenceRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Page<Member>> getAllMembers(Pageable pageable) {

		
		LOGGER.info("Entering getAllMembers(pageable)");
		Page<Member> memberList = memberRepository.findAll(pageable);

		if (memberList == null || memberList.getTotalPages()== 0) {
			throw new ResourceNotFoundException(
					"No Member(s) found in the System ");
		}

		LOGGER.info("Leaving getAllMembers(pageable)");
		return new ResponseEntity<>(memberList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public PageResource<Member> getAllMembersByPage(Pageable pageable) {

		Page<Member> memberList = memberRepository.findAll(pageable);

		if (memberList == null || memberList.getTotalPages()== 0) {
			throw new ResourceNotFoundException(
					"No Member(s) found in the System ");
		}
		
		
		return new PageResource<Member>(memberList,"page","size");
		

	}

	@RequestMapping(value = "/desc/{desc}", method = RequestMethod.GET)
	public ResponseEntity<Page<Member>> getMembersByDesc(
			@Valid @Nonnull @PathVariable String desc,Pageable pageable) {

		
		Page<Member> memberList = memberRepository.findByDesc(desc,pageable);

		if (memberList == null || memberList.getTotalPages() <= 0) {
			throw new ResourceNotFoundException(
					"No Member(s) found for DESC: desc: " + desc);
		}

		return new ResponseEntity<>(memberList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{memberId}", method = RequestMethod.GET)
	public ResponseEntity<Member> getMembersById(@PathVariable String memberId) {
		LOGGER.info("Entering getMembersById(memberId)");
		Member member = memberRepository.findOne(memberId);

		if (member == null) {
			throw new ResourceNotFoundException(
					"Member with Id " + memberId + " not found");
		}
		LOGGER.info("Leaving getMembersById(memberId)");
		return new ResponseEntity<>(member, HttpStatus.OK);
	}



	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createMember(@Valid @RequestBody Member member) {

		// Set the Current Date of Domain Model object creation
		member.setCreatedOn(new Date());

		// Get the Next Sequence Number to use for Domain Model
		BigInteger nextDomainModelObjectRefNo = mongoSequenceRepository
				.getNextSequence("member");

		member.setMemberId("" + nextDomainModelObjectRefNo);

		// Save the DomainModel object
		Member newMember = memberRepository.save(member);

		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newMemberUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newMember.getMemberId())
				.toUri();
		responseHeaders.setLocation(newMemberUri);

		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMemberDetails(@RequestBody Member member) {

		verifyMemberById(member.getMemberId());
		Member oriMemberObj = memberRepository.findOne(member.getMemberId());

		// Set the Current Date to Domain Model  updation Date
		oriMemberObj.setLastModifiedOn(new Date());
		
		//Set the User who made the update
		oriMemberObj.setModifedBy(member.getModifedBy());
		
	
		memberRepository.save(oriMemberObj);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	

	@RequestMapping(value = "/{memberId}", method = RequestMethod.DELETE)
	public void deleteMemberById(@PathVariable String domainModelId) {
		verifyMemberById(domainModelId);
		memberRepository.delete(domainModelId);
				
	}

	
	
	protected void verifyMemberById(String domainModelId)
			throws ResourceNotFoundException {
		if(null==domainModelId || domainModelId.trim().length()<=0){
		throw new ResourceNotFoundException(
				"Member Id is Mandatory");
		}
		
		Member domainModel = memberRepository.findOne(domainModelId);
		
		if (domainModel == null) {
			throw new ResourceNotFoundException(
					"Member with Id " + domainModelId
							+ " not found");
		}
	}

}