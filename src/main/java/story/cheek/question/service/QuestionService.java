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
                request.content(),
                member
        );

        questionRepository.save(question);

        return question.getId();
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
