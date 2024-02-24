package story.cheek.search.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.follow.repository.FollowRepository;
import story.cheek.member.repository.MemberRepository;
import story.cheek.search.member.document.SearchMember;
import story.cheek.search.member.dto.SearchMemberResponse;
import story.cheek.search.member.repository.SearchMemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMemberService {
    private final SearchMemberRepository searchMemberRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public SliceResponse<SearchMemberResponse> searchMembersByName(String name) {
        List<SearchMember> searchMembersByName = searchMemberRepository.findSearchMembersByNameContains(name);
//        searchMembersByName.stream()
//                .map()

        List<SearchMemberResponse> searchMemberResponses = searchMembersByName.stream()
                .map(SearchMemberResponse::from)
                .toList();
        return SliceResponse.of(searchMemberResponses, true, "1");
    }
}
