package springrestapidemo.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import springrestapidemo.dto.UserDto;
import springrestapidemo.service.UserService;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;

    @GetMapping()
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping()
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable Long id) {
        return ResponseEntity.ok(userService.update(userDto, id));
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}