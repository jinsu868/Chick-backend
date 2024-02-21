package story.cheek.story.dto.request;

import org.springframework.web.multipart.MultipartFile;
import story.cheek.question.domain.Occupation;

public record StoryCreateRequest(
        Long questionId,
        Occupation occupation,
        MultipartFile multipartFile
) {
    public static StoryCreateRequest of(
            StoryCreateRequestWithoutImage request,
            MultipartFile image
    ) {
        return new StoryCreateRequest(
                request.questionId(),
                request.occupation(),
                image
        );
    }
}
