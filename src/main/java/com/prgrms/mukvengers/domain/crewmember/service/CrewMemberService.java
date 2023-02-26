package com.prgrms.mukvengers.domain.crewmember.service;

import com.prgrms.mukvengers.domain.crewmember.dto.request.CreateCrewMemberRequest;
import com.prgrms.mukvengers.global.common.dto.IdResponse;

public interface CrewMemberService {
	IdResponse create(CreateCrewMemberRequest createCrewMemberRequest, Long userId);
}
