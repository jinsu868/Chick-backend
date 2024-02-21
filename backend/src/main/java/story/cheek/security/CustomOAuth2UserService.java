package story.cheek.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.member.domain.AuthProvider;
import story.cheek.member.domain.Member;
import story.cheek.member.domain.Role;
import story.cheek.member.domain.Status;
import story.cheek.member.repository.MemberRepository;
import story.cheek.security.oauth2.OAuth2AuthenticationProcessingException;
import story.cheek.security.oauth2.OAuth2UserInfo;
import story.cheek.security.oauth2.OAuth2UserInfoFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOauth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationException("OAuth2 provider에 이메일이 없습니다.");
        }

        Optional<Member> memberOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        Member member;

        if (memberOptional.isPresent()) {
            member = memberOptional.orElseThrow(
                    () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND)
            );
            if (!member.getProvider().toString().equalsIgnoreCase(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()).toString())) {
                throw new OAuth2AuthenticationProcessingException("이미 등록된 회원입니다.");
            }

        } else {
            member = registerNewMember(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(member, oAuth2UserInfo.getAttributes());
    }

    private Member registerNewMember(OAuth2UserRequest oAuth2UserRequest , OAuth2UserInfo oAuth2UserInfo) {
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        Member member = Member.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .image(oAuth2UserInfo.getImageUrl())
                .provider(AuthProvider.valueOf(provider))
                .providerId(oAuth2UserInfo.getId())
                .isMentor(false)
                .status(Status.ACTIVE)
                .role(Role.ROLE_USER)
                .isMentor(false)
                .build();

        return memberRepository.save(member);
    }
}
