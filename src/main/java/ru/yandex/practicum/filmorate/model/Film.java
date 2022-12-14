package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Film {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private LocalDate releaseDate;
    private int duration;
    private final LocalDate DATE = LocalDate.parse("1895-12-28");
    private int likes;
    @JsonIgnore
    private List<User> likers = new ArrayList<>();


    public Film(String name) {
    this.name = name;
    }


    public void validate(){
        if (getName().equals("")){
            throw new ValidationException("Неверное имя");
        } else if (description.length() > 200){
            throw new ValidationException("Слишком длинное описание");
        } else if (releaseDate.isBefore(DATE)){
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
