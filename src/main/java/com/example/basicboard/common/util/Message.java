package com.example.basicboard.common.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Message {

    private int statusCode;
    private String message;
    private Object data;


    public Message(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{statusCode=" + statusCode + ", message='" + message + "', data='" + data + "'}";
    }

    public static Message setSuccess(StatusEnum statusEnum, String message, Object object) {
        return new Message(statusEnum.getStatus().value(), message, object);
    }

    public static Message setSuccess(StatusEnum statusEnum, String message) {
        return new Message(statusEnum.getStatus().value(), message);
    }


}
