package story.cheek.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.member.dto.MemberResponse;
import story.cheek.member.service.MemberService;
import story.cheek.security.CurrentMember;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    @GetMapping
    public ResponseEntity<MemberResponse> getMemberInfo(@CurrentMember Member member) {
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }
}
