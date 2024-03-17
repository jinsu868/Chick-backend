package story.cheek.like.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.question.domain.Occupation;
import story.cheek.question.domain.Question;
import story.cheek.question.repository.QuestionRepository;
import story.cheek.story.domain.Story;
import story.cheek.story.repository.StoryRepository;

@SpringBootTest
class LikeServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LikeService likeService;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    QuestionRepository questionRepository;


    List<Member> members = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 100; i++) {
            Member member = Member.builder()
                    .name("name" + i)
                    .description("description")
                    .email("email" + i)
                    .build();

            members.add(memberRepository.save(member));
        }

        Member member = members.get(0);

        Question question = questionRepository.save(
                Question.createQuestion(Occupation.DEVELOP, "question1", "content", member));

        storyRepository.save(Story.createStory(Occupation.DEVELOP, "qwe.png", question, member));
    }

    @Test
    void 동시에_좋아요_요청_테스트() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        System.out.println(likeService.getClass());

        for (int i = 0; i < threadCount; i++) {
            int memberIdx = i;
            executorService.submit(() -> {
                try {
                    likeService.likeStory(1L, members.get(memberIdx));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Story story = storyRepository.findById(1L).orElseThrow();
        Assertions.assertThat(story.getLikeCount()).isEqualTo(100);
    }

}