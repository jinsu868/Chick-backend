package story.cheek.search.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.search.question.dto.SearchQuestionResponse;
import story.cheek.search.question.service.SearchQuestionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/questions")
public class SearchQuestionController {
    private final SearchQuestionService searchQuestionService;

    @GetMapping
    public SliceResponse<SearchQuestionResponse> searchQuestions(@RequestParam("search") String search,
                                                                 @RequestParam("occupation") String occupation,
                                                                 @RequestParam("cursor") String cursor) {
        return searchQuestionService.searchQuestion(search, occupation, cursor);
    }
}
