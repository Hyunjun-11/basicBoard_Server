package com.example.basicboard.global.message;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Message<T> {

    private int statusCode;
    private String message;
    private T data;


    public Message(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{statusCode=" + statusCode + ", message='" + message + "', data='" + data + "'}";
    }

    public static <T> Message<T> setSuccess(StatusEnum statusEnum, String message, T data) {
        return new Message<>(statusEnum.getStatus().value(), message, data);
    }

    public static <T> Message<T> setSuccess(StatusEnum statusEnum, String message) {
        return new Message<>(statusEnum.getStatus().value(), message);
    }


}
