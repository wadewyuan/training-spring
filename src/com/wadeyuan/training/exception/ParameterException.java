package com.wadeyuan.training.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {

    private static final long serialVersionUID = -8445735068544619787L;
    
    private Map<String, String> errorFields = new HashMap<String, String>();

    public Map<String, String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, String> errorFields) {
        this.errorFields = errorFields;
    }
    
    public void add(String field, String message) {
        errorFields.put(field, message);
    }
    
    public boolean hasErrorFields() {
        return !errorFields.isEmpty();
    }
}
