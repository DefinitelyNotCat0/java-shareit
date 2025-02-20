package ru.practicum.shareit.user;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + id));
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (StringUtil.isNullOrEmpty(userDto.getEmail())) {
            throw new ValidationException("Email is empty");
        }
        if (getUserCountByEmail(userDto.getEmail()) > 0) {
            throw new ConflictException("Email already exists");
        }

        User user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.create(user));
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        if (id == null) {
            throw new ValidationException("Id must not be empty");
        }
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id = " + id);
        }
        if (getUserCountByEmail(userDto.getEmail()) > 0) {
            throw new ConflictException("Email already exists");
        }

        User user = userMapper.toUser(userDto);
        user.setId(id);
        return userMapper.toUserDto(userRepository.update(user));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private long getUserCountByEmail(String newEmail) {
        return userRepository.findAll()
                .stream()
                .filter(user -> Objects.equals(newEmail, user.getEmail()))
                .count();
    }
}
