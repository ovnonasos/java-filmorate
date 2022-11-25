package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private int id = 1;
    private final ArrayList<User> users = new ArrayList<>();

    @GetMapping
    public ArrayList<User> getUsers(){
        log.info("Получен запрос на получение всех пользователей");
        return users;
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        log.info("Получен запрос на добавление пользователя");
        if (users.size() < id){
            id = users.size();
        }
        id++;
        if (user.getName() == null){
            user.setName(user.getLogin());
        }
        user.setId(id);
        user.validate();
        users.add(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        log.info("Получен запрос на обновление пользователя");
        user.validate();
        for(User user1 : users){
            if (user.getId() == user1.getId()){
                users.remove(user1);
                users.add(user);
                return user;
            } else if (user.getId() > id){
                throw new ValidationException("Слишком большой id");
            }
        }
        if (users.size() < id){
            id = users.size()-1;
        }
        id++;
        user.setId(id);
        users.add(user);
        return user;
    }
}
