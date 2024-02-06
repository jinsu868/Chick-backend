package story.cheek.scrap.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
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
}
