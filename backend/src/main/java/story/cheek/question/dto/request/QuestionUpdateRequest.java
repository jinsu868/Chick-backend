package story.cheek.question.dto.request;

import story.cheek.question.domain.Occupation;

public record QuestionUpdateRequest(
        String title,
        String content,
        Occupation occupation
) {
}
