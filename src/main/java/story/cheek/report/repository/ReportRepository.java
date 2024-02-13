package story.cheek.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.report.domain.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {


    List<Report> findAllByReportingMemberId(Long reportingMemberId);

    Optional<Report> findReportByReportingMemberIdAndReportedMemberId(Long reportingMemberId, Long reportedMemberId);
}
