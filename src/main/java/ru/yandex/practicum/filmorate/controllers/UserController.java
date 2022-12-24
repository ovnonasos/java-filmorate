package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        log.info("Получен запрос на добавление пользователя");
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Получен запрос на обновление пользователя");
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable int id){
        log.info("Получен запрос на получение пользователя по id");
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос от пользователя " + id + " на добавление в друзья пользователя " + id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос от пользователя " + id + " на удаление пользователя " + id);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> returnFriends(@PathVariable int id) {
        log.info("Получен запрос от пользователя " + id + " на просмотр друзей");
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> returnCommonFriends(@PathVariable int id, @PathVariable int otherId){
        log.info("Получен запрос от пользователя " + id + " на просмотр общих друзей с пользователем " + id);
        return userService.commonFriends(id, otherId);

    }
}