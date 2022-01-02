package springrestapidemo.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springrestapidemo.dto.EventDto;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1")
public class EventRestControllerV1 {

    private EventService eventService;

    public EventRestControllerV1(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<EventDto> findAll() {
        return eventService.findAll()
                .stream()
                .map(EventDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/{id}")
    public EventDto findById(@PathVariable Long id) {
        return EventDto.toDto(eventService.findById(id));
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto save(@RequestBody EventDto eventDto) {
        EventEntity event = eventService.save(EventDto.toEntity(eventDto));
        return EventDto.toDto(event);
    }

    @PutMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto update(@RequestBody EventDto eventDto, @PathVariable Long id) {
        EventEntity event = eventService.findById(id);
        event.setDescription(eventDto.getDescription());
        return EventDto.toDto(eventService.update(event));
    }

    @DeleteMapping("/events/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        EventEntity event = eventService.findById(id);
        eventService.delete(event);
    }
}