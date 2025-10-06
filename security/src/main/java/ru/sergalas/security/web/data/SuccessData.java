package ru.sergalas.security.web.data;

import lombok.Data;

@Data
public class SuccessData implements ResponseData{
    private String success = "ok";
}
