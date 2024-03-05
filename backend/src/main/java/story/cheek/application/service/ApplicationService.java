package story.cheek.application.service;

import static story.cheek.common.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.application.domain.Application;
import story.cheek.application.dto.request.ApplicationRequest;
import story.cheek.application.dto.response.ApplicationDetailResponse;
import story.cheek.application.dto.response.ApplicationResponse;
import story.cheek.application.repository.ApplicationRepository;
import story.cheek.common.dto.SliceResponse;
import story.cheek.common.event.MentorApproveEvent;
import story.cheek.common.exception.DuplicateApplyException;
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
    private final ApplicationEventPublisher eventPublisher;

    public Long apply(Member member, ApplicationRequest request) {
        validateDuplicateApply(member);
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
        application.delete();

        eventPublisher.publishEvent(new MentorApproveEvent(member.getId(), member.getName()));
    }

    public SliceResponse<ApplicationResponse> findAll(Member adminMember, String cursor) {
        validateAdmin(adminMember);

        return applicationRepository.findAllExceptDeleted(cursor);
    }

    public ApplicationDetailResponse findDetailById(Member adminMember, Long applicationId) {
        validateAdmin(adminMember);

        Application application = findApplication(applicationId);

        return ApplicationDetailResponse.from(application);
    }

    private Member findMember(Application application) {
        return memberRepository.findByApplication(application)
                .orElseThrow(() -> new NotFoundMemberException(MEMBER_NOT_FOUND));

    }

    private Application findApplication(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundApplication(APPLICATION_NOT_FOUND));
    }

    private void validateDuplicateApprove(Member member) {
        if (member.isMentor()) {
            throw new DuplicateApprovalException(APPROVAL_DUPLICATION);
        }
    }

    private void validateDuplicateApply(Member member) {
        if (applicationRepository.existsByMember(member)) {
            throw new DuplicateApplyException(DUPLICATED_APPLICATION);
        }
    }

    private void validateAdmin(Member member) {
        if (!member.isAdmin()) {
            throw new NotAdminException(NOT_ADMIN);
        }
    }
}
