package story.cheek.security.oauth2;

import backend.backend.auth.oauth2.user.KakaoOAuth2UserInfo;
import story.cheek.member.domain.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("OAuth2UserInfoFactory의 인스턴스는 생성할 수 없습니다.");
    }

    public static OAuth2UserInfo getOauth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        if (registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())) {
            System.out.println("kakao " + attributes);
            return new KakaoOAuth2UserInfo(attributes);
        }

        if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            Map<String, Object> naverAttributes = (Map<String, Object>) attributes.get("response");
            System.out.println("naver " + naverAttributes);
            return new NaverOAuth2UserInfo(naverAttributes);
        }

        throw new OAuth2AuthenticationProcessingException(registrationId + " 로그인은 지원하지 않습니다.");
    }
}
