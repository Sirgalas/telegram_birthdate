package ru.sergalas.data.entities.user.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sergalas.data.web.data.ResponseData;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseData implements ResponseData {
    private Long id;
    private String username;
    private String password;
}
