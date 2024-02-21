package story.cheek.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.mail.domain.CompanyDomain;

public interface CompanyDomainRepository extends JpaRepository<CompanyDomain, Long> {
    boolean existsByDomain(String domain);
}
