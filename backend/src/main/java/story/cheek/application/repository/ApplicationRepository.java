package story.cheek.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.application.domain.Application;
import story.cheek.member.domain.Member;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationRepositoryCustom {

    boolean existsByMember(Member member);
}
