package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void shouldThrowExceptionWithEmptyEmail() {
        User user = new User("","BbQ",LocalDate.parse("2010-11-11"));
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        user.validate();
                    }
                }
        );
    }

    @Test
    public void shouldThrowExceptionWithoutAt() {
        User user = new User("dwadafaw","BbQ",LocalDate.parse("2010-11-11"));
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        user.validate();
                    }
                }
        );
    }

    @Test
    public void shouldThrowExceptionWithSpaceInLogin() {
        User user = new User("daw@daw","Bb Q",LocalDate.parse("2010-11-11"));
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        user.validate();
                    }
                }
        );
    }

    @Test
    public void shouldThrowExceptionWithDateAfterToday() {
        User user = new User("dwa@daw","BbQ",LocalDate.now().plusDays(1));
        final ValidationException ex = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute(){
                        user.validate();
                    }
                }
        );
    }
}