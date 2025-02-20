package ru.practicum.shareit.item;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

    @Override
    public ItemDto getById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id = " + id));
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(itemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> getAllByUser(Long userId) {
        return itemRepository.findAll()
                .stream()
                .filter(item -> userId.equals(item.getOwner().getId()))
                .map(itemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> getAllAvailableByText(String text) {
        return itemRepository.findAll()
                .stream()
                .filter(item ->
                        item.getAvailable() &&
                                (item.getName().equalsIgnoreCase(text)
                                        || item.getDescription().equalsIgnoreCase(text)))
                .map(itemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto create(Long userId, ItemDto itemDto) {
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Available must not be empty");
        }
        if (StringUtil.isNullOrEmpty(itemDto.getName())) {
            throw new ValidationException("Name must not be empty");
        }
        if (StringUtil.isNullOrEmpty(itemDto.getDescription())) {
            throw new ValidationException("Description must not be empty");
        }

        Item item = itemMapper.toItem(itemDto);
        item.setOwner(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + userId)));
        return itemMapper.toItemDto(itemRepository.create(item));
    }

    @Override
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        if (itemId == null) {
            throw new ValidationException("Id must not be empty");
        }
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException("Item not found with id = " + itemId);
        }

        Item item = itemMapper.toItem(itemDto);
        item.setId(itemId);
        item.setOwner(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + userId)));
        return itemMapper.toItemDto(itemRepository.update(item));
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
