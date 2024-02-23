package story.cheek.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.dto.QuestionSearchResponse;
import story.cheek.search.repository.QuestionSearchRepository;

@Service
@RequiredArgsConstructor
public class QuestionSearchService {
    private final QuestionSearchRepository questionSearchRepository;

    public SliceResponse<QuestionSearchResponse> searchQuestion(String search, String occupation, String cursor) {
        return questionSearchRepository.getQuestions(search, occupation, cursor);
    }
}
