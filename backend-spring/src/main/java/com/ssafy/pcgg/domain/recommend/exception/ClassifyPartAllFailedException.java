package com.ssafy.pcgg.domain.recommend.exception;

public class ClassifyPartAllFailedException extends ClassifyPartException{
    public ClassifyPartAllFailedException() {
    }

    public ClassifyPartAllFailedException(String message) {
        super(message);
    }

    public ClassifyPartAllFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassifyPartAllFailedException(Throwable cause) {
        super(cause);
    }
}
