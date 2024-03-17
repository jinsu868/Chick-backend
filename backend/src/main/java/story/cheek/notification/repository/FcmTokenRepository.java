package story.cheek.notification.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.notification.domain.FcmToken;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    Optional<FcmToken> findByMemberId(Long memberId);
}
