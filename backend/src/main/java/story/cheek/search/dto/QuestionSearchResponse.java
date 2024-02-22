package story.cheek.search.dto;

import story.cheek.question.domain.Occupation;
import story.cheek.search.document.SearchQuestion;

public record QuestionSearchResponse(
        Long questionId,
        String occupation,
        String title,
        String content,
        String writer
) {
    public static QuestionSearchResponse from(SearchQuestion searchQuestion, String writer) {
        return new QuestionSearchResponse(
                searchQuestion.getQuestionId(),
                searchQuestion.getOccupation(),
                searchQuestion.getTitle(),
                searchQuestion.getContent(),
                writer
        );
    }
}
