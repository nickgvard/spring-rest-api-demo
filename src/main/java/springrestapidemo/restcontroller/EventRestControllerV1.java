package springrestapidemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import springrestapidemo.dto.EventDto;
import springrestapidemo.service.EventService;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventRestControllerV1 {

    private final EventService eventService;

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<List<EventDto>> findAll() {
        return ResponseEntity.ok(
                eventService.findAll());
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    public ResponseEntity<EventDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<EventDto> save(@RequestBody EventDto eventDto) {
        return ResponseEntity.ok(eventService.save(eventDto));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<EventDto> update(@RequestBody EventDto eventDto, @PathVariable Long id) {
        return ResponseEntity.ok(eventService.update(eventDto, id));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }
}