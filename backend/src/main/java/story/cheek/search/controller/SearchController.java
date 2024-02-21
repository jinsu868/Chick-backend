package story.cheek.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.dto.QuestionSearchResponse;
import story.cheek.search.service.QuestionSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
    private final QuestionSearchService questionSearchService;

    @GetMapping
    public SliceResponse<QuestionSearchResponse> searchQuestion(@RequestParam("title") String title) {
        return questionSearchService.searchQuestion(title);
    }
}
