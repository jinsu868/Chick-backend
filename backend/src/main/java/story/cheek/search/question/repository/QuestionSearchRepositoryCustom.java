package story.cheek.search.question.repository;

import story.cheek.common.dto.SliceResponse;
import story.cheek.search.question.dto.QuestionSearchResponse;

public interface QuestionSearchRepositoryCustom {
    SliceResponse<QuestionSearchResponse> getQuestions(String search, String occupation, String cursor);
}
