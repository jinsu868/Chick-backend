package story.cheek.question.dto.response;

import java.time.LocalDateTime;
import story.cheek.question.Occupation;
import story.cheek.question.Question;

public record QuestionDetailResponse(
        Long id,
        Occupation occupation,
        String title,
        String content,
        LocalDateTime updatedAt
) {

    public static QuestionDetailResponse of(Question question) {
        return new QuestionDetailResponse(
                question.getId(),
                question.getOccupation(),
                question.getTitle(),
                question.getContent(),
                question.getUpdateAt()
        );
    }
}
