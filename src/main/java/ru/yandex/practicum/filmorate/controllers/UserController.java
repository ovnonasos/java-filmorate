package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private int id = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers(){
        log.info("Получен запрос на получение всех пользователей");
        return new ArrayList<User>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        log.info("Получен запрос на добавление пользователя");
        user.validate();
        id++;
        user.setId(id);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        log.info("Получен запрос на обновление пользователя");
        user.validate();
            if (users.containsKey(user.getId())){
                users.remove(user.getId());
                users.put(user.getId(), user);
                return user;
            } else {
                throw new ValidationException("Неверный id");
            }
    }
}