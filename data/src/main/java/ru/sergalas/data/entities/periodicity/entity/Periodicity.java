package ru.sergalas.data.entities.periodicity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;

import java.sql.Types;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "periodicity")
public class Periodicity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String title;
    @Column(length = 1220)
    String description;

    @ManyToOne
    @JoinColumn(
            name = "date_periodicity_id",
            foreignKey = @ForeignKey(
                    name = "fk_periodicity_date_periodicity",
                    foreignKeyDefinition = "FOREIGN KEY (date_periodicity_id) REFERENCES date_periodicity(id) ON DELETE SET NULL ON UPDATE RESTRICT"
            )
    )
    DatePeriodicity datePeriodicity;
}
