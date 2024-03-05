package story.cheek.notification.dto.response;

import story.cheek.notification.domain.Notification;
import story.cheek.notification.domain.NotificationType;

public record NotificationDetailResponse(
        Long notificationId,
        NotificationType type,
        String content,
        Long receiverId,
        Long targetId,
        boolean isRead
        //TODO : 화면 나오면 수정
) {

    public static NotificationDetailResponse from(Notification notification) {
        return new NotificationDetailResponse(
                notification.getId(),
                notification.getNotificationType(),
                notification.getContent(),
                notification.getReceiverId(),
                notification.getTargetId(),
                notification.isRead()
        );
    }
}
