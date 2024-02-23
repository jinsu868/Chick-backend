package story.cheek.search.dto;

import story.cheek.search.document.SearchQuestion;

public record QuestionSearchResponse(
        Long questionId,
        String occupation,
        String title,
        String content,
        String writer,
        String createdAt
) {
    public static QuestionSearchResponse from(SearchQuestion searchQuestion, String writer) {
        return new QuestionSearchResponse(
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
