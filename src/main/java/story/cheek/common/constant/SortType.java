package story.cheek.common.constant;

public enum SortType {
    LIKE("LIKE", "좋아요순"),
    LATEST("LATEST", "최신순");

    private String title;

    private String value;

    private SortType(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
