package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemRequestItemDto;
import ru.practicum.shareit.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemRequestDto {

    List<ItemRequestItemDto> items;
    private Long id;
    private String description;
    private UserEntity requestor;
    private LocalDateTime created;
}
