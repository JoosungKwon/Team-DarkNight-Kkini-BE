package com.prgrms.mukvengers.domain.crew.mapper;

import static org.mapstruct.ReportingPolicy.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prgrms.mukvengers.domain.crew.dto.request.CreateCrewRequest;
import com.prgrms.mukvengers.domain.crew.dto.response.CrewResponse;
import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.store.model.Store;

@Mapper(componentModel = "spring", unmappedSourcePolicy = IGNORE)
public interface CrewMapper {

	@Mapping(target = "category", source = "createCrewRequest.category")
	@Mapping(target = "promiseTime", source = "createCrewRequest.promiseTime")
	@Mapping(target = "store", source = "store")
	@Mapping(target = "location", source = "store.location")
	Crew toCrew(CreateCrewRequest createCrewRequest, Store store);

	@Mapping(target = "promiseTime", source = "crew.promiseTime")
	CrewResponse toCrewResponse(Crew crew, Integer currentMember);

}


