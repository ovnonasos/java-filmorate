package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NonNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    public Film(String name) {
    this.name = name;
    }


    public void validate(){
        if (getName().equals("")){
            throw new ValidationException("Неверное имя");
        } else if (description.length() > 200){
            throw new ValidationException("Слишком длинное описание");
        } else if (releaseDate.isBefore(LocalDate.parse("1895-12-28"))){
            throw new ValidationException("Слишком старый фильм");
        } else if (duration < 0){
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    public Film(@NonNull String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }


}
