package springrestapidemo.service;

import org.springframework.stereotype.Service;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.repository.EventRepository;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventEntity findById(Long id) {
        return eventRepository.getById(id);
    }

    public List<EventEntity> findAll() {
        return eventRepository.findAll();
    }

    public EventEntity save(EventEntity eventEntity) {
        return eventRepository.save(eventEntity);
    }

    public EventEntity update(EventEntity eventEntity) {
        return eventRepository.save(eventEntity);
    }

    public void delete(EventEntity eventEntity) {
        eventRepository.delete(eventEntity);
    }
}