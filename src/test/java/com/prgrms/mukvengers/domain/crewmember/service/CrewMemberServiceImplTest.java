package com.prgrms.mukvengers.domain.crewmember.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.mukvengers.base.ServiceTest;
import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.crew.repository.CrewRepository;
import com.prgrms.mukvengers.domain.crewmember.dto.request.CreateCrewMemberRequest;
import com.prgrms.mukvengers.domain.crewmember.model.CrewMember;
import com.prgrms.mukvengers.domain.crewmember.repository.CrewMemberRepository;
import com.prgrms.mukvengers.domain.store.model.Store;
import com.prgrms.mukvengers.domain.store.repository.StoreRepository;
import com.prgrms.mukvengers.domain.user.model.User;
import com.prgrms.mukvengers.domain.user.repository.UserRepository;
import com.prgrms.mukvengers.global.common.dto.IdResponse;
import com.prgrms.mukvengers.utils.CrewMemberObjectProvider;
import com.prgrms.mukvengers.utils.CrewObjectProvider;
import com.prgrms.mukvengers.utils.StoreObjectProvider;
import com.prgrms.mukvengers.utils.UserObjectProvider;

class CrewMemberServiceImplTest extends ServiceTest {

	@Autowired
	private CrewMemberService crewMemberService;

	@Autowired
	private CrewMemberRepository crewMemberRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CrewRepository crewRepository;

	@Test
	@Transactional
	@DisplayName("[성공] 밥 모임원을 생성할 수 있다.")
	void create_success() {

		User user = UserObjectProvider.createUser();

		userRepository.save(user);

		Store store = StoreObjectProvider.createStore();

		storeRepository.save(store);

		Crew crew = CrewObjectProvider.createCrew(user, store);

		crewRepository.save(crew);

		CreateCrewMemberRequest createCrewMemberRequest = CrewMemberObjectProvider.getCreateCrewMemberRequest(
			crew.getId());

		IdResponse idResponse = crewMemberService.create(createCrewMemberRequest, user.getId());

		Optional<CrewMember> optionalCrewMember = crewMemberRepository.findById(idResponse.id());

		assertThat(crewMemberRepository.count()).isNotZero();
		assertThat(optionalCrewMember).isPresent();
		CrewMember crewMember = optionalCrewMember.get();
		assertThat(crewMember)
			.hasFieldOrPropertyWithValue("user", user)
			.hasFieldOrPropertyWithValue("crew", crew)
			.hasFieldOrPropertyWithValue("blocked", false)
			.hasFieldOrPropertyWithValue("blocked", false);

	}
}