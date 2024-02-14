package story.cheek.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.mail.dto.MailRequest;
import story.cheek.mail.dto.MailVerificationRequest;
import story.cheek.mail.service.MailService;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {
    private final MailService mailService;

    @PostMapping("verification-requests")
    public ResponseEntity<Void> sendMail(
            @CurrentMember Member member,
            @RequestBody MailRequest mailRequest
    ) {
        mailService.sendEmail(member, mailRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifications")
    public ResponseEntity<Void> verifyMail(
            @CurrentMember Member member,
            @RequestBody MailVerificationRequest mailVerificationRequest
    ) {
        mailService.verify(member, mailVerificationRequest);

        return ResponseEntity.ok().build();
    }
}
