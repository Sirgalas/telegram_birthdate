package ru.sergalas.admin.client.data.periodicity.update;

import java.util.UUID;

public record UpdateRequestData(
        UUID id,
        String title,
        String description,
        String date
) {
}
