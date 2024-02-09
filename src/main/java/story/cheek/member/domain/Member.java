package story.cheek.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import story.cheek.story.domain.Scrap;
import story.cheek.story.domain.Story;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;
    private String image;

    @Column(nullable = false)
    private boolean isMentor;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public String roleName() {
        return role.name();
    }

    public boolean canMakeStory() {
        return isMentor;
    }

    public boolean isScrapPermission(Scrap scrap) {
        return id == scrap.getMember().id;
    }
}
