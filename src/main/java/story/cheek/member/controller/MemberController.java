package story.cheek.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;

@RestController
public class MemberController {
    @GetMapping("/api/v1")
    public String hello(@CurrentMember Member member) {
        return member.getName();
    }
}
