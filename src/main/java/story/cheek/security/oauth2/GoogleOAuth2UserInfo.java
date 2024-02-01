package story.cheek.security.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo{
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        System.out.println("오우 " + attributes);
        return (String)attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        System.out.println("이미지 " + (String)attributes.get("picture"));
        return (String)attributes.get("picture");
    }
}
