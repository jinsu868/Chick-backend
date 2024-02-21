package story.cheek.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.document.SearchQuestion;
import story.cheek.search.dto.QuestionSearchResponse;
import story.cheek.search.repository.QuestionSearchRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionSearchService {
    private final QuestionSearchRepository questionSearchRepository;

    public SliceResponse<QuestionSearchResponse> searchQuestion(String title) {
        List<SearchQuestion> searchQuestionByTitle = questionSearchRepository.findSearchQuestionsByTitle(title);
        List<QuestionSearchResponse> questionSearchResponses = searchQuestionByTitle.stream()
                .map(QuestionSearchResponse::from).toList();

        return SliceResponse.of(questionSearchResponses, true, "1");
    }
}
