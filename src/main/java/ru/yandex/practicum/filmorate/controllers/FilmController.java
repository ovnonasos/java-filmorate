package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int id = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public ArrayList<Film> getFilms(){
        log.info("Получен запрос на получение всех фильмов");
        return new ArrayList<Film>(films.values());
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film){
        log.info("Получен запрос на добавление фильма");
        for (Integer id1 : films.keySet()){
            id = id1;
        }
        id++;
        film.setId(id);
        film.validate();
        films.put(films.size(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        log.info("Получен запрос на обновление фильма");
        film.validate();
        for(Film film1 : films.values()){
            if (film.getId() == film1.getId()){
                films.remove(film1.getId() - 1);
                films.put(films.size(), film);
                return film;
            } else if (film.getId() > id) {
                throw new ValidationException("Слишком большой id");
            }
        }
        return film;
    }
}
