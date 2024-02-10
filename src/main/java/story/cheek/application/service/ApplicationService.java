package story.cheek.application.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.application.domain.Application;
import story.cheek.application.dto.ApplicationRequest;
import story.cheek.application.repository.ApplicationRepository;
import story.cheek.member.domain.Member;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    public Long apply(Member member, ApplicationRequest applicationRequest) {
        Application application = applicationRequest.toEntity(member);
        // 메일 전송

        return applicationRepository.save(application).getId();
    }
}
