package com.prgrms.mukvengers.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.mukvengers.domain.crew.exception.CrewNotFoundException;
import com.prgrms.mukvengers.domain.crew.model.Crew;
import com.prgrms.mukvengers.domain.crew.repository.CrewRepository;
import com.prgrms.mukvengers.domain.crewmember.model.vo.Role;
import com.prgrms.mukvengers.domain.crewmember.repository.CrewMemberRepository;
import com.prgrms.mukvengers.domain.review.dto.request.CreateLeaderReviewRequest;
import com.prgrms.mukvengers.domain.review.dto.request.CreateMemberReviewRequest;
import com.prgrms.mukvengers.domain.review.dto.response.ReviewResponse;
import com.prgrms.mukvengers.domain.review.mapper.ReviewMapper;
import com.prgrms.mukvengers.domain.review.model.Review;
import com.prgrms.mukvengers.domain.review.repository.ReviewRepository;
import com.prgrms.mukvengers.domain.user.exception.UserNotFoundException;
import com.prgrms.mukvengers.domain.user.model.User;
import com.prgrms.mukvengers.domain.user.repository.UserRepository;
import com.prgrms.mukvengers.global.common.dto.IdResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

	private final UserRepository userRepository;
	private final CrewRepository crewRepository;
	private final CrewMemberRepository crewMemberRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;

	@Override
	@Transactional
	public IdResponse createLeaderReview(CreateLeaderReviewRequest leaderReviewRequest, Long reviewerId,
		Long crewId) {

		User reviewer = userRepository.findById(reviewerId)
			.orElseThrow(() -> new UserNotFoundException(reviewerId));

		User reviewee = userRepository.findById(leaderReviewRequest.leaderId())
			.orElseThrow(() -> new UserNotFoundException(leaderReviewRequest.leaderId()));

		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new CrewNotFoundException(crewId));

		crewMemberRepository.findCrewMemberByCrewIdAndUserId(crewId, reviewee.getId())
			.filter(crewMember -> crewMember.getRole() == Role.LEADER)
			.orElseThrow(() -> new IllegalArgumentException("해당 밥모임원의 리더가 아닙니다."));

		crewMemberRepository.findCrewMemberByCrewIdAndUserId(crewId, reviewer.getId())
			.filter(crewMember -> crewMember.getRole() == Role.MEMBER)
			.orElseThrow(() -> new IllegalArgumentException("해당 밥모임원의 참여자가 아닙니다."));

		Review review = reviewMapper.toReview(leaderReviewRequest, reviewer, reviewee, crew);

		Review saveReview = reviewRepository.save(review);
		return new IdResponse(saveReview.getId());
	}

	@Override
	@Transactional
	public IdResponse createMemberReview(CreateMemberReviewRequest memberReviewRequest, Long reviewerId, Long crewId) {

		User reviewer = userRepository.findById(reviewerId)
			.orElseThrow(() -> new UserNotFoundException(reviewerId));

		User reviewee = userRepository.findById(memberReviewRequest.revieweeId())
			.orElseThrow(() -> new UserNotFoundException(memberReviewRequest.revieweeId()));

		Crew crew = crewRepository.findById(crewId)
			.orElseThrow(() -> new CrewNotFoundException(crewId));

		crewMemberRepository.findCrewMemberByCrewIdAndUserId(crewId, reviewee.getId())
			.filter(crewMember -> crewMember.getRole() == Role.MEMBER)
			.orElseThrow(() -> new IllegalArgumentException("해당 밥모임원의 참여자가 아닙니다."));

		crewMemberRepository.findCrewMemberByCrewIdAndUserId(crewId, reviewer.getId())
			.filter(crewMember -> crewMember.getRole() == Role.MEMBER)
			.orElseThrow(() -> new IllegalArgumentException("해당 밥모임원의 참여자가 아닙니다."));

		Review review = reviewMapper.toReview(memberReviewRequest, reviewer, reviewee, crew);

		Review saveReview = reviewRepository.save(review);
		return new IdResponse(saveReview.getId());
	}

	@Override
	public ReviewResponse getSingleReview(Long reviewId, Long userId) {

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new IllegalArgumentException("해당 리뷰는 존재하지 않는 리뷰입니다."));

		if (!review.getReviewer().isSameUser(userId) && !review.getReviewee().isSameUser(userId)) {
			throw new IllegalArgumentException("해당 리뷰를 볼 수 있는 접근 권한이 없습니다.");
		}

		return reviewMapper.toReviewResponse(review);
	}
}
