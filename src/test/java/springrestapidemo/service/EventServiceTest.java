package springrestapidemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.EventRepository;

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
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenFindById() {
        EventEntity expected = eventEntity("Description1");

        given(eventRepository.findById(expected.getId())).willReturn(Optional.of(expected));

        EventEntity actual = eventService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void whenFindAll() {
        List<EventEntity> expected = List
                .of(
                        eventEntity("Description1"),
                        eventEntity("Description2"));

        given(eventRepository.findAll()).willReturn(expected);

        List<EventEntity> actual = eventService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSave() {
        EventEntity expected = eventEntity("Description1");
        EventEntity saved = new EventEntity(
                null, UserEntity.builder().build(), FileEntity.builder().build(), "Description1");

        given(eventRepository.save(saved))
                .willReturn(expected);

        EventEntity actual = eventService.save(saved);

        assertEquals(expected, actual);
    }

    @Test
    public void whenUpdate() {
        EventEntity expected = eventEntity("Description1");
        EventEntity updated = eventEntity("Description1");

        given(eventRepository.save(any(EventEntity.class))).willReturn(expected);

        EventEntity actual = eventService.update(updated);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDelete() {
        EventEntity deleted = eventEntity("Description2");
        eventService.delete(deleted);

        verify(eventRepository).delete(deleted);
    }

    private EventEntity eventEntity(String description) {
        return EventEntity
                .builder()
                .id(1L)
                .userEntity(UserEntity.builder().build())
                .fileEntity(FileEntity.builder().build())
                .description(description)
                .build();
    }
}