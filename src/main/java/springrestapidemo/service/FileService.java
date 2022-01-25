package springrestapidemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springrestapidemo.dto.FileDto;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.repository.FileRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final AmazonS3FileService s3FileService;

    public FileDto findById(Long id) {
        FileEntity fileEntity = fileRepository.findById(id).orElse(null);

        if (Objects.isNull(fileEntity))
            throw new RuntimeException("File by id: " + id + " not found");

        return FileDto.toDto(fileEntity);
    }

    public List<FileDto> findAll() {
        return fileRepository.findAll()
                .stream()
                .map(FileDto::toDto)
                .collect(Collectors.toList());
    }

    public FileDto save(MultipartFile multipartFile) {
        FileDto uploadFile = FileDto.toDto(multipartFile);

        uploadFile = s3FileService.uploadFileToAmazon(uploadFile, multipartFile);

        FileEntity fileEntity = fileRepository.save(FileDto.toEntity(uploadFile));

        return FileDto.toDto(fileRepository.save(fileEntity));
    }

    public FileDto update(FileDto fileDto, Long id) {
        FileEntity fileEntity = fileRepository.findById(id).orElse(null);

        if (Objects.isNull(fileEntity))
            throw new RuntimeException("File by id: " + id + " not found");

        fileEntity.setName(fileDto.getName());
        fileEntity.setLocation(fileDto.getLocation());

        return FileDto.toDto(fileRepository.save(fileEntity));
    }

    public void delete(Long id) {
        FileEntity fileEntity = fileRepository.findById(id).orElse(null);

        if (Objects.isNull(fileEntity))
            throw new RuntimeException("File by id: " + id + " not found");

        s3FileService.removeFileFromAmazon(FileDto.toDto(fileEntity));
        fileRepository.delete(fileEntity);
    }
}