package story.cheek.mail.service;

import static story.cheek.common.exception.ErrorCode.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;
import story.cheek.common.exception.CodeGenerationException;

@Component
public class CodeGenerator {

    private static final int CODE_LENGTH = 6;

    public String generate() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < CODE_LENGTH; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CodeGenerationException(FAILED_GENERATE_CODE);
        }
    }

}
