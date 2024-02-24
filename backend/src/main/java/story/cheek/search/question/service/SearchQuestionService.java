package story.cheek.search.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.question.dto.SearchQuestionResponse;
import story.cheek.search.question.repository.SearchQuestionRepository;

@Service
@RequiredArgsConstructor
public class SearchQuestionService {
    private final SearchQuestionRepository searchQuestionRepository;

    public SliceResponse<SearchQuestionResponse> searchQuestion(String search, String occupation, String cursor) {
        return searchQuestionRepository.getQuestions(search, occupation, cursor);
    }
}
