package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(Long id);

    List<Item> findAll();

    Item create(Item item);

    Item update(Item newItem);

    void deleteById(Long id);

    boolean existsById(Long id);
}
