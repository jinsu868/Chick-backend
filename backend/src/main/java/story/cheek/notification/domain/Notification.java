package story.cheek.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import story.cheek.common.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    private String content;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private Long receiverId;

    private boolean isRead;

    private Notification(
            NotificationType notificationType,
            String content,
            Long targetId,
            Long receiverId
    ) {
        this.notificationType = notificationType;
        this.content = content;
        this.targetId = targetId;
        this.receiverId = receiverId;
        isRead = false;
    }

    public static Notification createNotification(
        NotificationType notificationType,
        String content,
        Long targetId,
        Long receiverId
    ) {
        return new Notification(notificationType, content, targetId, receiverId);
    }

    public void read() {
        isRead = true;
    }

}
