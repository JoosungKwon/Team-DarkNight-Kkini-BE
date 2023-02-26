package com.prgrms.mukvengers.utils;

import com.prgrms.mukvengers.domain.crewmember.dto.request.CreateCrewMemberRequest;

public class CrewMemberObjectProvider {

	public static CreateCrewMemberRequest getCreateCrewMemberRequest(Long crewId) {
		return new CreateCrewMemberRequest(crewId, false);
	}
}
