package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int id = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getFilms(){
        log.info("Получен запрос на получение всех фильмов");
        return new ArrayList<Film>(films.values());
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film){
        log.info("Получен запрос на добавление фильма");
        film.validate();
        id++;
        film.setId(id);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        log.info("Получен запрос на обновление фильма");
        film.validate();
            if (films.containsKey(film.getId())){
                films.remove(film.getId());
                films.put(film.getId(), film);
                return film;
            } else {
                throw new ValidationException("Неверный id");
            }
    }
}
