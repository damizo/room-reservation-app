package com.cosmose.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by damian on 26.08.18.
 */

@Getter
@Setter
public class LogicValidationException extends RuntimeException {

    private String message;
    private Object[] param;

    public LogicValidationException(String message, Object... param) {
        this.message = message;
        this.param = param;
    }

}
