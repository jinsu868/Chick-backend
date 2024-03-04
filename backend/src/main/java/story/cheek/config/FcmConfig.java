package story.cheek.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Slf4j
public class FcmConfig {

    private static final String FIREBASE_ADMIN_KEY = "firebase/cheek-fcm-firebase-adminsdk-yb4eu-9cd5509c24.json";

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource(FIREBASE_ADMIN_KEY);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException {}", e.getMessage());
        } catch (IOException e) {
            log.error("FirebaseOptions IOException {}", e.getMessage());
        }
    }
}
