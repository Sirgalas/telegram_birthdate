package ru.sergalas.data.entities.periodicity.service;

import ru.sergalas.data.entities.periodicity.data.ListPeriodicityData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;

import java.time.LocalDate;
import java.util.List;

public interface PeriodicityService {

    public PeriodicityResponseData create(PeriodicityRequestData data);

    public PeriodicityResponseData update(PeriodicityRequestData data, String id);
    public void delete(String id);
    public ListPeriodicityData getToDate(String date);
    public PeriodicityResponseData  getPeriodicity(String id);

    public List<Periodicity> getByDate(LocalDate date);
}
