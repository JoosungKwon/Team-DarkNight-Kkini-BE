package com.prgrms.mukvengers.domain.crewmember.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.crew.repository.CrewRepository;
import com.prgrms.mukvengers.domain.crewmember.dto.request.CreateCrewMemberRequest;
import com.prgrms.mukvengers.domain.crewmember.mapper.CrewMemberMapper;
import com.prgrms.mukvengers.domain.crewmember.model.CrewMember;
import com.prgrms.mukvengers.domain.crewmember.repository.CrewMemberRepository;
import com.prgrms.mukvengers.domain.user.exception.UserNotFoundException;
import com.prgrms.mukvengers.domain.user.model.User;
import com.prgrms.mukvengers.domain.user.repository.UserRepository;
import com.prgrms.mukvengers.global.common.dto.IdResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewMemberServiceImpl implements CrewMemberService {

	private final CrewMemberRepository crewMemberRepository;
	private final UserRepository userRepository;
	private final CrewRepository crewRepository;
	private final CrewMemberMapper crewMemberMapper;

	@Override
	@Transactional
	public IdResponse create(CreateCrewMemberRequest createCrewMemberRequest, Long userId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));

		Crew crew = crewRepository.findById(createCrewMemberRequest.crewId())
			.orElseThrow();

		Optional<CrewMember> optionalCrewMember = crewMemberRepository.findByIdAndUserId(
			createCrewMemberRequest.crewId(),
			userId);

		if (optionalCrewMember.isPresent()) {
			if (optionalCrewMember.get().isBlocked()) {
				throw new IllegalArgumentException();
			}
		}

		CrewMember crewMember = crewMemberMapper.toCrewMember(user, crew);

		crewMemberRepository.save(crewMember);

		return new IdResponse(crewMember.getId());
	}

}
