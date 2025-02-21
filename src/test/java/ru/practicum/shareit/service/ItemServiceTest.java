package ru.practicum.shareit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemInMemoryRepository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.*;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    private ItemService itemService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserRepository userRepository = new UserInMemoryRepository();
        userService = new UserServiceImpl(userRepository, new UserMapper());
        itemService = new ItemServiceImpl(new ItemInMemoryRepository(), new ItemMapper(), userRepository);


        userService.create(new UserDto(null, "name1", "email1@gmail.com"));
        userService.create(new UserDto(null, "name2", "email2@gmail.com"));
        userService.create(new UserDto(null, "name3", "email3@gmail.com"));
        userService.create(new UserDto(null, "name4", "email4@gmail.com"));

        itemService.create(1L,
                new ItemDto(null, "user1Name1", "description1_1",
                        true, null, null));
        itemService.create(1L,
                new ItemDto(null, "user1Name2", "description1_2",
                        false, null, null));
        itemService.create(1L,
                new ItemDto(null, "userName", "description1_3",
                        true, null, null));

        itemService.create(2L,
                new ItemDto(null, "user2Name1", "description2_1",
                        false, null, null));
        itemService.create(2L,
                new ItemDto(null, "userName", "description2_2",
                        true, null, null));
    }

    @Test
    void getByIdTest() {
        ItemDto itemDto = itemService.getById(1L);
        assertEquals("user1Name1", itemDto.getName());
        assertEquals("description1_1", itemDto.getDescription());
        assertTrue(itemDto.getAvailable());
        assertEquals(1L, itemDto.getOwner().getId());
    }

    @Test
    void getAllTest() {
        assertEquals(5, itemService.getAll().size());
    }

    @Test
    void getAllByUserTest() {
        assertEquals(3, itemService.getAllByUser(1L).size());
    }

    @Test
    void getAllAvailableByTextTest() {
        assertEquals(0, itemService.getAllAvailableByText("description1_2").size());
        assertEquals(1, itemService.getAllAvailableByText("description1_1").size());
        assertEquals(2, itemService.getAllAvailableByText("userName").size());
        assertEquals(2, itemService.getAllAvailableByText("USERNAME").size());
    }

    @Test
    void createTest() {
        assertThrows(NotFoundException.class,
                () -> itemService.create(
                        100L,
                        new ItemDto(null, "userName", "description",
                                true, null, null)
                )
        );
        assertThrows(ValidationException.class,
                () -> itemService.create(
                        1L,
                        new ItemDto(null, "userName", "description",
                                null, null, null)
                )
        );
        assertThrows(ValidationException.class,
                () -> itemService.create(
                        1L,
                        new ItemDto(null, null, "description",
                                true, null, null)
                )
        );
        assertThrows(ValidationException.class,
                () -> itemService.create(
                        1L,
                        new ItemDto(null, "userName", null,
                                true, null, null)
                )
        );

        itemService.create(1L, new ItemDto(null, "userName", "description",
                true, null, null));
        assertEquals(6, itemService.getAll().size());
    }

    @Test
    void updateTest() {
        assertThrows(ValidationException.class,
                () -> itemService.update(
                        1L,
                        null,
                        new ItemDto(null, "userName", "description",
                                true, null, null)
                )
        );
        assertThrows(NotFoundException.class,
                () -> itemService.update(
                        1L,
                        100L,
                        new ItemDto(null, "userName", "description",
                                true, null, null)
                )
        );
        assertThrows(NotFoundException.class,
                () -> itemService.update(
                        100L,
                        1L,
                        new ItemDto(null, "userName", "description",
                                true, null, null)
                )
        );
        assertThrows(ForbiddenException.class,
                () -> itemService.update(
                        2L,
                        1L,
                        new ItemDto(null, "userNameUpdated", "descriptionUpdated",
                                true, null, null)
                )
        );

        itemService.update(
                1L,
                1L,
                new ItemDto(null, "userNameUpdated", "descriptionUpdated",
                        true, null, null)
        );
        assertEquals(5, itemService.getAll().size());

        ItemDto updatedItemDto = itemService.getById(1L);
        assertEquals("userNameUpdated", updatedItemDto.getName());
        assertEquals("descriptionUpdated", updatedItemDto.getDescription());
        assertTrue(updatedItemDto.getAvailable());
    }

    @Test
    void deleteTest() {
        itemService.deleteById(1L);
        assertEquals(4, userService.getAll().size());
    }
}
