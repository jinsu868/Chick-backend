package story.cheek.mail.service;

import org.springframework.stereotype.Component;

@Component
public class DomainExtractor {
    private static final String DELIMITER = "@";

    public String extract(String mail) {
        return mail.substring(mail.indexOf(DELIMITER) + 1);
    }
}
