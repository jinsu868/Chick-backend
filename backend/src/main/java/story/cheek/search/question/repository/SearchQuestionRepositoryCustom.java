package story.cheek.search.question.repository;

import story.cheek.common.dto.SliceResponse;
import story.cheek.search.question.dto.SearchQuestionResponse;

public interface SearchQuestionRepositoryCustom {
    SliceResponse<SearchQuestionResponse> getQuestions(String search, String occupation, String cursor);
}
