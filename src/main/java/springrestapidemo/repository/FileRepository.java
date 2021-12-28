package springrestapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springrestapidemo.entity.FileEntity;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> { }