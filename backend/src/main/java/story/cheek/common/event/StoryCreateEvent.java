package story.cheek.common.event;

public record StoryCreateEvent(
        String sender,
        Long targetId,
        Long receiverId
) {
}
