package story.cheek.application.repository;

import story.cheek.application.dto.response.ApplicationResponse;
import story.cheek.common.dto.SliceResponse;

public interface ApplicationRepositoryCustom {

    SliceResponse<ApplicationResponse> findAllExceptDeleted(String cursor);
}
