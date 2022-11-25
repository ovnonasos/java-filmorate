package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int id = 0;
    private final ArrayList<Film> films = new ArrayList<>();

    @GetMapping
    public ArrayList<Film> getFilms(){
        log.info("Получен запрос на получение всех фильмов");
        return films;
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film){
        log.info("Получен запрос на добавление фильма");
        if (films.size() < id){
            id = films.size();
        }
        id++;
        film.setId(id);
        film.validate();
        films.add(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        log.info("Получен запрос на обновление фильма");
        film.validate();
        for(Film film1 : films){
            if (film.getId() == film1.getId()){
                films.remove(film1);
                films.add(film);
                return film;
            } else if (film.getId() > id) {
                throw new ValidationException("Слишком большой id");
            }
        }
        if (films.size() < id){
            id = films.size();
        }
        id++;
        film.setId(id++);
        films.add(film);
        return film;
    }
}
