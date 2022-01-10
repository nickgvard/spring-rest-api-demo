package springrestapidemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springrestapidemo.dto.UserDto;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if (Objects.isNull(userEntity))
            throw new UsernameNotFoundException("User by id: " + id + " not found");

        return UserDto.toDto(userEntity);
    }

    public UserDto findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (Objects.isNull(userEntity))
            throw new UsernameNotFoundException("User by email: " + email + " not found");

        return UserDto.toDto(userEntity);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public UserDto save(UserDto userDto) {
        return UserDto
                .toDto(userRepository
                        .save(UserDto
                                .toEntity(userDto)));
    }

    public UserDto update(UserDto userDto, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if (Objects.isNull(userEntity))
            throw new UsernameNotFoundException("User by id: " + id + " not found");

        userEntity.setEmail(userDto.getEmail());

        return UserDto.toDto(userRepository.save(userEntity));
    }

    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if (Objects.isNull(userEntity))
            throw new UsernameNotFoundException("User by id: " + id + " not found");

        userRepository.delete(userEntity);
    }
}