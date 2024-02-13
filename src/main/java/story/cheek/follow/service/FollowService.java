package story.cheek.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.constant.SortType;
import story.cheek.common.dto.SliceResponse;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
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

    public Long followMember(Long followingId, FollowRequest followRequest) {
        Member followingMember = findMember(followingId);
        Member follower = findMember(followRequest.followerId());

        Follow follow = followRepository.save(followRequest.toEntity(followingMember, follower));
        followingMember.addFollowingMemberList(follow);
        follower.addFollowerList(follow);

        return follow.getId();
    }

    public SliceResponse<FollowResponse> getFollows(Member member, SortType sortType, String cursor) {
        if (sortType.equals(SortType.FOLLOWING)) {
            return followRepository.findFollowingMembersByOrderByFollowingSequenceNumberDesc(sortType, member, cursor);
        }

        return followRepository.findFollowersByOrderByFollowerSequenceNumberDesc(sortType, member, cursor);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
