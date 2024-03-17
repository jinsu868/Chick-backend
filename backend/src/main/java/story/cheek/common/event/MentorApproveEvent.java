package story.cheek.common.event;

public record MentorApproveEvent(
        Long receiverId,
        String receiverName
) {
}
