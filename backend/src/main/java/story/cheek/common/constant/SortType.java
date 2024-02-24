package story.cheek.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
    FOLLOWING("FOLLOWING", "내가 팔로우 중인 유저 검색"),
    FOLLOWER("FOLLOWER", "나를 팔로우 중인 유저 검색"),
    LIKE("LIKE", "좋아요순"),
    LATEST("LATEST", "최신순");

    private final String title;

    private final String value;
}
