package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService (InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Film getFilmById(int id){
        try {
            return getInMemoryFilmStorage().getFilms().get(id-1);
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }

    public void addLike (int userId, int filmId){
        try {
            User user = inMemoryUserStorage.getUsers().get(userId - 1);
            Film film = inMemoryFilmStorage.getFilms().get(filmId - 1);
            if (!user.getLikedFilms().contains(film)) {
                film.setLikes(film.getLikes() + 1);
                film.getLikers().add(user);
            } else {
                throw new ValidationException("У фильма уже стоит лайк");
            }
        }
        catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }

    public void removeLike(int userId, int filmId){
        try{
        User user = inMemoryUserStorage.getUsers().get(userId-1);
        Film film = inMemoryFilmStorage.getFilms().get(filmId-1);
            if (film.getLikers().contains(user) && !user.getLikedFilms().contains(film)){
                film.setLikes(film.getLikes() - 1);
                user.getLikedFilms().remove(film);
                film.getLikers().remove(user);
            }
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }

    }

    public List<Film> getTop10Films(){
        try {
            return inMemoryFilmStorage.getFilms().stream()
                    .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }

    }

    public List<Film> getTopFilms(int count){
        try {
            return inMemoryFilmStorage.getFilms().stream()
                    .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                    .limit(count)
                    .collect(Collectors.toList());
        } catch (IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }

    }

    public InMemoryFilmStorage getInMemoryFilmStorage() {
        return inMemoryFilmStorage;
    }
}
