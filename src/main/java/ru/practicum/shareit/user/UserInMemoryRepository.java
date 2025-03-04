package ru.practicum.shareit.user;

import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class UserInMemoryRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> findById(Long id) {
        if (!users.containsKey(id)) {
            log.debug("User was not found for id = {}", id);
            return Optional.empty();
        }
        log.debug("User was found for id = {}", id);
        return Optional.of(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User create(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.debug("User with id {} was created", user.getId());
        return user;
    }

    @Override
    public User update(User newUser) {
        User user = users.get(newUser.getId());
        user.setName(!StringUtil.isNullOrEmpty(newUser.getName()) ? newUser.getName() : user.getName());
        user.setEmail(!StringUtil.isNullOrEmpty(newUser.getEmail()) ? newUser.getEmail() : user.getEmail());

        log.debug("User with id {} was updated", newUser.getId());
        return user;
    }

    @Override
    public void deleteById(Long id) {
        users.remove(id);
        log.debug("User with id {} was deleted", id);
    }

    @Override
    public boolean existsById(Long id) {
        return users.containsKey(id);
    }

    private Long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0L);
        return ++currentMaxId;
    }
}
