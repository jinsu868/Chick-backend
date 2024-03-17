package story.cheek.notification.service;

import static story.cheek.notification.domain.NotificationType.*;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import story.cheek.common.event.MentorApproveEvent;
import story.cheek.common.event.StoryCreateEvent;
import story.cheek.notification.domain.Notification;
import story.cheek.notification.repository.NotificationRepository;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private static final String STORY_NOTIFICATION_MESSAGE_FORM = "%s님이 스토리를 개시했습니다.";
    private static final String MENTOR_APPROVE_NOTIFICATION_MESSAGE_FORM = "%s 님 멘토 인증이 완료됐습니다!";

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    @Async
    public void sendStoryNotification(StoryCreateEvent storyCreateEvent) {
        Notification notification = notificationRepository.save(Notification.createNotification(
                STORY,
                createStoryNotificationMessage(storyCreateEvent.sender()),
                storyCreateEvent.targetId(),
                storyCreateEvent.receiverId()));

        fcmService.sendMessage(notification);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    @Async
    public void sendMentorApproveNotification(MentorApproveEvent mentorApproveEvent) {
        Notification notification = notificationRepository.save(Notification.createNotification(
                APPROVE,
                createMentorApproveMessage(mentorApproveEvent.receiverName()),
                mentorApproveEvent.receiverId(),
                mentorApproveEvent.receiverId()
        ));

        fcmService.sendMessage(notification);
    }

    //TODO : 메시지 생성 클래스 분리
    private String createStoryNotificationMessage(String sender) {
        return String.format(STORY_NOTIFICATION_MESSAGE_FORM, sender);
    }

    private String createMentorApproveMessage(String receiverName) {
        return String.format(MENTOR_APPROVE_NOTIFICATION_MESSAGE_FORM, receiverName);
    }
}
