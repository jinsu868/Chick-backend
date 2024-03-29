package story.cheek.story.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import story.cheek.DatabaseCleanup;
import story.cheek.common.constant.SortType;
import story.cheek.common.dto.SliceResponse;
import story.cheek.highlight.domain.Highlight;
import story.cheek.highlight.domain.StoryHighlight;
import story.cheek.highlight.repository.HighlightRepository;
import story.cheek.highlight.repository.StoryHighlightRepository;
import story.cheek.member.domain.Member;
import story.cheek.member.domain.Role;
import story.cheek.member.repository.MemberRepository;
import story.cheek.question.domain.Occupation;
import story.cheek.question.domain.Question;
import story.cheek.question.repository.QuestionRepository;
import story.cheek.story.domain.Story;
import story.cheek.story.dto.response.StoryResponse;
import story.cheek.common.annotation.RepositoryTest;

@RepositoryTest
class StoryRepositoryTest {

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    HighlightRepository highlightRepository;

    @Autowired
    StoryHighlightRepository storyHighlightRepository;

    Member member;

    Question question;

    @BeforeEach
    void setUp() {
        databaseCleanup.execute();
        member = memberRepository.save(Member.builder().role(Role.ROLE_USER).isMentor(true).build());
        question = questionRepository.save(Question.createQuestion(
                Occupation.DEVELOP,
                "chick",
                "백엔드란",
                member
        ));
    }

    @Test
    void 분야를_선택하지_않으면_모든_직종의_스토리가_조회된다() {
        List<StoryResponse> values = new ArrayList<>();
        Story story1 = storyRepository.save(Story.createStory(
                Occupation.DEVELOP,
                "image.png",
                question,
                member
        ));
        Story story2 = storyRepository.save(Story.createStory(
                Occupation.PLAN,
                "image.png",
                question,
                member
        ));
        Story story3 = storyRepository.save(Story.createStory(
                Occupation.MARKETING,
                "image.png",
                question,
                member
        ));

        values.add(StoryResponse.from(story3));
        values.add(StoryResponse.from(story2));
        values.add(StoryResponse.from(story1));

        SliceResponse<StoryResponse> response = storyRepository.findAllByOrderByIdDesc(3, null,
                SortType.LATEST, null);

        Assertions.assertThat(response.values()).usingRecursiveComparison().isEqualTo(values);
        Assertions.assertThat(response.hasNext()).isFalse();
        Assertions.assertThat(response.cursor()).isNull();
    }

    @Test
    void 분야를_선택하면_해당_직종의_스토리만_조회된다() {
        List<StoryResponse> values = new ArrayList<>();
        Story story1 = storyRepository.save(Story.createStory(
                Occupation.DEVELOP,
                "image.png",
                question,
                member
        ));
        Story story2 = storyRepository.save(Story.createStory(
                Occupation.DEVELOP,
                "image.png",
                question,
                member
        ));
        Story story3 = storyRepository.save(Story.createStory(
                Occupation.MARKETING,
                "image.png",
                question,
                member
        ));

        values.add(StoryResponse.from(story2));
        values.add(StoryResponse.from(story1));

        SliceResponse<StoryResponse> response = storyRepository.findAllByOrderByIdDesc(3, null,
                SortType.LATEST, Occupation.DEVELOP);

        Assertions.assertThat(response.values()).usingRecursiveComparison().isEqualTo(values);
        Assertions.assertThat(response.hasNext()).isFalse();
    }

    @Test
    void 최신순으로_스토리를_조회한다() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Story savedStory = storyRepository.save(Story.createStory(
                    Occupation.DEVELOP,
                    "image.png",
                    question,
                    member
            ));

            ids.add(savedStory.getId());
        }

        Collections.reverse(ids);

        SliceResponse<StoryResponse> response1 = storyRepository.findAllByOrderByIdDesc(3, null,
                SortType.LATEST, null);

        Assertions.assertThat(response1.values().get(0).storyId()).isEqualTo(ids.get(0));
        Assertions.assertThat(response1.hasNext()).isTrue();

        String cursor = response1.cursor();

        SliceResponse<StoryResponse> response2 = storyRepository.findAllByOrderByIdDesc(3, cursor,
                SortType.LATEST, null);

        Assertions.assertThat(response2.values().get(0).storyId()).isEqualTo(ids.get(3));
        Assertions.assertThat(response2.hasNext()).isFalse();
    }

    @Test
    void 좋아요순으로_스토리를_조회한다() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Story savedStory = storyRepository.save(Story.createStory(
                    Occupation.DEVELOP,
                    "image.png",
                    question,
                    member
            ));

            ids.add(savedStory.getId());
            for (int j = 0; j < 5-i; j++) {
                savedStory.like();
            }
        }

        SliceResponse<StoryResponse> response1 = storyRepository.findAllByOrderByLikeCountDesc(3, null,
                SortType.LIKE, null);

        Assertions.assertThat(response1.values().get(0).storyId()).isEqualTo(ids.get(0));
        Assertions.assertThat(response1.hasNext()).isTrue();

        String cursor = response1.cursor();
        SliceResponse<StoryResponse> response2 = storyRepository.findAllByOrderByLikeCountDesc(3,
                cursor, SortType.LIKE, null);

        Assertions.assertThat(response2.values().get(0).storyId()).isEqualTo(ids.get(3));
        Assertions.assertThat(response2.hasNext()).isFalse();
    }

    @Test
    void 하이라이트에_속한_스토리를_조회한다() {
        List<Long> ids = new ArrayList<>();
        Highlight highlight = highlightRepository.save(Highlight.createHighlight(
                member,
                "chick"
        ));

        for (int i = 0; i < 5; i++) {
            Story savedStory = storyRepository.save(Story.createStory(
                    Occupation.DEVELOP,
                    "image.png",
                    question,
                    member
            ));
            ids.add(savedStory.getId());
            storyHighlightRepository.save(StoryHighlight.of(highlight, savedStory));
        }

        Collections.reverse(ids);

        SliceResponse<StoryResponse> response1 = storyRepository.findAllByHighlightOrderByIdDesc(3,
                null, highlight, SortType.LATEST);

        Assertions.assertThat(response1.values().get(0).storyId()).isEqualTo(ids.get(0));
        Assertions.assertThat(response1.hasNext()).isTrue();

        String cursor = response1.cursor();

        SliceResponse<StoryResponse> response2 = storyRepository.findAllByHighlightOrderByIdDesc(3,
                cursor, highlight, SortType.LATEST);

        Assertions.assertThat(response2.values().get(0).storyId()).isEqualTo(ids.get(3));
        Assertions.assertThat(response2.hasNext()).isFalse();
    }
}