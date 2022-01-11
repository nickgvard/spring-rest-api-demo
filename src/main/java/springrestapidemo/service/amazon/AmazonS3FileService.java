package springrestapidemo.service.amazon;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.config.AmazonConfig;
import springrestapidemo.dto.FileDto;
import springrestapidemo.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Nikita Gvardeev
 * 06.01.2022
 */

@Service
@RequiredArgsConstructor
public class AmazonS3FileService {

    private final AmazonConfig amazonConfig;
    private final AmazonS3 amazonS3;

    private String location;

    public FileDto uploadFileToAmazon(FileDto fileDto, MultipartFile multipartFile) {
        uploadMultipartFile(multipartFile);

        fileDto.setLocation(location);
        return fileDto;
    }

    public void removeFileFromAmazon(FileDto fileDto) {
        String fileName = fileDto
                .getLocation()
                .substring(fileDto.getLocation().lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(amazonConfig.getBucketName(), fileName));
    }

    private void uploadMultipartFile(MultipartFile multipartFile) {
        try {
            File file = FileUtils.convertedMultipartToFile(multipartFile);
            String fileName = FileUtils.generatedFileName(multipartFile);

            uploadFile(fileName, file);

            file.delete();

            location = amazonConfig.getUrl().concat(fileName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile(String fileName, File file) {
        amazonS3
                .putObject(
                        new PutObjectRequest(amazonConfig.getBucketName(), fileName, file));
    }
}
