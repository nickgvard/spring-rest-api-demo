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
    public void whenFindById() {
        UserEntity expected = userEntity("Nick");

        given(userRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        UserEntity actual = userService.findById(expected.getId());

        assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void whenFindAll() {
        List<UserEntity> expected = List.of(userEntity("Nick"), userEntity("Bob"));

        given(userRepository.findAll()).willReturn(expected);

        List<UserEntity> actual = userService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSave() {
        UserEntity expected = userEntity("Nick");
        UserEntity saved = UserEntity
                .builder()
                .id(null)
                .firstName("Nick")
                .eventEntities(null)
                .build();

        given(userRepository.save(saved))
                .willReturn(expected);

        UserEntity actual = userService.save(saved);

        assertEquals(expected, actual);
    }

    @Test
    public void whenUpdate() {
        UserEntity expected = userEntity("Nick");
        UserEntity updated = userEntity("Nick");

        given(userRepository.save(any(UserEntity.class))).willReturn(expected);

        UserEntity actual = userService.update(updated);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDelete() {
        UserEntity deleted = userEntity("Bob");
        userService.delete(deleted);

        verify(userRepository).delete(deleted);
    }

    private UserEntity userEntity(String name) {
        return UserEntity
                .builder()
                .id(1L)
                .firstName(name)
                .build();
    }
}