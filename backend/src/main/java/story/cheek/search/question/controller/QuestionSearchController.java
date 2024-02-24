package story.cheek.search.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.question.dto.QuestionSearchResponse;
import story.cheek.search.question.service.QuestionSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/questions")
public class QuestionSearchController {
    private final QuestionSearchService questionSearchService;

    @GetMapping("questions")
    public SliceResponse<QuestionSearchResponse> searchQuestions(@RequestParam("search") String search,
                                                                 @RequestParam("occupation") String occupation,
                                                                 @RequestParam("cursor") String cursor) {
        return questionSearchService.searchQuestion(search, occupation, cursor);
    }
}
