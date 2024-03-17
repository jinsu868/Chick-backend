package story.cheek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import story.cheek.search.member.repository.SearchMemberRepository;
import story.cheek.search.question.repository.SearchQuestionRepository;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {SearchQuestionRepository.class, SearchMemberRepository.class}))
@EnableAsync
@SpringBootApplication
public class CheekApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheekApplication.class, args);
    }
}
