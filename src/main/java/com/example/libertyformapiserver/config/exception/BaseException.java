package com.example.libertyformapiserver.config.exception;

import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private BaseResponseStatus status;
}
