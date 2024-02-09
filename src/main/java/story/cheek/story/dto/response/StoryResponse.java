package story.cheek.story.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import story.cheek.story.domain.Story;

public record StoryResponse(
        Long storyId,
        String imageUrl,
        LocalDateTime createdAt,
        int likeCount
) {

    public static StoryResponse from(Story story) {
        return new StoryResponse(
                story.getId(),
                story.getImageUrl(),
                story.getCreatedAt(),
                story.getLikeCount()
        );
    }

    @QueryProjection
    public StoryResponse(
            Long storyId,
            String imageUrl,
            LocalDateTime createdAt,
            int likeCount
    ) {
        this.storyId = storyId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }
}
