package springrestapidemo.service;

import org.springframework.stereotype.Service;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.repository.FileRepository;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
public class FileService {

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity findById(Long id) {
        return fileRepository.findById(id).get();
    }

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    public FileEntity save(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    public FileEntity update(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    public void delete(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
    }
}