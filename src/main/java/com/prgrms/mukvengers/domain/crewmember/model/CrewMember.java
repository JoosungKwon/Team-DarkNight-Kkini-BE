package com.prgrms.mukvengers.domain.crewmember.model;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.springframework.util.Assert.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Point;

import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.user.model.User;
import com.prgrms.mukvengers.global.common.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE crew_member set deleted = true where id=?")
public class CrewMember extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "crew_id", referencedColumnName = "id")
	private Crew crew;

	@Column(nullable = false)
	private boolean blocked;

	@Column(nullable = false)
	private boolean ready;

	@Builder
	protected CrewMember(User user, Crew crew) {
		this.user = user;
		this.crew = crew;
	}

	public void validateUser(User user) {
		notNull(user, "유효하지 않는 사용자입니다.");
	}

	public void validateCrew(Crew crew) {
		notNull(crew, "유효하지 않는 모임입니다.");
	}

}
