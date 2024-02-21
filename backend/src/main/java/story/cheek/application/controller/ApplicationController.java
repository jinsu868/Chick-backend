package story.cheek.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import story.cheek.application.dto.request.ApplicationRequest;
import story.cheek.application.dto.request.ApplicationRequestOnlyJson;
import story.cheek.application.dto.response.ApplicationDetailResponse;
import story.cheek.application.dto.response.ApplicationResponse;
import story.cheek.application.service.ApplicationService;
import story.cheek.common.dto.SliceResponse;
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

    @GetMapping
    public ResponseEntity<SliceResponse<ApplicationResponse>> findAll(
            @CurrentMember Member member,
            @RequestParam(required = false) String cursor) {
        SliceResponse<ApplicationResponse> response = applicationService.findAll(member, cursor);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationDetailResponse> findById(
            @CurrentMember Member member,
            @PathVariable Long applicationId
    ) {
        ApplicationDetailResponse response = applicationService.findDetailById(member, applicationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve/{applicationId}") // 어드민
    public ResponseEntity<Void> approve(@CurrentMember Member member, @PathVariable Long applicationId) {
        applicationService.approve(member, applicationId);

        return ResponseEntity.ok().build();
    }
}
