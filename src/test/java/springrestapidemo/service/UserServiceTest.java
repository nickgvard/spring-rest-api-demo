package springrestapidemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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
    public void findById() {
        UserEntity expected = userEntity("Nick");

        given(userRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        UserEntity actual = userService.findById(expected.getId());

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void findAll() {
        List<UserEntity> expected = List.of(userEntity("Nick"), userEntity("Bob"));

        given(userRepository.findAll()).willReturn(expected);

        List<UserEntity> actual = userService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void save() {
        UserEntity expected = userEntity("Nick");
        UserEntity saved = new UserEntity(null, "Nick", null);

        given(userRepository.save(saved))
                .willReturn(expected);

        UserEntity actual = userService.save(saved);

        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        UserEntity expected = userEntity("Nick");
        UserEntity updated = userEntity("Nick");

        given(userRepository.save(any(UserEntity.class))).willReturn(expected);

        UserEntity actual = userService.update(updated);

        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        UserEntity deleted = userEntity("Bob");
        userService.delete(deleted);

        verify(userRepository).delete(deleted);
    }

    private UserEntity userEntity(String name) {
        return UserEntity
                .builder()
                .id(1L)
                .name(name)
                .build();
    }
}