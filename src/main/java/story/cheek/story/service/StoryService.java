package story.cheek.story.service;

import static story.cheek.common.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundQuestionException;
import story.cheek.common.exception.StoryForbiddenException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.question.domain.Question;
import story.cheek.question.repository.QuestionRepository;
import story.cheek.story.domain.Story;
import story.cheek.story.dto.request.StoryCreateRequest;
import story.cheek.story.repository.StoryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member, StoryCreateRequest request) {
        validateStoryCreate(member);

        // S3에 이미지 저장
        String tempUrl = "123.png";

        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new NotFoundQuestionException(QUESTION_NOT_FOUND));

        Story story = Story.createStory(
                request.occupation(),
                tempUrl,
                question,
                member);

        storyRepository.save(story);

        return story.getId();
    }

    private void validateStoryCreate(Member member) {
        if (!member.canMakeStory()) {
            throw new StoryForbiddenException(FORBIDDEN_STORY_CREATE);
        }
    }
}
