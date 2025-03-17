package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

import static ru.practicum.shareit.constant.ShareItConstants.USER_ID_HEADER;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto createRequest(@RequestHeader(USER_ID_HEADER) Long userId,
                                        @Valid @RequestBody ItemRequestCreateDto itemRequestCreateDto) {
        return itemRequestService.create(userId, itemRequestCreateDto);
    }

    @GetMapping
    public List<ItemRequestDto> getUserItemRequests(@RequestHeader(USER_ID_HEADER) Long userId) {
        return itemRequestService.getAllByRequestorId(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllItemRequests() {
        return itemRequestService.getAll();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@PathVariable Long requestId) {
        return itemRequestService.getById(requestId);
    }
}
