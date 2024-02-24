package story.cheek.search.member.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import story.cheek.search.member.document.SearchMember;

import java.util.List;

public interface MemberSearchRepository extends ElasticsearchRepository<SearchMember, Long> , MemberSearchRepositoryCustom{
    List<SearchMember> findSearchMembersByNameContains(String name);
}
