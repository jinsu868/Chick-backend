package story.cheek.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.document.SearchMember;

import java.util.List;

public interface MemberSearchRepository extends ElasticsearchRepository<SearchMember, Long> {
    List<SearchMember> findSearchMembersByNameContains(String name);

}
