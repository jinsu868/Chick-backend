package story.cheek.search.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import story.cheek.common.dto.SliceResponse;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.NotFoundFollowException;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.member.domain.Member;
import story.cheek.member.repository.MemberRepository;
import story.cheek.search.document.SearchQuestion;
import story.cheek.search.dto.QuestionSearchResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class QuestionSearchRepositoryImpl implements QuestionSearchRepositoryCustom {
    private static final int PAGE_SIZE = 10;

    private final ElasticsearchOperations elasticsearchOperations;
    private final MemberRepository memberRepository;

    @Override
    public SliceResponse<QuestionSearchResponse> getQuestions(String search, String occupation, String cursor) {
        NativeSearchQuery query = createQueryWithIdDescendingOrder(search, occupation, cursor);

        SearchHits<SearchQuestion> results = elasticsearchOperations.search(query, SearchQuestion.class);

        List<QuestionSearchResponse> responses = results
                .stream()
                .map((SearchHit<SearchQuestion> searchHit) -> QuestionSearchResponse
                        .from(searchHit.getContent(), getMemberName(searchHit.getContent().getWriterId())))
                .collect(Collectors.toList());

        return convertToSlice(responses);
    }

    public NativeSearchQuery createQueryWithIdDescendingOrder(String search, String occupation, String cursor) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("content", search))
                .must(QueryBuilders.matchQuery("occupation", occupation))
                .filter(QueryBuilders.rangeQuery("created_at").lte(cursor));

        queryBuilder.withQuery(query);
        queryBuilder.withSort(SortBuilders.fieldSort("created_at").order(SortOrder.DESC));
        return queryBuilder.build();
    }

    private SliceResponse<QuestionSearchResponse> convertToSlice(List<QuestionSearchResponse> searchQuestions) {
        validateSearchQuestionExist(searchQuestions);
        boolean hasNext = existNextPage(searchQuestions);
        String nextCursor = generateCursor(searchQuestions);
        return SliceResponse.of(searchQuestions, hasNext, nextCursor);
    }

    private void validateSearchQuestionExist(List<QuestionSearchResponse> questionSearchResponses) {
        if (questionSearchResponses.isEmpty()) {
            throw new NotFoundFollowException(ErrorCode.FOLLOWING_NOT_FOUND);
        }
    }

    private boolean existNextPage(List<QuestionSearchResponse> searchQuestions) {
        if (searchQuestions.size() > PAGE_SIZE) {
            searchQuestions.remove(PAGE_SIZE);
            return true;
        }

        return false;
    }

    private String generateCursor(List<QuestionSearchResponse> searchQuestions) {
        QuestionSearchResponse searchResponse = searchQuestions.get(searchQuestions.size() - 1);

        return String.valueOf(searchResponse.createdAt());
    }

    private String getMemberName(Long writerId) {
        Member member = memberRepository.findById(writerId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));

        return member.getName();
    }
}
