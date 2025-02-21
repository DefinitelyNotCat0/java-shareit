package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getById(Long id);

    List<UserDto> getAll();

    UserDto create(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    void deleteById(Long id);
}
