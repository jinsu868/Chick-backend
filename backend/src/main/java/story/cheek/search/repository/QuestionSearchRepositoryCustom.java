package story.cheek.search.repository;

import story.cheek.common.dto.SliceResponse;
import story.cheek.search.dto.QuestionSearchResponse;

public interface QuestionSearchRepositoryCustom {
    SliceResponse<QuestionSearchResponse> getQuestions(String search, String occupation, String cursor);
}
