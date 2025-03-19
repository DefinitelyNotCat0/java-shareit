package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CommentItemRequestDto;
import ru.practicum.shareit.item.dto.ItemCreateRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;

import java.util.List;

import static ru.practicum.shareit.constant.ShareItConstants.USER_ID_HEADER;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader(USER_ID_HEADER) Long userId,
                           @RequestBody ItemCreateRequestDto itemCreateRequestDto) {
        return itemService.create(userId, itemCreateRequestDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(USER_ID_HEADER) Long userId,
                              @PathVariable Long itemId,
                              @RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {
        return itemService.update(userId, itemId, itemUpdateRequestDto);
    }

    @GetMapping
    public List<ItemDto> getAllUserItems(@RequestHeader(USER_ID_HEADER) Long userId) {
        return itemService.getAllByUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByText(@RequestParam String text) {
        return itemService.getAllAvailableByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(USER_ID_HEADER) Long userId,
                                 @PathVariable Long itemId,
                                 @RequestBody CommentItemRequestDto commentItemRequestDto) {
        return itemService.addComment(userId, itemId, commentItemRequestDto);
    }
}
