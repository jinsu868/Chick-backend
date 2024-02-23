package story.cheek.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.document.SearchMember;
import story.cheek.search.dto.MemberSearchResponse;
import story.cheek.search.repository.MemberSearchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSearchService {
    private final MemberSearchRepository memberSearchRepository;

    public SliceResponse<MemberSearchResponse> searchMembersByTitle(String name) {
        List<SearchMember> searchMembersByName = memberSearchRepository.findSearchMembersByNameContains(name);
        List<MemberSearchResponse> memberSearchResponses = searchMembersByName.stream()
                .map(MemberSearchResponse::from)
                .toList();
        return SliceResponse.of(memberSearchResponses, true, "1");
    }
}
