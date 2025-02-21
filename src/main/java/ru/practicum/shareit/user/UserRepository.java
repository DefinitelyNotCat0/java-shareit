package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAll();

    User create(User user);

    User update(User newUser);

    void deleteById(Long id);

    boolean existsById(Long id);
}
