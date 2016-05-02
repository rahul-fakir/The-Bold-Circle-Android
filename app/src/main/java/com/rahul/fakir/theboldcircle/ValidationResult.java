package com.rahul.fakir.theboldcircle;

/**
 * Created by Rahul on 2016/05/01.
 */
public class ValidationResult {
    private Boolean status;
    private String message;

    public ValidationResult(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus(){
        return status;
    }

    public String getMessage() {
        return message;
    }
}
