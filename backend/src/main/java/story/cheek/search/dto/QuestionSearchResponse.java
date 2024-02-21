package story.cheek.search.dto;

import story.cheek.search.document.SearchQuestion;

public record QuestionSearchResponse(
        Long id,
        String title,
        String content
) {
    public static QuestionSearchResponse from(SearchQuestion searchQuestion) {
        return new QuestionSearchResponse(
                searchQuestion.getQuestionId(),
                searchQuestion.getTitle(),
                searchQuestion.getContent()
        );
    }
}
