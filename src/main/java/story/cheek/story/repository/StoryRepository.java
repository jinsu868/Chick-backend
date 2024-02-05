package story.cheek.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.story.domain.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
