package springrestapidemo.service.amazon;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.service.amazon.util.AmazonClientService;
import springrestapidemo.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Nikita Gvardeev
 * 06.01.2022
 */

@Service
public class AmazonS3FileService extends AmazonClientService {

    private String location;

    public FileEntity uploadFileToAmazon(FileEntity fileEntity, MultipartFile multipartFile) {
        uploadMultipartFile(multipartFile);

        fileEntity.setLocation(location);
        return fileEntity;
    }

    public void removeFileFromAmazon(FileEntity fileEntity) {
        String fileName = fileEntity
                .getLocation()
                .substring(fileEntity.getLocation().lastIndexOf("/") + 1);

        getClient().deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
    }

    private void uploadMultipartFile(MultipartFile multipartFile) {
        try {
            File file = FileUtils.convertedMultipartToFile(multipartFile);
            String fileName = FileUtils.generatedFileName(multipartFile);

            uploadFile(fileName, file);

            file.delete();

            location = getUrl().concat(fileName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile(String fileName, File file) {
        getClient()
                .putObject(
                        new PutObjectRequest(getBucketName(), fileName, file));
//                                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
