package story.cheek.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import story.cheek.common.dto.SliceResponse;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.search.document.SearchQuestion;
import story.cheek.search.dto.QuestionSearchResponse;
import story.cheek.search.repository.QuestionSearchRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionSearchService {
    private final QuestionSearchRepository questionSearchRepository;
    private final MemberRepository memberRepository;

    public SliceResponse<QuestionSearchResponse> searchQuestion(String title, String content, String occupation) {
        List<SearchQuestion> resultList = questionSearchRepository.findSearchQuestionsByTitleContainsOrContentContainsAndOccupationIgnoreCaseOrderByQuestionIdDesc(title, content, occupation);
        List<QuestionSearchResponse> responses = resultList
                .stream()
                .map((SearchQuestion searchQuestion) ->
                        QuestionSearchResponse.from(searchQuestion, getMemberName(searchQuestion.getMemberId()))).toList();

        return SliceResponse.of(responses, true, "1");
    }

    private String getMemberName(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));

        return member.getName();
    }
}
