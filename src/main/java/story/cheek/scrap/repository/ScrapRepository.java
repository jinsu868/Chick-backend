package story.cheek.scrap.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.story.domain.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
