package springrestapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springrestapidemo.entity.RoleEntity;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}