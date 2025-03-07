package ru.practicum.shareit.request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import ru.practicum.shareit.user.UserEntity;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Entity
@Table(name = "requests")
@Data
public class ItemRequest {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;
    private UserEntity requestor;
    private LocalDateTime created;
}
