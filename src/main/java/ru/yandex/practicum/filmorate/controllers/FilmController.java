package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmService;

import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService){
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms(){
        log.info("Получен запрос на получение всех фильмов");
        return filmService.getInMemoryFilmStorage().getFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film){
        log.info("Получен запрос на добавление фильма");
        return filmService.getInMemoryFilmStorage().addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        log.info("Получен запрос на обновление фильма");
        return filmService.getInMemoryFilmStorage().updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id){
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void makeLike(@PathVariable int id,@PathVariable int userId){
        filmService.addLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id,@PathVariable int userId){
        filmService.removeLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "0") int count){
        if (count != 0) {
            return filmService.getTopFilms(count);
        } else {
            return filmService.getTop10Films();
        }
    }
}
