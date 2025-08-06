package ru.sergalas.data.entities.date.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sergalas.data.entities.participant.entity.Participant;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "date_periodicity")
public class DatePeriodicity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    private String date;

    @OneToMany(mappedBy = "datePeriodicity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<Participant> participants;

    @OneToMany(mappedBy = "datePeriodicity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    List<Periodicity> periodicities;

}
