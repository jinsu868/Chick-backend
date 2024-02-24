package story.cheek.search.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.member.dto.SearchMemberResponse;
import story.cheek.search.member.service.SearchMemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/members")
public class SearchMemberController {
    private final SearchMemberService searchMemberService;

    @GetMapping("members")
    public SliceResponse<SearchMemberResponse> searchMembers(@RequestParam("name") String name) {
        return searchMemberService.searchMembersByName(name);
    }
}
