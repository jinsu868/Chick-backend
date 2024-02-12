package story.cheek.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.follow.domain.Follow;
import story.cheek.follow.dto.FollowRequest;
import story.cheek.follow.dto.FollowResponse;
import story.cheek.follow.repository.FollowRepository;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public Long followMember(Long followingId, FollowRequest followRequest) {
        Member followingMember = findMember(followingId);
        Member followerMember = findMember(followRequest.followerId());

        Follow follow = followRepository.save(followRequest.toEntity(followingMember, followerMember));
        followingMember.addFollowingMemberList(follow);
        followerMember.addFollowerMemberList(follow);

        return follow.getId();
    }

    public List<FollowResponse> getFollowingMembers(Long memberId) {
        Member member = findMember(memberId);
        List<Follow> followingList = member.getFollowingList();
        return followingList.stream()
                .map(Follow::getFollowerMember)
                .map(FollowResponse::from)
                .collect(Collectors.toList());
    }

    public List<FollowResponse> getFollowers(Long memberId) {
        Member member = findMember(memberId);
        List<Follow> followingList = member.getFollowerList();
        return followingList.stream()
                .map(Follow::getFollowingMember)
                .map(FollowResponse::from)
                .collect(Collectors.toList());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
