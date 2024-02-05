package story.cheek.question;

public enum Occupation {
    DEVELOP("DEVELOP", "개발"),
    PLAN("PLAN", "기획"),
    MARKETING("MARKETING", "마케팅"),
    ACCOUNTING("ACCOUNTING", "회계");

    private String key;
    private String title;

    private Occupation(String key, String title) {
        this.key = key;
        this.title = title;
    }
}
