package story.cheek.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.question.domain.Occupation;
import story.cheek.search.dto.MemberSearchResponse;
import story.cheek.search.dto.QuestionSearchResponse;
import story.cheek.search.service.MemberSearchService;
import story.cheek.search.service.QuestionSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
    private final QuestionSearchService questionSearchService;
    private final MemberSearchService memberSearchService;

    @GetMapping("questions")
    public SliceResponse<QuestionSearchResponse> searchQuestions(@RequestParam("title") String title,
                                                                 @RequestParam("occupation") String occupation) {
        return questionSearchService.searchQuestion(title, title, occupation);
    }

    @GetMapping("members")
    public SliceResponse<MemberSearchResponse> searchMembers(@RequestParam("name") String name) {
        return memberSearchService.searchMembersByTitle(name);
    }
}
