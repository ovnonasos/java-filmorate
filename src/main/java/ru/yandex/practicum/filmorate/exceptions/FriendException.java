package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FriendException extends RuntimeException{

    public FriendException (final String message){
        super(message);
        log.warn("Ошибка при работе со списком друзей");
    }
}
