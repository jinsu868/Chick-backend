package story.cheek.report.dto;

import story.cheek.member.domain.Member;
import story.cheek.report.domain.Report;

public record ReportRequest(
        Long reportedMemberId,
        String content
) {
    public Report toEntity(Member reportingMember, Member reportedMember) {
        return Report.builder()
                .content(content)
                .reportedMember(reportedMember)
                .reportingMember(reportingMember)
                .build();
    }
}
