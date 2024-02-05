package story.cheek.question.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.question.dto.request.QuestionCreateRequest;
import story.cheek.question.service.QuestionService;
import story.cheek.security.CurrentMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Void> create(
            @CurrentMember Member member,
            @RequestBody QuestionCreateRequest questionCreateRequest
    ) {

        Long savedId = questionService.save(member.getEmail(), questionCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/questions/" + savedId))
                .build();

    }
}
