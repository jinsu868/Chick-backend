package story.cheek.search.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.question.dto.QuestionSearchResponse;
import story.cheek.search.question.repository.QuestionSearchRepository;

@Service
@RequiredArgsConstructor
public class QuestionSearchService {
    private final QuestionSearchRepository questionSearchRepository;

    public SliceResponse<QuestionSearchResponse> searchQuestion(String search, String occupation, String cursor) {
        return questionSearchRepository.getQuestions(search, occupation, cursor);
    }
}
