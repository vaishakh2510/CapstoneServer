package com.capstone.CapstoneServer.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse{

    private int errorCode;
    private String message;
    private LocalDateTime dateTime;

}
