package springrestapidemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springrestapidemo.dto.EventDto;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.repository.EventRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventDto findById(Long id) {
        EventEntity eventEntity = eventRepository.findById(id).orElse(null);

        if (Objects.isNull(eventEntity))
            throw new RuntimeException("Event by id: " + id + " not found");

        return EventDto.toDto(eventEntity);
    }

    public List<EventDto> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(EventDto::toDto)
                .collect(Collectors.toList());
    }

    public EventDto save(EventDto eventDto) {
        return EventDto
                .toDto(eventRepository
                        .save(EventDto
                                .toEntity(eventDto)));
    }

    public EventDto update(EventDto eventDto, Long id) {
        EventEntity eventEntity = eventRepository.findById(id).orElse(null);

        if (Objects.isNull(eventEntity))
            throw new RuntimeException("Event by id: " + id + " not found");

        eventEntity.setDescription(eventDto.getDescription());
        return EventDto.toDto(eventRepository.save(eventEntity));
    }

    public void delete(Long id) {
        EventEntity eventEntity = eventRepository.findById(id).orElse(null);

        if (Objects.isNull(eventEntity))
            throw new RuntimeException("Event by id: " + id + " not found");

        eventRepository.delete(eventEntity);
    }
}