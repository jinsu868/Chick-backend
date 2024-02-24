package story.cheek.story.dto.request;

import story.cheek.question.domain.Occupation;

public record StoryCreateRequestWithoutImage(
        Long questionId,
        Occupation occupation
) {
}
