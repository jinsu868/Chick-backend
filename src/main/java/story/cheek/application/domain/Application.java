package story.cheek.application.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import story.cheek.common.domain.BaseEntity;
import story.cheek.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor
public class Application extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String firstImageUrl;

    private String secondImageUrl;

    private String companyEmail;

    private boolean isDelete;

    @Builder
    public Application(
            Member member,
            String firstImageUrl,
            String secondImageUrl,
            String companyEmail,
            boolean isDelete) {

        this.member = member;
        this.firstImageUrl = firstImageUrl;
        this.secondImageUrl = secondImageUrl;
        this.companyEmail = companyEmail;
        this.isDelete = isDelete;
    }
}
