package story.cheek.application.service;

import static story.cheek.common.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.application.domain.Application;
import story.cheek.application.dto.ApplicationRequest;
import story.cheek.application.repository.ApplicationRepository;
import story.cheek.common.exception.DuplicateApprovalException;
import story.cheek.common.exception.NotAdminException;
import story.cheek.common.exception.NotFoundApplication;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.mail.domain.CompanyDomain;
import story.cheek.mail.repository.CompanyDomainRepository;
import story.cheek.mail.service.DomainExtractor;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final CompanyDomainRepository companyDomainRepository;
    private final DomainExtractor domainExtractor;

    public Long apply(Member member, ApplicationRequest request) {
        validateDuplicateApprove(member);
        Application application = request.toEntity(member);

        return applicationRepository.save(application).getId();
    }

    @Transactional
    public void approve(Member adminMember, Long applicationId) {
        validateAdmin(adminMember);
        Application application = findApplication(applicationId);

        Member member = findMember(application);
        validateDuplicateApprove(member);
        member.approveMentor();

        String domain = domainExtractor.extract(application.getCompanyEmail());
        companyDomainRepository.save(CompanyDomain.from(domain));
        //TODO: push alarm
    }

    private Member findMember(Application application) {
        Member member = memberRepository.findByApplication(application)
                .orElseThrow(() -> new NotFoundMemberException(MEMBER_NOT_FOUND));
        return member;
    }

    private Application findApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundApplication(APPLICATION_NOT_FOUND));
        return application;
    }

    private void validateDuplicateApprove(Member member) {
        if (member.isMentor()) {
            throw new DuplicateApprovalException(APPROVAL_DUPLICATION);
        }
    }

    private void validateAdmin(Member member) {
        if (!member.isAdmin()) {
            throw new NotAdminException(NOT_ADMIN);
        }
    }
}
