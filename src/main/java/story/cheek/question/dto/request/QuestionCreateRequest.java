package story.cheek.question.dto.request;

import story.cheek.question.Occupation;

public record QuestionCreateRequest(
        String title,
        String content,
        Occupation occupation
) {
}
