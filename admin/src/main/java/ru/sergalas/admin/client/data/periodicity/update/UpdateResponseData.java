package ru.sergalas.admin.client.data.periodicity.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateResponseData(
        @NotBlank(message = "data.periodicity.not_blank")
        UUID id,
        @NotBlank(message = "data.periodicity.not_blank")
        @Size(min = 1, max = 255, message = "periodicity.title.size")
        String title,
        @NotBlank(message = "data.periodicity.not_blank")
        @Size(message = "periodicity.description.size", min = 1, max = 1220)
        String description,
        @NotBlank(message = "data.periodicity.not_blank")
        @Size(min = 5, max = 5,message = "date.size.error")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])$")
        String date
) {
}
