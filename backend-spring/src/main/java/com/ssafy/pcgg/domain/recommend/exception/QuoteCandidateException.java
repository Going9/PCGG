package com.ssafy.pcgg.domain.recommend.exception;

public class QuoteCandidateException extends RuntimeException{
    public QuoteCandidateException() {}

    public QuoteCandidateException(String message) {super(message);}

    public QuoteCandidateException(String message, Throwable cause) {super(message, cause);}

    public QuoteCandidateException(Throwable cause) {super(cause);}

//    public QuoteCandidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
