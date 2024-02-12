package story.cheek.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import story.cheek.follow.dto.FollowRequest;
import story.cheek.follow.service.FollowService;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/request")
    public ResponseEntity<Void> followRequest(@CurrentMember Member member,
                                              @RequestBody FollowRequest followRequest) {
        Long followId = followService.followMember(member.getId(), followRequest);
        return ResponseEntity.created(URI.create("/api/v1/follow/" + followId)).build();
    }
}
