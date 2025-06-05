package ru.sergalas.data.entities.periodicity.data;

import jakarta.validation.constraints.Size;
import ru.sergalas.data.web.data.ResponseData;

public class PeriodicityResponseData implements ResponseData {
    String title;
    String description;
}
