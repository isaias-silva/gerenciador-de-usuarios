package com.zack.api.util.responses.bodies;

import java.util.ArrayList;
import java.util.List;

public class ResponseErrorValidator extends Response {

    private final List<String> errors = new ArrayList<>();

    public ResponseErrorValidator(String message) {
        super(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addErrors(String error) {
        this.errors.add(error);
    }
}
