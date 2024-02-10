package story.cheek.story.repository;

import story.cheek.common.constant.SortType;
import story.cheek.common.dto.SliceResponse;
import story.cheek.story.dto.response.StoryResponse;

public interface StoryRepositoryCustom {
    SliceResponse<StoryResponse> findAllByOrderByLikeCountDesc(String cursor, SortType sortType);
    SliceResponse<StoryResponse> findAllByOrderByIdDesc(String cursor, SortType sortType);
}
