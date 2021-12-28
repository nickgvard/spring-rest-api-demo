package springrestapidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springrestapidemo.entity.EventEntity;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */
@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> { }