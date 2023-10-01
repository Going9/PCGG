package com.ssafy.pcgg.domain.recommend.exception;

public class NoSuchPriorityException extends IllegalArgumentException {
    public NoSuchPriorityException() {}

    public NoSuchPriorityException(int priority) {super(String.valueOf(priority));}

    public NoSuchPriorityException(String message) {super(message);}

    public NoSuchPriorityException(String message, Throwable cause) {super(message, cause);}

    public NoSuchPriorityException(Throwable cause) {super(cause);}
}
