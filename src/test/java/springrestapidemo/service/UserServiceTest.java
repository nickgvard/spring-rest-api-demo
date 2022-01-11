package springrestapidemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Nikita Gvardeev
 * 02.01.2022
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenFindById() {
        UserDto expected = userDto("Nick");

        given(userRepository.findById(anyLong())).willReturn(Optional.of(UserDto.toEntity(expected)));

        UserDto actual = userService.findById(expected.getId());

        assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void whenFindAll() {
        List<UserDto> expected = List.of(userDto("Nick"), userDto("Bob"));

        given(userRepository.findAll()).willReturn(expected
                .stream()
                .map(UserDto::toEntity)
                .collect(Collectors.toList()));

        List<UserDto> actual = userService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSave() {
        UserEntity expected = userEntity("Nick");

        UserDto saved = UserDto
                .builder()
                .id(null)
                .firstName("Nick")
                .eventsDto(null)
                .build();

        given(userRepository.save(any())).willReturn(expected);

        UserDto actual = userService.save(saved);

        assertEquals(UserDto.toDto(expected), actual);
    }

    @Test
    public void whenUpdate() {
        UserEntity expected = userEntity("Nick");

        UserDto updated = userDto("Nick");

        given(userRepository.findById(anyLong())).willReturn(Optional.of(expected));

        given(userRepository.save(any())).willReturn(expected);

        UserDto actual = userService.update(updated, updated.getId());

        assertEquals(UserDto.toDto(expected), actual);
    }

    @Test
    public void whenDelete() {
        UserEntity deleted = UserEntity.builder().id(1L).build();

        given(userRepository.findById(anyLong())).willReturn(Optional.of(deleted));

        userService.delete(deleted.getId());

        verify(userRepository).delete(any());
    }

    private UserDto userDto(String name) {
        return UserDto
                .builder()
                .id(1L)
                .firstName(name)
                .build();
    }

    private UserEntity userEntity(String name) {
        return UserEntity
                .builder()
                .id(1L)
                .firstName(name)
                .build();
    }
}