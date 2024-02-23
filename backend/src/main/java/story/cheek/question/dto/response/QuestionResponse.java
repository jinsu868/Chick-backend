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
                splitTitleAndContent(question),
                question.getWriter().getName()
        );
    }

    // title 분리 메서드 입니다
    private static String splitTitleAndContent(Question question) {
        return question.getContent().substring(question.getTitle().length() + 1);
    }
}
