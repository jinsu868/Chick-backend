package story.cheek.highlight.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.common.dto.SliceResponse;
import story.cheek.highlight.dto.request.HighlightCreateRequest;
import story.cheek.highlight.dto.request.HighlightStoryCreateRequest;
import story.cheek.highlight.dto.response.HighlightResponse;
import story.cheek.highlight.service.HighlightService;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/highlights")
public class HighlightController {

    private final HighlightService highlightService;

    @PostMapping
    public ResponseEntity<Void> create(
            @CurrentMember Member member,
            @RequestBody HighlightCreateRequest request
    ) {

        Long highlightId = highlightService.save(member, request);

        return ResponseEntity.created(URI.create("/api/v1/highlights/" + highlightId))
                .build();
    }

    @DeleteMapping("/{highlightId}")
    public ResponseEntity<Void> delete(
            @CurrentMember Member member,
            @PathVariable Long highlightId
    ) {

        highlightService.delete(member, highlightId);

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse<HighlightResponse>> findAllMyHighlight(
        @RequestParam(defaultValue = "5") int pageSize,
        @CurrentMember Member member,
        @RequestParam(required = false) String cursor
    ) {

        SliceResponse<HighlightResponse> response = highlightService.findAll(pageSize, member.getId(), cursor);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/members")
    public ResponseEntity<SliceResponse<HighlightResponse>> findAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam("id") Long memberId,
            @RequestParam(required = false) String cursor
    ) {
        // TODO 기획 : 하이라이트 정렬 기준은?

        SliceResponse<HighlightResponse> response = highlightService.findAll(pageSize, memberId, cursor);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/stories")
    public ResponseEntity<Void> addStory(
            @CurrentMember Member member,
            @RequestBody HighlightStoryCreateRequest request
    ) {

        Long storyId = highlightService.saveStoryHighlight(member, request);

        return ResponseEntity.created(URI.create("/api/v1/highlights/stories/" + storyId))
                .build();
    }

    @DeleteMapping("/stories/{storyId}")
    public ResponseEntity<Void> removeStory(
            @CurrentMember Member member,
            @PathVariable Long storyId,
            @RequestParam("highlightId") Long highlightId
    ) {

        highlightService.deleteStoryHighlight(member, storyId, highlightId);

        return ResponseEntity.noContent()
                .build();
    }
}
