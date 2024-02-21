package story.cheek.scrap.dto.response;

import java.time.LocalDateTime;
import story.cheek.story.domain.Scrap;

public record ScrapResponse(
        Long scrapId,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static ScrapResponse from(Scrap scrap) {
        return new ScrapResponse(
                scrap.getId(),
                scrap.getStory().getImageUrl(),
                scrap.getCreatedAt()
        );
    }
}
