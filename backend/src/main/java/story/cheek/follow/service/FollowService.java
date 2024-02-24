package story.cheek.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.constant.SortType;
import story.cheek.common.dto.SliceResponse;
import story.cheek.common.exception.*;
import story.cheek.follow.domain.Follow;
import story.cheek.follow.dto.FollowRequest;
import story.cheek.follow.dto.FollowResponse;
import story.cheek.follow.repository.FollowRepository;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public Long followMember(Member followingMember, FollowRequest followRequest) {
        Member followRequestdMember = findMember(followingMember.getId());
        Member follower = findMember(followRequest.followerId());

        validateFollow(followRequestdMember, follower);

        Follow follow = followRepository.save(followRequest.toEntity(followRequestdMember, follower));
        followRequestdMember.addFollowingMemberList(follow);
        follower.addFollowerList(follow);

        return follow.getId();
    }

    public SliceResponse<FollowResponse> getFollows(Member member, SortType sortType, String cursor) {
        if (sortType.equals(SortType.FOLLOWING)) {
            return followRepository.findFollowingMembersByOrderByFollowingSequenceNumberDesc(sortType, member, cursor);
        }

        return followRepository.findFollowersByOrderByFollowerSequenceNumberDesc(sortType, member, cursor);
    }

    public void deleteFollow(Member member, Long followId) {
        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new NotFoundFollowException(ErrorCode.FOLLOW_NOT_FOUND));

        if (member.hasAuthority(follow.getFollowingMember().getId())) {
            throw new ForbiddenFollowException(ErrorCode.FORBIDDEN_FOLLOW_DELETE);
        }

        followRepository.deleteById(followId);
    }

    private void validateFollow(Member followRequestdMember, Member follower) {
        if (followRepository.existsFollowByFollowingMemberAndFollower(followRequestdMember, follower)) {
            throw new DuplicateFollowException(ErrorCode.DUPLICATED_FOLLOW);
        }

        if (followRequestdMember.getId().equals(follower.getId())) {
            throw new SelfFollowException(ErrorCode.SELF_FOLLOW);
        }
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
