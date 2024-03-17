package story.cheek.notification.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.notification.dto.response.NotificationDetailResponse;
import story.cheek.notification.service.NotificationService;
import story.cheek.security.CurrentMember;
import story.cheek.story.dto.request.NotificationDeleteRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDetailResponse>> findAll(@CurrentMember Member member) {

        List<NotificationDetailResponse> response = notificationService.findAll(member);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Void> readNotification(
            @CurrentMember Member member,
            @PathVariable Long notificationId) {

        notificationService.read(member, notificationId);

        return ResponseEntity.noContent()
                .build();
    }

    //DeleteMapping
    @PutMapping
    public ResponseEntity<Void> delete(
            @CurrentMember Member member,
            @RequestBody NotificationDeleteRequest request
    ) {

        notificationService.deleteNotifications(member, request);

        return ResponseEntity.noContent()
                .build();

    }

}
