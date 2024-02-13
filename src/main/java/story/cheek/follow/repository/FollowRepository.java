package story.cheek.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.follow.domain.Follow;

import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
    Optional<Follow> findByFollowingMemberIdAndAndFollowerId(Long followingMemberId, Long followerId);
}
