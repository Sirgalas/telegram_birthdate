package ru.sergalas.data.entities.periodicity.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.sergalas.data.entities.periodicity.entity.Periodicity}
 */
@Value
public class PeriodicityData {
    UUID id;
    @NotBlank
    String chatId;
    @Size(min = 1, max = 255, message = "periodicity.description.size")
    String title;
    @Size(message = "periodicity.description.size", min = 1, max = 1220)
    String description;
}