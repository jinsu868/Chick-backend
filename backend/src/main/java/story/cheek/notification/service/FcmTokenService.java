package story.cheek.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import story.cheek.member.domain.Member;
import story.cheek.notification.domain.FcmToken;
import story.cheek.notification.dto.request.FcmTokenCreateRequest;
import story.cheek.notification.repository.FcmTokenRepository;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    public void save(Member member, FcmTokenCreateRequest request) {
        String token = request.token();
        Long memberId = member.getId();

        fcmTokenRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        fcmToken -> fcmToken.update(token),
                        () -> fcmTokenRepository.save(FcmToken.createFcmToken(token, memberId))
                );
    }
}
