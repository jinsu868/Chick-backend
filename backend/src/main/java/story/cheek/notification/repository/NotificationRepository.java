package story.cheek.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
