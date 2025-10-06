package ru.sergalas.security.web.data;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.io.Serializable;

public class ErrorData implements Serializable{

    public String field;
    public String objectName;
    public String defaultMessage;

    public ErrorData(ObjectError objectError){
        DefaultMessageSourceResolvable messageSourceResolvable =
                (objectError.getArguments() == null || objectError.getArguments().length == 0)
                        ? new DefaultMessageSourceResolvable("UNKNOWN")
                        : (DefaultMessageSourceResolvable) objectError.getArguments()[0];
        field = messageSourceResolvable.getCode();
        defaultMessage = objectError.getDefaultMessage();
        objectName = objectError.getObjectName();
    }

    public ErrorData(String field, String objectName, String defaultMessage){
        this.field = field;
        this.objectName = objectName;
        this.defaultMessage = defaultMessage;
    }

}
