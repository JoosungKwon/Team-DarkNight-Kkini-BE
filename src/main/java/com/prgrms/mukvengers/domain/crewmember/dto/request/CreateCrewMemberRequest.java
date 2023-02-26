package com.prgrms.mukvengers.domain.crewmember.dto.request;

import javax.validation.constraints.NotNull;

public record CreateCrewMemberRequest(
	@NotNull Long crewId,
	boolean blocked
) {
}
