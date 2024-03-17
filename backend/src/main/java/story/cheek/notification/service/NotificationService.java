package story.cheek.notification.service;

import static story.cheek.common.exception.ErrorCode.FORBIDDEN_NOTIFICATION_READ;
import static story.cheek.common.exception.ErrorCode.NOT_FOUND_NOTIFICATION;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.common.exception.BusinessException;
import story.cheek.member.domain.Member;
import story.cheek.notification.domain.Notification;
import story.cheek.notification.dto.response.NotificationDetailResponse;
import story.cheek.notification.repository.NotificationRepository;
import story.cheek.story.dto.request.NotificationDeleteRequest;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationDetailResponse> findAll(Member loginMember) {

        return notificationRepository.findAllByReceiverId(loginMember.getId())
                .stream()
                .map(NotificationDetailResponse::from)
                .toList();
    }

    @Transactional
    public void read(Member member, Long notificationId) {
        Notification notification = findNotification(notificationId);
        validateNotificationRead(member, notification);
        notification.read();
    }

    public void deleteNotifications(Member member, NotificationDeleteRequest request) {
        List<Long> deleteRequestIds = request.deleteRequestIds();
        List<Long> ownIds = notificationRepository.findAllByIdInIds(deleteRequestIds)
                .stream()
                .filter(notification -> member.hasAuthority(notification.getReceiverId()))
                .map(Notification::getId)
                .toList();

        notificationRepository.deleteAllByIds(ownIds);
    }

    private Notification findNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_NOTIFICATION));
        return notification;
    }

    private void validateNotificationRead(Member member, Notification notification) {
        if (!member.hasAuthority(notification.getReceiverId())) {
            throw new BusinessException(FORBIDDEN_NOTIFICATION_READ);
        }
    }
}
