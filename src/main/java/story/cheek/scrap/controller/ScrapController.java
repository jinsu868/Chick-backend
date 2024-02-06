package story.cheek.scrap.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.scrap.dto.response.ScrapResponse;
import story.cheek.scrap.service.ScrapService;
import story.cheek.security.CurrentMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scraps")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping("/{storyId}")
    public ResponseEntity<Void> create(
            @CurrentMember Member member,
            @PathVariable Long storyId

    ) {

        Long scrapId = scrapService.save(member, storyId);

        return ResponseEntity.created(URI.create("/api/v1/scraps/" + scrapId))
                .build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<ScrapResponse>> findAll(
            @PathVariable Long memberId
    ) {
        List<ScrapResponse> response = scrapService.findAllByMemberId(memberId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{scrapId}")
    public ResponseEntity<Void> delete(
            @CurrentMember Member member,
            @PathVariable Long scrapId
    ) {
        scrapService.delete(member, scrapId);

        return ResponseEntity.noContent().build();
    }
}
