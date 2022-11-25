package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;


    public void validate(){
        if (email.equals("") || !email.contains("@")){
            throw new ValidationException("Неверный имейл");
        } else if (login.equals("") || login.contains(" ")){
            throw new ValidationException("Неверный логин");
        } else if (birthday.isAfter(LocalDate.now())){
            throw new ValidationException("Неверная дата рождения");
        }
    }
}
