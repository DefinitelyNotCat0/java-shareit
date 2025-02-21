package ru.practicum.shareit.item;

import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ItemInMemoryRepository implements ItemRepository {

    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Optional<Item> findById(Long id) {
        if (!items.containsKey(id)) {
            log.debug("Item was not found for id = {}", id);
            return Optional.empty();
        }
        log.debug("Item was found for id = {}", id);
        return Optional.of(items.get(id));
    }

    @Override
    public List<Item> findAll() {
        return items.values().stream().toList();
    }

    @Override
    public Item create(Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        log.debug("Item with id {} was created", item.getId());
        return item;
    }

    @Override
    public Item update(Item newItem) {
        Item item = items.get(newItem.getId());
        if (!item.getOwner().getId().equals(newItem.getOwner().getId())) {
            throw new ForbiddenException("You can only edit your items");
        }

        item.setName(!StringUtil.isNullOrEmpty(newItem.getName()) ?
                newItem.getName() : item.getName());
        item.setDescription(!StringUtil.isNullOrEmpty(newItem.getDescription()) ?
                newItem.getDescription() : item.getDescription());
        item.setAvailable(newItem.getAvailable() != null ? newItem.getAvailable() : item.getAvailable());

        log.debug("Item with id {} was updated", newItem.getId());
        return item;
    }

    @Override
    public void deleteById(Long id) {
        items.remove(id);
        log.debug("Item with id {} was deleted", id);
    }

    @Override
    public boolean existsById(Long id) {
        return items.containsKey(id);
    }

    private Long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0L);
        return ++currentMaxId;
    }
}
