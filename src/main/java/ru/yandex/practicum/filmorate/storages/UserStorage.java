package ru.yandex.practicum.filmorate.storages;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public List<User> getUsers();

    public User addUser(User user);

    public User updateUser(User user);
}
