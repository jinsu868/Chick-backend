package story.cheek.follow.dto;

import story.cheek.follow.domain.Follow;
import story.cheek.member.domain.Member;

//@Getter
public record FollowRequest(
        Long followerId
) {
    public Follow toEntity(Member following, Member follower) {
        return Follow.builder()
                .followingMember(following)
                .followerMember(follower)
                .build();
    }
}
