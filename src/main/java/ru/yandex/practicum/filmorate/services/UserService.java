package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService (InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public boolean validateFriends(User user){
        return inMemoryUserStorage.getUsers().contains(user);
    }

    public User getUserById(int id){
        try {
            return getInMemoryUserStorage().getUsers().get(id-1);
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }

    public void addFriend(int id1, int id2){
        try {
            User user1 = inMemoryUserStorage.getUsers().get(id1 - 1);
            User user2 = inMemoryUserStorage.getUsers().get(id2 - 1);
            if (user1.getFriendsIds().contains(user2.getId())) {
                throw new FriendException("Пользователь уже находится в друзьях");
            } else if (!validateFriends(user1) || !validateFriends(user2)) {
                throw new FriendException("Пользователь не существует");
            } else {
                user1.getFriendsIds().add(user2.getId());
                user2.getFriendsIds().add(user1.getId());
            }
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }

    public void removeFriend(int id1, int id2){
        try {
            User user1 = inMemoryUserStorage.getUsers().get(id1 - 1);
            User user2 = inMemoryUserStorage.getUsers().get(id2 - 1);
            if (!user1.getFriendsIds().contains(user2.getId())) {
                throw new FriendException("Пользователя нет в друзьях");
            } else if (!validateFriends(user1) || !validateFriends(user2)) {
                throw new FriendException("Пользователь не существует");
            } else {
                user1.getFriendsIds().remove(Integer.valueOf(user2.getId()));
                user2.getFriendsIds().remove(Integer.valueOf(user1.getId()));
            }
        }
        catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }

    public List<User> commonFriends(int id1, int id2){
        try {
            User user1 = inMemoryUserStorage.getUsers().get(id1 - 1);
            User user2 = inMemoryUserStorage.getUsers().get(id2 - 1);
            List<Integer> common = new ArrayList<>(user1.getFriendsIds());
            common.retainAll(user2.getFriendsIds());
            return common.stream().map(id -> getInMemoryUserStorage().getUsers().get(id - 1))
                    .collect(Collectors.toList());
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }

    public InMemoryUserStorage getInMemoryUserStorage() {
        return inMemoryUserStorage;
    }

    public List<User> getUserFriends(int id) {
        try {
            return getInMemoryUserStorage().getUsers().get(id-1).getFriendsIds()
                    .stream()
                    .map(x -> getInMemoryUserStorage().getUsers().get(x - 1))
                    .collect(Collectors.toList());
        } catch (IndexOutOfBoundsException ex){
                throw new NotFoundException();
            }
    }
}
