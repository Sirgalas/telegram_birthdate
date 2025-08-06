package ru.sergalas.data.entities.participant.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String chatId;
    private String firstName;
    private String lastName;
    private String patronymic;

    @ManyToOne
    @JoinColumn(
        name = "date_periodicity_id",
        foreignKey = @ForeignKey(
            name = "fk_participants_date_periodicity",
            foreignKeyDefinition = "FOREIGN KEY (date_periodicity_id) REFERENCES date_periodicity(id) ON DELETE SET NULL ON UPDATE RESTRICT"
        )
    )
    DatePeriodicity datePeriodicity;
}
