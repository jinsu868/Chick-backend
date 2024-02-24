package story.cheek.search.member.repository;

import story.cheek.common.dto.SliceResponse;
import story.cheek.search.member.dto.SearchMemberResponse;

public interface SearchMemberRepositoryCustom {
    SliceResponse<SearchMemberResponse> getMembersByName(String name, String cursor);
}
