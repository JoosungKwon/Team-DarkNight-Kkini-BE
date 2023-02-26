package com.prgrms.mukvengers.domain.crewmember.api;

import static org.springframework.http.MediaType.*;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prgrms.mukvengers.domain.crewmember.dto.request.CreateCrewMemberRequest;
import com.prgrms.mukvengers.domain.crewmember.service.CrewMemberService;
import com.prgrms.mukvengers.global.common.dto.IdResponse;
import com.prgrms.mukvengers.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew-members")
public class CrewMemberController {

	private final CrewMemberService crewMemberService;

	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<URI> create(
		@RequestBody @Valid CreateCrewMemberRequest createCrewMemberRequest,
		@AuthenticationPrincipal JwtAuthentication user) {

		IdResponse idResponse = crewMemberService.create(createCrewMemberRequest, user.id());

		String createURL = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() + "/" + idResponse.id();

		return ResponseEntity.created(URI.create(createURL)).build();
	}
}
