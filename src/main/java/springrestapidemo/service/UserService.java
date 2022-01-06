package springrestapidemo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.UserRepository;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity update(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }
}