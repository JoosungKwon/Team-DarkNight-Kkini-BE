package com.prgrms.mukvengers.domain.crewmember.mapper;

import static org.mapstruct.ReportingPolicy.*;

import org.mapstruct.Mapper;

import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.crewmember.model.CrewMember;
import com.prgrms.mukvengers.domain.user.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface CrewMemberMapper {

	CrewMember toCrewMember(User user, Crew crew);

}
