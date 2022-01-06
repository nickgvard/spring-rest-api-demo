package springrestapidemo.util;

import lombok.Cleanup;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author Nikita Gvardeev
 * 06.01.2022
 */
public class FileUtils {

    public static File convertedMultipartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        @Cleanup FileOutputStream outputStream = new FileOutputStream(convertedFile);
        outputStream.write(file.getBytes());
        return convertedFile;
    }

    public static String generatedFileName(MultipartFile file) {
        return new Date().getTime() + "-"
                + Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s", "_");
    }
}
