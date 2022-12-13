package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmTest {

    @Test
    void shouldThrowExceptionWithEmptyName() {
        Film film = new Film("");
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        film.validate();
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWithBigDescription() {
        Film film = new Film("Terminator");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 201; i++) {
            s.append("a");
        }
        film.setDescription(s.toString());
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        film.validate();
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWithEarlierReleaseDate() {
        Film film = new Film("Terminator");
        film.setReleaseDate(LocalDate.parse("1895-12-27"));
        film.setDescription("a");
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        film.validate();
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWithNegativeDuration() {
        Film film = new Film("Terminator");
        film.setDuration(-1);
        film.setDescription("a");
        film.setReleaseDate(LocalDate.parse("1995-12-27"));
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        film.validate();
                    }
                }
        );
    }
}
