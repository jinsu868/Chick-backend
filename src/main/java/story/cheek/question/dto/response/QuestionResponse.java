package story.cheek.question.dto.response;

import story.cheek.question.domain.Question;

public record QuestionResponse(
        Long id,
        String title,
        String content,
        String writer
) {

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getTitle(),
                question.getContent(),
                question.getWriter().getName()
        );
    }
}
