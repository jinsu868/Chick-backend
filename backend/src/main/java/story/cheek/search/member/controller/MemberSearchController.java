package story.cheek.search.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.member.dto.MemberSearchResponse;
import story.cheek.search.member.service.MemberSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/members")
public class MemberSearchController {
    private final MemberSearchService memberSearchService;

    @GetMapping("members")
    public SliceResponse<MemberSearchResponse> searchMembers(@RequestParam("name") String name) {
        return memberSearchService.searchMembersByName(name);
    }
}
