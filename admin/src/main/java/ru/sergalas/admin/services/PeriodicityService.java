package ru.sergalas.admin.services;

import ru.sergalas.admin.client.data.periodicity.view.ListPeriodicityData;
import ru.sergalas.admin.entity.Periodicity;

import java.util.List;

public interface PeriodicityService {
    List<Periodicity> getPeriodicity(String data);
    Periodicity createPeriodicity(Periodicity periodicity);
    Periodicity updatePeriodicity(Periodicity periodicity);
    List<Periodicity> deletePeriodicity(Periodicity periodicity);
}
