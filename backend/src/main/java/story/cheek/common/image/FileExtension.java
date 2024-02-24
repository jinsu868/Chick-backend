package story.cheek.common.image;

import static story.cheek.common.exception.ErrorCode.*;

import java.util.Arrays;
import lombok.Getter;
import story.cheek.common.exception.ErrorCode;
import story.cheek.common.exception.FileExtensionException;

@Getter
public enum FileExtension {
    JPEG(".jpeg"),
    JPG(".jpg"),
    JFIF(".jfif"),
    PNG(".png"),
    SVG(".svg");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public static FileExtension from(String fileName) {
        return Arrays.stream(values())
                .filter(fileExtension -> fileName.endsWith(fileExtension.getExtension()))
                .findFirst()
                .orElseThrow(() -> new FileExtensionException(INVALID_FILE_EXTENSION));
    }
}
