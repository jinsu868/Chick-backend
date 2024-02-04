package story.cheek.security.refreshToken.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.common.exception.NotFoundRefreshTokenException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.security.TokenProvider;
import story.cheek.security.UserPrincipal;
import story.cheek.security.refreshToken.domain.RefreshToken;
import story.cheek.security.refreshToken.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public String getAccessToken(String token) {
        RefreshToken refreshToken = findRefreshTokenByToken(token);
        tokenProvider.validateToken(refreshToken.getToken());
        Member refreshTokenOwner = findRefreshTokenOwner(refreshToken.getMemberId());
        return tokenProvider.createAccessTokenByRefreshToken(refreshTokenOwner.getName());
    }

    @Transactional
    public void saveRefreshToken(String token, Long memberId) {
        deleteRefreshToken(memberId);
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(memberId)
                        .token(token)
                        .build()
        );
    }

    public void deleteRefreshToken(Long memberId) {
        refreshTokenRepository.deleteAllByMemberId(memberId);
    }

    private Member findRefreshTokenOwner(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private RefreshToken findRefreshTokenByToken(String token) {
        return refreshTokenRepository.findRefreshTokenByToken(token)
                .orElseThrow(() -> new NotFoundRefreshTokenException(ErrorCode.ENTITY_NOT_FOUND));
    }
}
