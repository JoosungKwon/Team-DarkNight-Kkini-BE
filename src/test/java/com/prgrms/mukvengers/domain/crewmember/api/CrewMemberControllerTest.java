package com.prgrms.mukvengers.domain.crewmember.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import com.prgrms.mukvengers.base.ControllerTest;
import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.crewmember.dto.request.CreateCrewMemberRequest;
import com.prgrms.mukvengers.utils.CrewMemberObjectProvider;
import com.prgrms.mukvengers.utils.CrewObjectProvider;

class CrewMemberControllerTest extends ControllerTest {

	@Test
	@DisplayName("[성공] 밥 모임원을 저장한다.")
	void create_success() throws Exception {

		Crew crew = CrewObjectProvider.createCrew(savedUser, savedStore);

		crewRepository.save(crew);

		CreateCrewMemberRequest createCrewMemberRequest = CrewMemberObjectProvider.getCreateCrewMemberRequest(
			crew.getId());

		String jsonRequest = objectMapper.writeValueAsString(createCrewMemberRequest);

		mockMvc.perform(post("/api/v1/crew-members")
				.contentType(APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + ACCESS_TOKEN)
				.content(jsonRequest))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", containsString("/api/v1/crew-members")))
			.andExpect(redirectedUrlPattern("http://localhost:8080/api/v1/crew-members/*"))
			.andDo(print())
			.andDo(document("crew-member-create",
				requestFields(
					fieldWithPath("crewId").type(NUMBER).description("밥 모임 아이디"),
					fieldWithPath("blocked").type(BOOLEAN).description("강퇴 여부")
				)
			));
	}
}