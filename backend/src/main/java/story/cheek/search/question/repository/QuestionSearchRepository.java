package story.cheek.search.question.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.question.document.SearchQuestion;

public interface QuestionSearchRepository extends ElasticsearchRepository<SearchQuestion, Long>, QuestionSearchRepositoryCustom {
}