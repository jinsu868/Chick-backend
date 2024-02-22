package story.cheek.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.question.domain.Occupation;
import story.cheek.search.document.SearchQuestion;

import java.util.List;

public interface QuestionSearchRepository extends ElasticsearchRepository<SearchQuestion, Long> {
    List<SearchQuestion> findSearchQuestionsByTitleContainsOrContentContainsAndOccupationIgnoreCaseOrderByQuestionIdDesc(String title, String content, String occupation);
}