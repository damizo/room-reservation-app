package com.cosmose.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by damian on 26.08.18.
 */
@Getter
@Setter
@AllArgsConstructor
public class DomainEntityNotFoundException extends RuntimeException {

    private final String message;

}
