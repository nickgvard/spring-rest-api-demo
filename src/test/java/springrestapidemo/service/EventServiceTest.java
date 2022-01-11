package springrestapidemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import springrestapidemo.dto.EventDto;
import springrestapidemo.dto.FileDto;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.EventEntity;
import springrestapidemo.entity.FileEntity;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        EventDto expected = eventDto("Description1");

        given(eventRepository.findById(expected.getId())).willReturn(Optional.of(EventDto.toEntity(expected)));

        EventDto actual = eventService.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void whenFindAll() {
        List<EventDto> expected = List
                .of(
                        eventDto("Description1"),
                        eventDto("Description2"));

        given(eventRepository.findAll()).willReturn(expected
                .stream()
                .map(EventDto::toEntity)
                .collect(Collectors.toList()));

        List<EventDto> actual = eventService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSave() {
        EventEntity expected = eventEntity("Description1");

        EventDto saved = EventDto
                .builder()
                .id(null)
                .userDto(UserDto.builder().build())
                .fileDto(FileDto.builder().build())
                .description("Description1")
                .build();

        given(eventRepository.save(EventDto.toEntity(saved)))
                .willReturn(expected);

        EventDto actual = eventService.save(saved);

        assertEquals(EventDto.toDto(expected), actual);
    }

    @Test
    public void whenUpdate() {
        EventEntity expected = eventEntity("Description1");

        EventDto updated = eventDto("Description1");

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        given(eventRepository.save(any())).willReturn(expected);

        EventDto actual = eventService.update(updated, updated.getId());

        assertEquals(EventDto.toDto(expected), actual);
    }

    @Test
    public void whenDelete() {
        EventEntity deleted = EventEntity.builder().id(1L).build();

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(deleted));

        eventService.delete(deleted.getId());

        verify(eventRepository).delete(any());
    }

    private EventDto eventDto(String description) {
        return EventDto
                .builder()
                .id(1L)
                .userDto(UserDto.builder().build())
                .fileDto(FileDto.builder().build())
                .description(description)
                .build();
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