package story.cheek.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Long memberId;

    private FcmToken(String token, Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }

    public static FcmToken createFcmToken(String token, Long memberId) {
        return new FcmToken(token, memberId);
    }

    public void update(String token) {
        this.token = token;
    }
}
