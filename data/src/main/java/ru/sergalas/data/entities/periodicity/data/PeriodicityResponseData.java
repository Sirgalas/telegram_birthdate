package ru.sergalas.data.entities.periodicity.data;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sergalas.data.web.data.ResponseData;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodicityResponseData implements ResponseData {

    UUID id;
    String chatId;
    String title;
    String description;
    String date;
}
