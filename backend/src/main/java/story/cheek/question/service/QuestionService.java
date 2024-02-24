package story.cheek.question.service;

import static story.cheek.common.exception.ErrorCode.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import story.cheek.common.exception.NotFoundQuestionException;
import story.cheek.common.exception.QuestionForbiddenException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.question.domain.Question;
import story.cheek.question.dto.request.QuestionCreateRequest;
import story.cheek.question.dto.request.QuestionUpdateRequest;
import story.cheek.question.dto.response.QuestionDetailResponse;
import story.cheek.question.dto.response.QuestionResponse;
import story.cheek.question.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long save(Member member, QuestionCreateRequest request) {

        Question question = Question.createQuestion(
                request.occupation(),
                request.title(),
                concatenateTitleAndContent(request),
                member
        );

        questionRepository.save(question);

        return question.getId();
    }

    // es로 제목 + 내용 + 직종 세가지로 조회 시 쿼리 문제가 있어서
    // 제목 + 내용을 더해서 하나로 만들어 제목에 있는 단어까지 내용으로 바로 조회하기 위해서 만들었습니다.
    private String concatenateTitleAndContent(QuestionCreateRequest request) {
        return request.title() + " " + request.content();
    }

    public QuestionDetailResponse findDetailById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundQuestionException(QUESTION_NOT_FOUND));

        return QuestionDetailResponse.of(question);
    }

    @Transactional
    public void update(
            Member member,
            Long questionId,
            QuestionUpdateRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundQuestionException(QUESTION_NOT_FOUND));

        validateQuestionUpdate(member, question);

        question.update(request.occupation(), request.title(), request.content());
    }

    private void validateQuestionUpdate(Member member, Question question) {
        //TODO: refactor
        if (question.getWriter().getId() != member.getId()) {
            throw new QuestionForbiddenException(FORBIDDEN_QUESTION_UPDATE);
        }
    }

    public List<QuestionResponse> findAll(Member member) {
        //member 읽기 권한 체크 (2차 스프린트에서 구현)

        return questionRepository.findAllByOrderByIdDesc()
                .stream()
                .map(QuestionResponse::from)
                .toList();
    }
}
