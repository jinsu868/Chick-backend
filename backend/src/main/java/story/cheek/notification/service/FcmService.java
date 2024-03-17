package story.cheek.notification.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static story.cheek.common.exception.ErrorCode.FAILED_COMMUNICATION_FIREBASE;
import static story.cheek.common.exception.ErrorCode.FAILED_FCM_ACCESS_TOKEN_REQUEST;
import static story.cheek.common.exception.ErrorCode.FAILED_JSON_CONVERT;
import static story.cheek.common.exception.ErrorCode.FCM_TOKEN_NOT_FOUND;
import static story.cheek.common.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import story.cheek.common.exception.BusinessException;
import story.cheek.common.exception.NotFoundMemberException;
import story.cheek.member.repository.MemberRepository;
import story.cheek.notification.domain.FcmToken;
import story.cheek.notification.domain.Notification;
import story.cheek.notification.dto.request.StoryNotificationMessage;
import story.cheek.notification.dto.request.StoryNotificationMessage.Data;
import story.cheek.notification.dto.request.StoryNotificationMessage.Message;
import story.cheek.notification.repository.FcmTokenRepository;

@Service
@RequiredArgsConstructor
public class FcmService {

    private static final String FIREBASE_ADMIN_KEY = "firebase/cheek-fcm-firebase-adminsdk-yb4eu-9cd5509c24.json";
    private static final String GOOGLE_AUTH_URL = "https://www.googleapis.com/auth/cloud-platform";
    private static final String FIREBASE_NOTIFICATION_REQUEST_URL = "https://fcm.googleapis.com/v1/projects/%s/messages:send";
    private static final String BEARER = "Bearer ";

    @Value("${fcm.project.id}")
    private String projectId;

    private final FcmTokenRepository fcmTokenRepository;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public void sendMessage(Notification notification) {
        String message = createMessage(notification);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, BEARER + getAccessToken());
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(message, headers);
        String requestUrl = String.format(FIREBASE_NOTIFICATION_REQUEST_URL, projectId);
        ResponseEntity<String> exchange = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        if (exchange.getStatusCode().isError()) {
            throw new BusinessException(FAILED_COMMUNICATION_FIREBASE);
        }
    }

    private String getAccessToken() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FIREBASE_ADMIN_KEY).getInputStream())
                    .createScoped(List.of(GOOGLE_AUTH_URL));

            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            throw new BusinessException(FAILED_FCM_ACCESS_TOKEN_REQUEST);
        }
    }

    private String createMessage(Notification notification) {
        Long receiverId = notification.getReceiverId();
        validateExistMember(receiverId);
        FcmToken fcmToken = findFcmToken(receiverId);
        StoryNotificationMessage request = StoryNotificationMessage.of(
                false,
                Message.of(Data.from(notification), fcmToken.getToken())
        );

        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new BusinessException(FAILED_JSON_CONVERT);
        }
    }

    private void validateExistMember(Long receiverId) {
        if (!memberRepository.existsById(receiverId)) {
            throw new NotFoundMemberException(MEMBER_NOT_FOUND);
        }
    }

    private FcmToken findFcmToken(Long receiverId) {
        return fcmTokenRepository.findByMemberId(receiverId)
                .orElseThrow(() -> new BusinessException(FCM_TOKEN_NOT_FOUND));
    }
}
