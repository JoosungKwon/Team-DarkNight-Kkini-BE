package com.prgrms.mukvengers.domain.crewmember.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.prgrms.mukvengers.domain.crewmember.model.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

	Optional<CrewMember> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

}
