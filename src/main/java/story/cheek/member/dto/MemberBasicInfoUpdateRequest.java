package story.cheek.member.dto;

import story.cheek.question.domain.Occupation;

public record MemberBasicInfoUpdateRequest(
        String name,
        Occupation occupation,
        String description
) {
}
