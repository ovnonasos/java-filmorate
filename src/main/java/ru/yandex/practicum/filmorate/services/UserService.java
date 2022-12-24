package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.inMemoryUserStorage = userStorage;
    }

    public boolean validateFriends(User user) {
        return inMemoryUserStorage.getUsers().contains(user);
    }

    public User getUserById(int id) {
        return inMemoryUserStorage.getById(id);
    }

    public List<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    public void addFriend(int id1, int id2) {
        User user1 = inMemoryUserStorage.getById(id1);
        User user2 = inMemoryUserStorage.getById(id2);
        if (!validateFriends(user1) || !validateFriends(user2)) {
            throw new FriendException("Пользователь не существует");
        } else {
            user1.getFriendsIds().add(user2.getId());
            user2.getFriendsIds().add(user1.getId());
        }
    }

    public void removeFriend(int id1, int id2) {
        User user1 = inMemoryUserStorage.getById(id1);
        User user2 = inMemoryUserStorage.getById(id2);
        if (!validateFriends(user1) || !validateFriends(user2)) {
            throw new FriendException("Пользователь не существует");
        } else {
            user1.getFriendsIds().remove(Integer.valueOf(user2.getId()));
            user2.getFriendsIds().remove(Integer.valueOf(user1.getId()));
        }
    }

    public List<User> commonFriends(int id1, int id2) {
        User user1 = inMemoryUserStorage.getById(id1);
        User user2 = inMemoryUserStorage.getById(id2);
        List<Integer> common = new ArrayList<>(user1.getFriendsIds());
        common.retainAll(user2.getFriendsIds());
        return common.stream().map(id -> inMemoryUserStorage.getUsers().get(id - 1))
                .collect(Collectors.toList());
    }

    public List<User> getUserFriends(int id) {
        return inMemoryUserStorage.getById(id).getFriendsIds()
                .stream()
                .map(inMemoryUserStorage::getById)
                .collect(Collectors.toList());
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }
}
