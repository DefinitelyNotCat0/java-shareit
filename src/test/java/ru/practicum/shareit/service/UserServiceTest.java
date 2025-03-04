package ru.practicum.shareit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserInMemoryRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(new UserInMemoryRepository(), new UserMapper());

        userService.create(new UserDto(null, "name1", "email1@gmail.com"));
        userService.create(new UserDto(null, "name2", "email2@gmail.com"));
        userService.create(new UserDto(null, "name3", "email3@gmail.com"));
        userService.create(new UserDto(null, "name4", "email4@gmail.com"));
    }

    @Test
    void getByIdTest() {
        UserDto userDto = userService.getById(1L);
        assertEquals("name1", userDto.getName());
        assertEquals("email1@gmail.com", userDto.getEmail());
    }

    @Test
    void getAllTest() {
        assertEquals(4, userService.getAll().size());
    }

    @Test
    void createTest() {
        assertThrows(ConflictException.class,
                () -> userService.create(
                        new UserDto(null, "name5", "email1@gmail.com")
                )
        );

        assertThrows(ValidationException.class,
                () -> userService.create(
                        new UserDto(null, "name5", null)
                )
        );

        userService.create(new UserDto(null, "name5", "email5@gmail.com"));
        assertEquals(5, userService.getAll().size());
    }

    @Test
    void updateTest() {
        assertThrows(ValidationException.class,
                () -> userService.update(
                        null,
                        new UserDto(null, "name44", "email44@gmail.com")
                )
        );
        assertThrows(NotFoundException.class,
                () -> userService.update(
                        100L,
                        new UserDto(null, "name5", "email44@gmail.com")
                )
        );

        userService.update(4L, new UserDto(null, "name44", "email44@gmail.com"));
        assertEquals(4, userService.getAll().size());

        UserDto updatedUserDto = userService.getById(4L);
        assertEquals("name44", updatedUserDto.getName());
        assertEquals("email44@gmail.com", updatedUserDto.getEmail());
    }

    @Test
    void deleteTest() {
        userService.deleteById(4L);
        assertEquals(3, userService.getAll().size());
    }
}
