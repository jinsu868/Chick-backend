package story.cheek.mail.service;

import static story.cheek.common.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.common.exception.DuplicateApprovalException;
import story.cheek.common.exception.MailSendException;
import story.cheek.common.exception.NotFoundCompanyDomainException;
import story.cheek.common.exception.NotFoundMailVerificationException;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.common.exception.NotMatchCodeException;
import story.cheek.common.redis.RedisMailUtils;
import story.cheek.mail.dto.MailRequest;
import story.cheek.mail.dto.MailVerificationRequest;
import story.cheek.mail.repository.CompanyDomainRepository;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String MAIL_TITLE = "Cheek Mentor Authentication";

    private final CompanyDomainRepository companyDomainRepository;
    private final MemberRepository memberRepository;
    private final CodeGenerator codeGenerator;
    private final JavaMailSender mailSender;
    private final RedisMailUtils redisMailUtils;
    private final DomainExtractor domainExtractor;

    public void sendEmail(Member member, MailRequest request) {
        validateDuplicateApprove(member);
        String companyMail = request.email();
        String companyDomain = domainExtractor.extract(companyMail);
        validateCompanyDomain(companyDomain);
        String authCode = codeGenerator.generate();
        SimpleMailMessage mailForm = createMailForm(companyMail, authCode);

        try {
            redisMailUtils.setData(member.getName(), authCode);
            mailSender.send(mailForm);
        } catch (MailException e) {
            throw new MailSendException(FAILED_SEND_MAIL);
        }
    }

    @Transactional
    public void verify(Member currentMember, MailVerificationRequest request) {
        validateDuplicateApprove(currentMember);
        String authCode = redisMailUtils.getData(currentMember.getName());
        validateMailRequest(authCode);
        validateAuthCode(request, authCode);
        Member member = findMember(currentMember);
        member.approveMentor();
    }

    private Member findMember(Member member) {
        return memberRepository.findById(member.getId())
                .orElseThrow(() -> new NotFoundMemberException(MEMBER_NOT_FOUND));
    }

    private void validateMailRequest(String mailToken) {
        if (mailToken == null) {
            throw new NotFoundMailVerificationException(MAIL_REQUEST_NOT_FOUND);
        }
    }

    private void validateAuthCode(MailVerificationRequest request, String authCode) {
        if (!request.authCode().equals(authCode)) {
            throw new NotMatchCodeException(MAIL_CODE_NOT_MATCH);
        }
    }

    private void validateCompanyDomain(String companyDomain) {
        if (!companyDomainRepository.existsByDomain(companyDomain)) {
            throw new NotFoundCompanyDomainException(COMPANY_DOMAIN_NOT_FOUND);
        }
    }

    private void validateDuplicateApprove(Member member) {
        if (member.isMentor()) {
            throw new DuplicateApprovalException(APPROVAL_DUPLICATION);
        }
    }

    private SimpleMailMessage createMailForm(String toMail, String authCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject(MAIL_TITLE);
        message.setText(authCode);

        return message;
    }
}
