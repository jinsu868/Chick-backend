package story.cheek.search.member.repository;

import lombok.RequiredArgsConstructor;
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
import story.cheek.search.member.document.SearchMember;
import story.cheek.search.member.dto.SearchMemberResponse;
import story.cheek.search.question.document.SearchQuestion;
import story.cheek.search.question.dto.SearchQuestionResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SearchMemberRepositoryImpl implements SearchMemberRepositoryCustom {
    private static final int PAGE_SIZE = 10;

    private final ElasticsearchOperations elasticsearchOperations;
    @Override
    public SliceResponse<SearchMemberResponse> getMembersByName(String name, String cursor) {

        NativeSearchQuery query = createQueryWithNameDescendingFollowerCount(name, cursor);
        SearchHits<SearchMember> results = elasticsearchOperations.search(query, SearchMember.class);

        List<SearchMemberResponse> responses = results
                .stream()
                .map((SearchHit<SearchMember> searchHit) -> SearchMemberResponse
                        .from(searchHit.getContent()))
                .collect(Collectors.toList());

        return convertToSlice(responses);
    }

    private NativeSearchQuery createQueryWithNameDescendingFollowerCount(String name, String cursor) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", name))
                .filter(QueryBuilders.rangeQuery("member_id").lte(cursor));

        queryBuilder.withQuery(query);
        queryBuilder.withSort(SortBuilders.fieldSort("follower_count").order(SortOrder.DESC));
        return queryBuilder.build();
    }


    private SliceResponse<SearchMemberResponse> convertToSlice(List<SearchMemberResponse> searchMemberResponses) {
        validateSearchMemberExist(searchMemberResponses);
        boolean hasNext = existNextPage(searchMemberResponses);
        String nextCursor = generateCursor(searchMemberResponses);
        return SliceResponse.of(searchMemberResponses, hasNext, nextCursor);
    }

    private void validateSearchMemberExist(List<SearchMemberResponse> searchMemberResponses) {
        if (searchMemberResponses.isEmpty()) {
            throw new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private boolean existNextPage(List<SearchMemberResponse> searchQuestions) {
        if (searchQuestions.size() > PAGE_SIZE) {
            searchQuestions.remove(PAGE_SIZE);
            return true;
        }

        return false;
    }

    private String generateCursor(List<SearchMemberResponse> searchMemberResponses) {
        SearchMemberResponse searchMemberResponse = searchMemberResponses.get(searchMemberResponses.size() - 1);

        return String.valueOf(searchMemberResponse.memberId());
    }
}
