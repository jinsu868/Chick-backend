package story.cheek.notification.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import story.cheek.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverId(Long receiverId);

    @Query("select n from Notification n where n.id in :notificationIds")
    List<Notification> findAllByIdInIds(@Param("notificationIds") List<Long> notificationIds);

    @Modifying
    @Query("delete from Notification n where n.id in :ownIds")
    void deleteAllByIds(@Param("ownIds") List<Long> ownIds);
}
