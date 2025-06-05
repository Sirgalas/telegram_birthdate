package ru.sergalas.data.entities.periodicity.data;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodicityCreateRequestData {
    @Size(min = 1, max = 255, message = "periodicity.description.size")
    String title;
    @Size(message = "periodicity.description.size", min = 1, max = 1220)
    String description;
    @Size(min = 5, max = 5,message = "{date.size.error}")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])$")
    String date;
}
