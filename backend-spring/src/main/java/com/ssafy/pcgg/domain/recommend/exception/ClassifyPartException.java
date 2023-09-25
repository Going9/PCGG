package com.ssafy.pcgg.domain.recommend.exception;

public class ClassifyPartException extends RuntimeException{
    public ClassifyPartException() {}

    public ClassifyPartException(String message) {
        super(message);
    }

    public ClassifyPartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassifyPartException(Throwable cause) {
        super(cause);
    }
}
