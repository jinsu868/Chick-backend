package story.cheek.follow.dto;

import story.cheek.member.domain.Member;

public record FollowResponse(
        Long memberId,
        String name,
        String imageUrl
) {
    public static FollowResponse from(Member member) {
        return new FollowResponse(
                member.getId(),
                member.getName(),
                member.getImage()
        );
    }
}
