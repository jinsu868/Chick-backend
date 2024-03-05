package story.cheek.story.dto.request;

import java.util.List;

public record NotificationDeleteRequest(
        List<Long> deleteRequestIds
) {
}
