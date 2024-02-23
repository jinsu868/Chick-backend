package story.cheek.search.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import story.cheek.search.EsIndex;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = EsIndex.QUESTION_INDEX)
public class SearchQuestion {
    @Id
    @Field(name = "question_id", type = FieldType.Long)
    private Long questionId;

    @Field(name = "writer_id", type = FieldType.Long)
    private Long writerId;

    @Field(name = "occupation", type = FieldType.Text)
    private String occupation;

    @Field(name = "title", type = FieldType.Text)
    private String title;

    @Field(name = "content", type = FieldType.Text)
    private String content;

    @Field(name = "created_at", type = FieldType.Date, format = DateFormat.date_time_no_millis)
    private String createdAt;
}
