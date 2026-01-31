package ru.sergalas.data.entities.periodicity.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.sergalas.data.web.data.ResponseData;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListPeriodicityData  implements ResponseData {
    List<PeriodicityResponseData> listPeriodicity;
}
