package com.helmes.proovitoo.validation;

import java.util.List;

public class ValidationError {

    private String code;
    private List<String> arguments;

    public ValidationError(String code, List<String> args) {
        this.code = code;
        this.arguments = args;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public String getCode() {
        return code;
    }

    public List<String> getArguments() {
        return arguments;
    }
}