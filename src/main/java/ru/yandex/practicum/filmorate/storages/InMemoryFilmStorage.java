package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private int id = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> getFilms() {
        return new ArrayList<Film>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        film.validate();
        id++;
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        film.validate();
        if (films.containsKey(film.getId())){
            films.remove(film.getId());
            films.put(film.getId(), film);
            return film;
        } else {
            throw new NotFoundException();
        }
    }
}
