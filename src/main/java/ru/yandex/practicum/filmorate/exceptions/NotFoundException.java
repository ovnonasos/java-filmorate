package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends RuntimeException {

    public NotFoundException(){
        log.warn("Не найдено");
    }
}