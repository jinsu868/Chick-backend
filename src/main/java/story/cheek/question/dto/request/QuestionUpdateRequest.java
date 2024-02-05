package story.cheek.question.dto.request;

import story.cheek.question.Occupation;

public record QuestionUpdateRequest(
        String title,
        String content,
        Occupation occupation
) {
}
