package story.cheek.scrap.service;

import static story.cheek.common.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundStoryException;
import story.cheek.member.domain.Member;
import story.cheek.scrap.repository.ScrapRepository;
import story.cheek.story.domain.Scrap;
import story.cheek.story.domain.Story;
import story.cheek.story.repository.StoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final StoryRepository storyRepository;

    @Transactional
    public Long save(Member member, Long storyId) {
        // TODO: 차단 여부 체크 (2차 스프린트)

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new NotFoundStoryException(STORY_NOT_FOUND));

        Scrap scrap = Scrap.of(member, story);
        scrapRepository.save(scrap);

        return scrap.getId();
    }
}
