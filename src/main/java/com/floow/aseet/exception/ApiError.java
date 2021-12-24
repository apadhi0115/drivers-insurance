package com.floow.aseet.exception;

import java.util.Map;

public class ApiError {
    
    private Map<String, String> errorMap;

    public Map<String, String> getErrorList() {
        return errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }
}




