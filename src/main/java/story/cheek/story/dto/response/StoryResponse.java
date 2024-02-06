package story.cheek.story.dto.response;

import java.time.LocalDateTime;
import story.cheek.story.domain.Story;

public record StoryResponse(
        Long storyId,
        String imageUrl,
        LocalDateTime localDateTime
) {

    public static StoryResponse from(Story story) {
        return new StoryResponse(
                story.getId(),
                story.getImageUrl(),
                story.getCreatedAt()
        );
    }
}
