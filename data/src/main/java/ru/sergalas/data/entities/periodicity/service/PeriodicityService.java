package ru.sergalas.data.entities.periodicity.service;

import ru.sergalas.data.entities.periodicity.data.ListPeriodicityData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityCreateRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;

import java.util.UUID;

public interface PeriodicityService {

    public PeriodicityResponseData create(PeriodicityCreateRequestData data);

    public void update(PeriodicityCreateRequestData data, String id);
    public void delete(String id);
    public ListPeriodicityData getToDate(String date);
}
