package story.cheek.search.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import story.cheek.question.domain.Occupation;
import story.cheek.search.EsIndex;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(indexName = EsIndex.QUESTION_INDEX)
public class SearchQuestion {
    @Id
    @Field(name = "question_id", type = FieldType.Long)
    private Long questionId;

    @Field(name = "occupation", type = FieldType.Text)
    private Occupation occupation;

    @Field(name = "title", type = FieldType.Text)
    private String title;

    @Field(name = "content", type = FieldType.Text)
    private String content;
}
