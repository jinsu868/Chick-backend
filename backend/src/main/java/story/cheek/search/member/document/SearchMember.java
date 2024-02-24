package story.cheek.search.member.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import story.cheek.question.domain.Occupation;
import story.cheek.search.EsIndex;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = EsIndex.MEMBER_INDEX)
public class SearchMember {
    @Id
    @Field(name = "member_id", type = FieldType.Long)
    private Long memberId;

    @Field(name = "occupation", type = FieldType.Text)
    private Occupation occupation;

    @Field(name = "name", type = FieldType.Text)
    private String name;

    @Field(name = "image", type = FieldType.Text)
    private String image;

    @Field(name = "description", type = FieldType.Text)
    private String description;
}
