package com.wadeyuan.training.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -8235264801728891656L;
    private String message;

    public ServiceException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
