package springrestapidemo.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    private EventService eventService;

    public EventRestControllerV1(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public List<EventDto> findAll() {
        return eventService.findAll()
                .stream()
                .map(EventDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public EventDto findById(@PathVariable Long id) {
        return EventDto.toDto(eventService.findById(id));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto save(@RequestBody EventDto eventDto) {
        EventEntity event = eventService.save(EventDto.toEntity(eventDto));
        return EventDto.toDto(event);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @ResponseStatus(HttpStatus.OK)
    public EventDto update(@RequestBody EventDto eventDto, @PathVariable Long id) {
        EventEntity event = eventService.findById(id);
        event.setDescription(eventDto.getDescription());
        return EventDto.toDto(eventService.update(event));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        EventEntity event = eventService.findById(id);
        eventService.delete(event);
    }
}