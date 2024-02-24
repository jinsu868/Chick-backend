package story.cheek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import story.cheek.search.member.repository.MemberSearchRepository;
import story.cheek.search.question.repository.QuestionSearchRepository;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {QuestionSearchRepository.class, MemberSearchRepository.class}))
@SpringBootApplication
public class CheekApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheekApplication.class, args);
    }
}
