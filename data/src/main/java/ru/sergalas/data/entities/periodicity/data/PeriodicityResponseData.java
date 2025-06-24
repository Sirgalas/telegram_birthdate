package ru.sergalas.data.entities.periodicity.data;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sergalas.data.web.data.ResponseData;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodicityResponseData implements ResponseData {
    String title;
    String description;
}
