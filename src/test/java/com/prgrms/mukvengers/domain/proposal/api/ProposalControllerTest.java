package com.prgrms.mukvengers.domain.proposal.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.*;
import static com.prgrms.mukvengers.utils.CrewObjectProvider.*;
import static com.prgrms.mukvengers.utils.ProposalObjectProvider.*;
import static com.prgrms.mukvengers.utils.UserObjectProvider.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.epages.restdocs.apispec.Schema;
import com.prgrms.mukvengers.base.ControllerTest;
import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.crew.model.vo.CrewStatus;
import com.prgrms.mukvengers.domain.proposal.model.Proposal;
import com.prgrms.mukvengers.domain.user.model.User;

class ProposalControllerTest extends ControllerTest {

	public static final Schema GET_PROPOSALS_BY_LEADER_ID_PROPOSAL_RESPONSE = new Schema(
		"getProposalsByLeaderIdResponse");

	@Test
	@DisplayName("[성공] 사용자가 방장인 모임의 모든 신청서를 조회한다.")
	void getProposalsByLeaderId_success() throws Exception {

		User user = createUser("1232456789");
		userRepository.save(user);

		Crew crew = createCrew(savedStore, CrewStatus.RECRUITING);
		crewRepository.save(crew);

		List<Proposal> proposals = createProposals(user, savedUser.getId(), crew.getId());
		proposalRepository.saveAll(proposals);

		mockMvc.perform(get("/api/v1/proposals/leader")
				.header(AUTHORIZATION, BEARER_TYPE + accessToken)
				.accept(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists())
			.andDo(print())
			.andDo(document("proposal-getProposalsByLeaderId",
				resource(
					builder()
						.tag(PROPOSAL)
						.summary("사용자가 방장인 모임의 모든 신청서 조회")
						.description("사용자가 방장인 모임에서 모임 신청을 위해 작서된 신청서를 모두 조회를 위한 API 입니다.")
						.responseSchema(GET_PROPOSALS_BY_LEADER_ID_PROPOSAL_RESPONSE)
						.responseFields(
							fieldWithPath("data.responses.[].user.id").type(NUMBER).description("유저 ID"),
							fieldWithPath("data.responses.[].user.nickname").type(STRING).description("닉네임"),
							fieldWithPath("data.responses.[].user.profileImgUrl").type(STRING).description("프로필 이미지"),
							fieldWithPath("data.responses.[].user.introduction").type(STRING).description("한줄 소개"),
							fieldWithPath("data.responses.[].user.leaderCount").type(NUMBER).description("방장 횟수"),
							fieldWithPath("data.responses.[].user.crewCount").type(NUMBER).description("모임 참여 횟수"),
							fieldWithPath("data.responses.[].user.tasteScore").type(NUMBER).description("맛잘알 점수"),
							fieldWithPath("data.responses.[].user.mannerScore").type(NUMBER).description("매너 온도"),
							fieldWithPath("data.responses.[].user.mannerScore").type(NUMBER).description("매너 온도"),
							fieldWithPath("data.responses.[].id").type(NUMBER).description("신청서 아이디"),
							fieldWithPath("data.responses.[].content").type(STRING).description("신청서 내용"),
							fieldWithPath("data.responses.[].status").type(STRING).description("신청서 상태"),
							fieldWithPath("data.responses.[].leaderId").type(NUMBER).description("모임의 방장 아이디"),
							fieldWithPath("data.responses.[].crewId").type(NUMBER).description("모임 아이디")
						)
						.build()
				)
			));

	}
}