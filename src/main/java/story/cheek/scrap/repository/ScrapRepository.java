package story.cheek.scrap.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.member.domain.Member;
import story.cheek.story.domain.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findAllByMember(Member member);

    boolean existsByMember(Member member);
}
