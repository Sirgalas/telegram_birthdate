package ru.sergalas.admin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.sergalas.admin.client.PeriodicityClient;
import ru.sergalas.admin.client.data.periodicity.create.CreateRequestData;
import ru.sergalas.admin.client.data.periodicity.create.CreateResponseData;
import ru.sergalas.admin.client.data.periodicity.update.UpdateRequestData;
import ru.sergalas.admin.client.data.periodicity.view.ListPeriodicityData;
import ru.sergalas.admin.entity.Periodicity;
import ru.sergalas.admin.mapper.PeriodicityMapper;
import ru.sergalas.admin.services.PeriodicityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodicityServiceImpl implements PeriodicityService {
    private final PeriodicityClient client;
    private final PeriodicityMapper mapper;

    @Override
    public List<Periodicity> getAllPeriodicity(String data) {
        return client
            .getPeriodicity(data)
            .listPeriodicity()
            .stream()
            .map(mapper::fromOnePeriodicityData)
            .toList();
    }

    @Override
    public Periodicity createPeriodicity(Periodicity periodicity) {
        CreateRequestData data = mapper.toCreateRequestData(periodicity);
        return mapper.fromCreateRequestData(client.createPeriodicity(data));
    }

    @Override
    public Periodicity updatePeriodicity(Periodicity periodicity) {
        UpdateRequestData data = mapper.toUpdateRequestData(periodicity);
        return mapper.fromUpdateRequestData(client.updatePeriodicity(data, periodicity.id().toString()));
    }

    @Override
    public List<Periodicity> deletePeriodicity(Periodicity periodicity) {
        client.deletePeriodicity(periodicity.id().toString());
        return client.getPeriodicity().listPeriodicity().stream().map(mapper::fromOnePeriodicityData).toList();
    }
}
