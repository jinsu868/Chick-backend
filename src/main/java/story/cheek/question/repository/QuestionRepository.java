package story.cheek.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import story.cheek.question.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
