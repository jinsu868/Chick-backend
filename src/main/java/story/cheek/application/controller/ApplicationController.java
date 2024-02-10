package story.cheek.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import story.cheek.application.dto.ApplicationRequest;
import story.cheek.application.dto.ApplicationRequestOnlyJson;
import story.cheek.application.service.ApplicationService;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Void> applyMentor(@CurrentMember Member member,
                                            @RequestPart ApplicationRequestOnlyJson applicationRequestOnlyJson,
                                            @RequestPart List<MultipartFile> multipartFiles) {
        ApplicationRequest applicationRequest = ApplicationRequest.of(applicationRequestOnlyJson.email(), multipartFiles);
        Long applicationId = applicationService.apply(member, applicationRequest);
        return ResponseEntity.created(URI.create("/api/v1/applications/" + applicationId)).build();
    }

    @PostMapping("/approve") // 어드민
    public ResponseEntity<Void> approve(@CurrentMember Member member) {
        // 멘토 신청 승인
        return ResponseEntity.ok().build();
    }
}
