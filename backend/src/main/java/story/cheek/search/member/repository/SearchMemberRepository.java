package story.cheek.search.member.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.member.document.SearchMember;

import java.util.List;

public interface SearchMemberRepository extends ElasticsearchRepository<SearchMember, Long> , SearchMemberRepositoryCustom {
    List<SearchMember> findSearchMembersByNameContains(String name);
}
