package story.cheek.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.security.util.JwtExtractor;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final JwtExtractor jwtExtractor;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtExtractor.extractAccessTokenFromRequest(request);

            if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
                String email = tokenProvider.getEmailFromToken(accessToken);
                Member member = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
                UserDetails userDetails = UserPrincipal.create(member);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }
}
