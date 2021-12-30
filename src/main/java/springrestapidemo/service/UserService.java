package springrestapidemo.service;

import org.springframework.stereotype.Service;
import springrestapidemo.entity.UserEntity;
import springrestapidemo.repository.UserRepository;

import java.util.List;

/**
 * @author Nikita Gvardeev
 * 28.12.2021
 */

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity fileEntity) {
        return userRepository.save(fileEntity);
    }

    public UserEntity update(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }
}