package story.cheek.security.refreshToken.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import story.cheek.member.domain.Member;
import story.cheek.security.CurrentMember;
import story.cheek.security.TokenProvider;
import story.cheek.security.UserPrincipal;
import story.cheek.security.refreshToken.service.RefreshTokenService;
import story.cheek.security.util.JwtExtractor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtExtractor jwtExtractor;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/refresh")
    public ResponseEntity<Void> getAccessTokenByRefreshToken(HttpServletRequest request) {
        String refreshToken = jwtExtractor.extractRefreshTokenFromRequest(request);
        String accessToken = refreshTokenService.getAccessToken(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " +  accessToken)
                .build();
    }
}
