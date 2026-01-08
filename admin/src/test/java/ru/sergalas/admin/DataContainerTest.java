package ru.sergalas.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.sergalas.admin.client.data.participant.view.ParticipantsListRecord;

public class DataContainerTest {
    @Test
    public void testDeserialization() throws Exception {
        String json = """
        {
            "participants": [
                {
                    "id": "ae703b6e-4479-4d52-a0f5-041abb17edfd",
                    "chatId": "12",
                    "firstName": "Test",
                    "lastName": "Test",
                    "patronymic": "Test",
                    "date": "01.01"
                }
            ]
        }
        """;

        ObjectMapper mapper = new ObjectMapper();
        // Убедитесь, что у вас включена поддержка Records
        // mapper.registerModule(new JavaTimeModule()); // если есть Instant, LocalDate и т.п.
        // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // если не хотите @JsonIgnoreProperties

        ParticipantsListRecord container = mapper.readValue(json, ParticipantsListRecord.class);
        System.out.println(container);
    }
}
