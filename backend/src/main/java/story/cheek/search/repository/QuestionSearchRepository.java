package story.cheek.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.document.SearchQuestion;

import java.util.List;

public interface QuestionSearchRepository extends ElasticsearchRepository<SearchQuestion, Long> {
    List<SearchQuestion> findSearchQuestionsByContentContainsAndOccupation(String search, String occupation);
}