package springrestapidemo.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@RestController
@RequestMapping("/api/v1")
public class UserRestControllerV1 {

    private UserService userService;

    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> findAll() {
        return userService.findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto findById(@PathVariable Long id) {
        return UserDto.toDto(userService.findById(id));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody UserDto userDto) {
        UserEntity user = userService.save(UserDto.toEntity(userDto));
        return UserDto.toDto(user);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long id) {
        UserEntity user = userService.findById(id);
        user.setName(userDto.getName());
        return UserDto.toDto(userService.update(user));
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        UserEntity userEntity = userService.findById(id);
        userService.delete(userEntity);
    }
}