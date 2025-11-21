package ru.sergalas.data.entities.periodicity.service;

import ru.sergalas.data.entities.periodicity.data.ListPeriodicityData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;

public interface PeriodicityService {

    public PeriodicityResponseData create(PeriodicityRequestData data);

    public PeriodicityResponseData update(PeriodicityRequestData data, String id);
    public void delete(String id);
    public ListPeriodicityData getToDate(String date);
    public PeriodicityResponseData  getPeriodicity(String id);
}
