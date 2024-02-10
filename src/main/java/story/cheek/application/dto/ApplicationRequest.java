package story.cheek.application.dto;

import org.springframework.web.multipart.MultipartFile;
import story.cheek.application.domain.Application;
import story.cheek.member.domain.Member;

import java.util.List;

public record ApplicationRequest(
        String email,
        List<MultipartFile> files
) {
    public static ApplicationRequest of(String email, List<MultipartFile> files) {
        return new ApplicationRequest(email, files);
    }

    public Application toEntity(Member member) {
        return Application.builder()
                .member(member)
                .companyEmail(email)
                .firstImageUrl(files.getFirst().getOriginalFilename())
                .secondImageUrl(files.getLast().getOriginalFilename())
                .build();
    }
}
