package story.cheek.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.notification.dto.request.FcmTokenCreateRequest;
import story.cheek.notification.service.FcmTokenService;
import story.cheek.security.CurrentMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping("/token")
    public ResponseEntity<Void> create(
            @CurrentMember Member member,
            @RequestBody FcmTokenCreateRequest request) {

        fcmTokenService.save(member, request);

        return ResponseEntity.ok().build();
    }
}
