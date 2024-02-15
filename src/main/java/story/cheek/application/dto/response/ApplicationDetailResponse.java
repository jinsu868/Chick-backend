package story.cheek.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import story.cheek.application.domain.Application;

public record ApplicationDetailResponse(
        Long applicationId,
        LocalDateTime createdAt,
        List<String> imageUrls,
        String email
) {

    public static ApplicationDetailResponse from(Application application) {
        return new ApplicationDetailResponse(
                application.getId(),
                application.getCreatedAt(),
                List.of(application.getFirstImageUrl(), application.getSecondImageUrl()),
                application.getCompanyEmail()
        );
    }
}
