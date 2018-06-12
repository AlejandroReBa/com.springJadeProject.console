package com.springJadeProject.console.model.exceptions;

public class ExternalCallBadRequestException extends RuntimeException{

    public ExternalCallBadRequestException(){
        super();
    }

    public ExternalCallBadRequestException(String msg){
        super(msg);
    }
}
