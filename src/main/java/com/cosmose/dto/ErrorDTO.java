package com.cosmose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by damian on 26.08.18.
 */
@Data
@AllArgsConstructor
public class ErrorDTO {

    private String message;

}
