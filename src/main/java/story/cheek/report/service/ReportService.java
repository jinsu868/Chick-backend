package story.cheek.report.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.common.exception.DuplicateReportException;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.report.domain.Report;
import story.cheek.report.dto.ReportRequest;
import story.cheek.report.repository.ReportRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long report(Long reportingMemberId, ReportRequest reportRequest) {
        Member reportedMember = findmember(reportRequest.reportedMemberId());
        Member reportingMember = findmember(reportingMemberId);

        if (reportRepository.findReportByReportingMemberIdAndReportedMemberId(reportingMember.getId(), reportedMember.getId()).isPresent()) {
            throw new DuplicateReportException(ErrorCode.DUPLICATED_REPORT);
        }

        Report report = reportRepository.save(reportRequest.toEntity(reportingMember, reportedMember));
        changeStatusIfReportedCountOverTen(reportedMember);

        reportedMember.addReportedList(report);
        reportingMember.addReportingList(report);

        return report.getId();
    }

    private void changeStatusIfReportedCountOverTen(Member reportedMember) {
        if (reportedMember.getReportedListCount() > 10) {
            reportedMember.changeStatusToSuspend();
        }
    }

    private Member findmember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
