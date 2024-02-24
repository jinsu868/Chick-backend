package story.cheek.application.dto.response;

import java.time.LocalDateTime;

public record ApplicationResponse(Long applicationId, LocalDateTime createdAt) {

    public static ApplicationResponse of(Long applicationId, LocalDateTime createdAt) {
        return new ApplicationResponse(applicationId, createdAt);
    }
}
