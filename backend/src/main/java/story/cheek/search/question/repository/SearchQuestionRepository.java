package story.cheek.search.question.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.question.document.SearchQuestion;

public interface SearchQuestionRepository extends ElasticsearchRepository<SearchQuestion, Long>, SearchQuestionRepositoryCustom {
}