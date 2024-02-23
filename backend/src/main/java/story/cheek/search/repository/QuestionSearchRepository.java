package story.cheek.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.document.SearchQuestion;

public interface QuestionSearchRepository extends ElasticsearchRepository<SearchQuestion, Long>, QuestionSearchRepositoryCustom {
}