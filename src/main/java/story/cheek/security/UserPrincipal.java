package story.cheek.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import story.cheek.member.domain.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {
    private final Member member;
    private Map<String, Object> attributes;

    public UserPrincipal(Member member) {
        this.member = member;
    }

    public static UserPrincipal create(Member member) {
        return new UserPrincipal(member);
    }

    public static UserPrincipal create(Member member, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = new UserPrincipal(member);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public String getUsername() {
        return member.getEmail();
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(member.roleName()));
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return member.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
