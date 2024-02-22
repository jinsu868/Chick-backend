package story.cheek.question.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import story.cheek.common.domain.BaseEntity;
import story.cheek.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Occupation occupation;

    @Column(nullable = false)
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private Question(
            Occupation occupation,
            String title,
            String content,
            Member writer
    ) {
        this.occupation = occupation;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static Question createQuestion(
            Occupation occupation,
            String title,
            String content,
            Member writer
    ) {
        return new Question(
                occupation,
                title,
                content,
                writer);
    }

    public void update(
            Occupation occupation,
            String title,
            String content
    ) {
       this.occupation = occupation;
       this.title = title;
       this.content = content;
    }
}
