package story.cheek.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.follow.domain.Follow;


public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
}
