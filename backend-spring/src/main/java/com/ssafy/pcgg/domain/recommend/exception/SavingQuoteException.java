package com.ssafy.pcgg.domain.recommend.exception;

public class SavingQuoteException extends RuntimeException{
    public SavingQuoteException() {}

    public SavingQuoteException(String message) {super(message);}

    public SavingQuoteException(String message, Throwable cause) {super(message, cause);}

    public SavingQuoteException(Throwable cause) {super(cause);}
}
