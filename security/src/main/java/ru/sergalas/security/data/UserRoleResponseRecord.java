package ru.sergalas.security.data;

import ru.sergalas.security.web.data.ResponseData;

import java.util.List;

public record UserRoleResponseRecord(List<String> roles) implements ResponseData {
}
