package com.zack.api.util.responses.bodies;

import java.util.ArrayList;
import java.util.List;

public class ResponseErrorValidator extends Response {

    private final List<String> erros = new ArrayList<>();

    public ResponseErrorValidator(String message) {
        super(message);
    }

    public List<String> getErros() {
        return erros;
    }

    public void addErros(String erro) {
        this.erros.add(erro);
    }
}
