package ru.sergalas.admin.services;

import ru.sergalas.admin.entity.Periodicity;

public interface PeriodicityService {
    Periodicity getPeriodicity(String data);
    Periodicity createPeriodicity(Periodicity periodicity);
    Periodicity updatePeriodicity(Periodicity periodicity);
    void deletePeriodicity(Periodicity periodicity);
}
