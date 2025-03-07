package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.UserEntity;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Booking {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemEntity itemEntity;
    private UserEntity booker;
    private BookingStatus status;
}
