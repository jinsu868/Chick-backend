package story.cheek.search.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.follow.repository.FollowRepository;
import story.cheek.member.repository.MemberRepository;
import story.cheek.search.member.dto.SearchMemberResponse;
import story.cheek.search.member.repository.SearchMemberRepository;

@Service
@RequiredArgsConstructor
public class SearchMemberService {
    private final SearchMemberRepository searchMemberRepository;

    public SliceResponse<SearchMemberResponse> searchMembersByName(String name, String cursor) {
        return searchMemberRepository.getMembersByName(name, cursor);
    }
}
