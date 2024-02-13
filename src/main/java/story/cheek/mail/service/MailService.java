package story.cheek.mail.service;

import static story.cheek.common.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import story.cheek.common.exception.DuplicateApprovalException;
import story.cheek.common.exception.MailSendException;
import story.cheek.common.exception.NotFoundCompanyDomainException;
import story.cheek.common.redis.RedisMailUtils;
import story.cheek.mail.dto.MailRequest;
import story.cheek.mail.repository.CompanyDomainRepository;
import story.cheek.member.domain.Member;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String MAIL_TITLE = "Cheek Mentor Authentication";

    private final CompanyDomainRepository companyDomainRepository;
    private final CodeGenerator codeGenerator;
    private final JavaMailSender mailSender;
    private final RedisMailUtils redisMailUtils;
    private final DomainExtractor domainExtractor;

    public void sendEmail(Member member, MailRequest mailRequest) {
        validateDuplicateApprove(member);
        String companyMail = mailRequest.email();
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
