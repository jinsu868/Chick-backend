package story.cheek.notification.domain;

public enum NotificationType {
    STORY("STORY", "스토리"),
    APPROVE("APPROVE", "승인");

    private String key;
    private String value;

    private NotificationType(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
