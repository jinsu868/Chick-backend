package story.cheek.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.application.domain.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
