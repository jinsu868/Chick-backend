package story.cheek.search.question.dto;

import story.cheek.search.question.document.SearchQuestion;

public record SearchQuestionResponse(
        Long questionId,
        String occupation,
        String title,
        String content,
        String writer,
        String createdAt
) {
    public static SearchQuestionResponse from(SearchQuestion searchQuestion, String writer) {
        return new SearchQuestionResponse(
                searchQuestion.getQuestionId(),
                searchQuestion.getOccupation(),
                searchQuestion.getTitle(),
                splitTitleAndContent(searchQuestion),
                writer,
                searchQuestion.getCreatedAt()
        );
    }

    private static String splitTitleAndContent(SearchQuestion searchQuestion) {
        return searchQuestion.getContent().substring(searchQuestion.getTitle().length() + 1);
    }
}
