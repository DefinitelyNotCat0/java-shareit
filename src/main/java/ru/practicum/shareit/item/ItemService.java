package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto getById(Long id);

    List<ItemDto> getAll();

    List<ItemDto> getAllByUser(Long userId);

    List<ItemDto> getAllAvailableByText(String text);

    ItemDto create(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);

    void deleteById(Long id);
}
