package app.services;

import app.dto.UserBorrowDto;
import app.dto.UserDto;
import app.exception.UserAlreadyExistsException;
import app.exception.UserNoAlreadyExistsException;
import app.mapper.UserBorrowMapper;
import app.mapper.UserMapper;
import app.model.User;
import app.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    private RepositoryUser userRepository;

    @Autowired
    public UserService(RepositoryUser userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }


    public UserDto save(UserDto userDto) {
        Optional<User> entity = userRepository.findByPesel(userDto.getPesel());
        entity.ifPresent((e) -> {
            throw new UserAlreadyExistsException();
        });
        return mapAndSave(userDto);
    }

    private UserDto mapAndSave(UserDto userDto) {
        User entity = UserMapper.toEntity(userDto);
        User savedUser = userRepository.save(entity);
        return UserMapper.toDto(savedUser);
    }

    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(UserNoAlreadyExistsException::new);

    }

    public UserDto update(UserDto userDto) {
        Optional<User> entity = userRepository.findByPesel(userDto.getPesel());

        entity.ifPresent(e -> {
            if(!e.getId().equals(userDto.getId()))
                throw new UserAlreadyExistsException();
        });
        return mapAndSave(userDto);
    }

    public UserDto delete(Integer id) {
        Optional<User> userById = userRepository.findById(id);
        User user = userById.
                orElseThrow(UserNoAlreadyExistsException::new);
        userRepository.deleteById(id);
        return UserMapper.toDto(user);
    }

    public List<UserBorrowDto> findBorrowPublication(Integer id) {
        return userRepository.findById(id)
                .map(User::getBorrowBooks)
                .orElseThrow(UserNoAlreadyExistsException::new)
                .stream()
                .map(UserBorrowMapper::toDto)
                .collect(Collectors.toList());
    }
}
