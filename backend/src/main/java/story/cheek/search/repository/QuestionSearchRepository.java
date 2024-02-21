package story.cheek.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.document.SearchQuestion;

import java.util.List;
import java.util.Optional;

public interface QuestionSearchRepository extends ElasticsearchRepository<SearchQuestion, Long> {
    Page<SearchQuestion> findQuestionByTitle(Pageable pageable, String questionTitle);

    List<SearchQuestion> findSearchQuestionsByTitle(String questionTitle);

    Optional<SearchQuestion> findQuestionByQuestionId(Long questionId);
}