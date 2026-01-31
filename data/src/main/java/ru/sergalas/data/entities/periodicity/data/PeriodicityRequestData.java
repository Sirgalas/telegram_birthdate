package ru.sergalas.data.entities.periodicity.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodicityRequestData {

    UUID id;
    @NotBlank(message = "data.periodicity.not_blank")
    private String chatId;
    @NotBlank(message = "data.periodicity.not_blank")
    @Size(min = 1, max = 255, message = "periodicity.title.size")
    private String title;
    @NotBlank(message = "data.periodicity.not_blank")
    @Size(message = "periodicity.description.size", min = 1, max = 1220)
    private String description;
    @NotBlank(message = "data.periodicity.not_blank")
    @Size(min = 5, max = 5,message = "date.size.error")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])$")
    private String date;
}
