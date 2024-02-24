package story.cheek.search.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.follow.repository.FollowRepository;
import story.cheek.member.repository.MemberRepository;
import story.cheek.search.member.document.SearchMember;
import story.cheek.search.member.dto.MemberSearchResponse;
import story.cheek.search.member.repository.MemberSearchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSearchService {
    private final MemberSearchRepository memberSearchRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public SliceResponse<MemberSearchResponse> searchMembersByName(String name) {
        List<SearchMember> searchMembersByName = memberSearchRepository.findSearchMembersByNameContains(name);
//        searchMembersByName.stream()
//                .map()

        List<MemberSearchResponse> memberSearchResponses = searchMembersByName.stream()
                .map(MemberSearchResponse::from)
                .toList();
        return SliceResponse.of(memberSearchResponses, true, "1");
    }
}
