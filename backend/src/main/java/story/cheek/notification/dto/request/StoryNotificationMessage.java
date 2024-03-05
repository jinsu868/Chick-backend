package story.cheek.notification.dto.request;

import java.time.LocalDateTime;
import story.cheek.notification.domain.Notification;
import story.cheek.notification.domain.NotificationType;

public record StoryNotificationMessage(
        boolean validate_only,
        Message message
) {

    public static StoryNotificationMessage of(
            boolean validate_only,
            Message message
    ) {
        return new StoryNotificationMessage(validate_only, message);
    }

    public record Message(
        Data data,
        String token
    ) {

        public static Message of(Data data, String token) {
            return new Message(data, token);
        }
    }

    public record Data(
            Long targetId,
            Long receiverId,
            String content,
            NotificationType type,
            LocalDateTime createdAt
    ) {

        public static Data from(Notification notification) {
            return new Data(
                    notification.getTargetId(),
                    notification.getReceiverId(),
                    notification.getContent(),
                    notification.getNotificationType(),
                    notification.getCreatedAt()
            );
        }
    }
}
