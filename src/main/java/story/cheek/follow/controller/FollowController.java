package story.cheek.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import story.cheek.follow.dto.FollowRequest;
import story.cheek.follow.dto.FollowResponse;
import story.cheek.follow.service.FollowService;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/request")
    public ResponseEntity<Void> followRequest(@CurrentMember Member member,
                                              @RequestBody FollowRequest followRequest) {
        Long followId = followService.followMember(member.getId(), followRequest);
        return ResponseEntity.created(URI.create("/api/v1/follow/" + followId)).build();
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> getFollowingMembers(@CurrentMember Member member) {
        return ResponseEntity.ok().body(followService.getFollowingMembers(member.getId()));
    }

    @GetMapping("/follower")
    public ResponseEntity<List<FollowResponse>> getFollowerMembers(@CurrentMember Member member) {
        return ResponseEntity.ok().body(followService.getFollowers(member.getId()));
    }
}
