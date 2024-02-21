package story.cheek.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import story.cheek.search.repository.QuestionSearchRepository;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = QuestionSearchRepository.class)
public class ElasticConfig extends ElasticsearchConfiguration {

    private String esUrl;
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }
}
