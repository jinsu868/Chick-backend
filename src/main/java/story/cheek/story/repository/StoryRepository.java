package story.cheek.story.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.story.domain.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

    Slice<Story> findAllByOrderByIdDesc(Pageable pageable);

    Slice<Story> findAllByOrderByLikeCountDesc(Pageable pageable);
}
