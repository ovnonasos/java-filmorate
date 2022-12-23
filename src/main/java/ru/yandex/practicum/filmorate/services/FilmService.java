package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage inMemoryFilmStorage;
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.inMemoryFilmStorage = filmStorage;
        this.inMemoryUserStorage = userStorage;
    }

    public List<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film getFilmById(int id) {
        return inMemoryFilmStorage.getById(id);
    }

    public void addLike(int userId, int filmId) {
        User user = inMemoryUserStorage.getById(userId);
        Film film = inMemoryFilmStorage.getById(filmId);
        if (!user.getLikedFilms().contains(film)) {
            film.setLikes(film.getLikes() + 1);
            film.getLikers().add(user);
            user.getLikedFilms().add(film);
        } else {
            throw new ValidationException("У фильма уже стоит лайк");
        }

    }

    public void removeLike(int userId, int filmId) {
        User user = inMemoryUserStorage.getById(userId);
        Film film = inMemoryFilmStorage.getById(filmId);
        if (film.getLikers().contains(user) && !user.getLikedFilms().contains(film)) {
            film.setLikes(film.getLikes() - 1);
            user.getLikedFilms().remove(film);
            film.getLikers().remove(user);
        } else {
            throw new ValidationException("У фильма нет лайка");
        }
    }

    public List<Film> getTopFilms(int count) {
        return inMemoryFilmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }
}
