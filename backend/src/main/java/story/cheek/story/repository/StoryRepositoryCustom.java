package story.cheek.story.repository;

import story.cheek.common.constant.SortType;
import story.cheek.common.dto.SliceResponse;
import story.cheek.highlight.domain.Highlight;
import story.cheek.question.domain.Occupation;
import story.cheek.story.dto.response.StoryResponse;

public interface StoryRepositoryCustom {
    SliceResponse<StoryResponse> findAllByOrderByLikeCountDesc(int pageSize, String cursor, SortType sortType, Occupation occupation);
    SliceResponse<StoryResponse> findAllByOrderByIdDesc(int pageSize, String cursor, SortType sortType, Occupation occupation);
    SliceResponse<StoryResponse> findAllByHighlightOrderByIdDesc(int pageSize, String cursor, Highlight highlight, SortType sortType);
}
