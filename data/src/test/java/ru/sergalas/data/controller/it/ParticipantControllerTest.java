package ru.sergalas.data.controller.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.sergalas.data.entities.participant.entity.Participant;
import ru.sergalas.data.entities.participant.repository.ParticipantRepository;


import javax.sql.DataSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ParticipantControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private DataSource dataSource;

    private String baseUrl;
    private String existingId;
    private String notFoundExistentId;

    @BeforeEach
    void setUp() {
        baseUrl = "/participant";
        existingId = "550e8400-e29b-41d4-a716-446655440000";
        notFoundExistentId = UUID.randomUUID().toString();
    }

    @Test
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void createParticipant_ShowReturnCreated() throws Exception {
        mockMvc.perform(
                        post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                          "chatId": "new-chat-id",
                                          "firstName": "John",
                                          "lastName": "Doe",
                                          "patronymic": "Test",
                                          "date": "01.01"
                                        }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.firstName").value("John"));
        assertEquals(1, participantRepository.count());
    }

    @Test
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void createParticipant_NotValid_ReturnBadRequest() throws Exception {
        mockMvc.perform(
            post(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "chatId": 1,
                  "firstName": null,
                  "lastName": "",
                  "date": "01.01.2025"
                }
                """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.date").isArray())
        .andExpect(jsonPath("$.errors.lastName[0]").value("Фамилия не должно содержать менее 1 и более 255 символов"))
        .andExpect(jsonPath("$.errors.firstName[0]").value("Поле не должно быть пустым")); // Для chatId
    }

    @Test
    @Sql(scripts = "/sql/participant.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void updateParticipant_ShouldUpdateAndReturnNoContent() throws Exception {
        mockMvc
            .perform(put(baseUrl + "/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "chatId": "chat123",
                            "firstName": "Updated",
                            "lastName": "Name",
                            "patronymic": "Updated",
                            "date": "25.12"
                        }
                    """)
            ).andExpect(status().isNoContent());
        Participant updated = participantRepository.findById(UUID.fromString(existingId)).orElseThrow();
        assertEquals("Updated", updated.getFirstName());
    }

    @Test
    @Sql(scripts = "/sql/participant.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void updateParticipant_IdIsEmpty_ReturnNotFound() throws Exception {
        mockMvc
                .perform(
                        put(baseUrl + "/" + notFoundExistentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                            {
                                                "chatId": "chat123",
                                                "firstName": "Updated",
                                                "lastName": "Name",
                                                "patronymic": "Updated",
                                                "date": "25.12"
                                            }
                                        """)
                ).andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/participant.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void updateParticipant_NotValid_ReturnBadRequest() throws Exception {
        String invalidDate = "31.13";
        String tooLongName = "a".repeat(256);
        String emptyString = "";

        mockMvc.perform(
            put(baseUrl + "/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                        """
                                        {
                                            "chatId": "%s",
                                            "firstName": "%s",
                                            "lastName": "%s",
                                            "patronymic": "%s",
                                            "date": "%s"
                                        }
                                """,
                        emptyString,  // Невалидный chatId (пустая строка)
                        emptyString,  // Невалидный firstName (пустая строка)
                        tooLongName,  // Невалидный lastName (слишком длинный)
                        tooLongName,  // Невалидный patronymic (слишком длинный)
                        invalidDate   // Невалидная дата
                    )
                )
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.date[0]").value("Неверный формат даты. Ожидается формат dd.MM"))
            .andExpect(jsonPath("$.errors.lastName[0]").value("Фамилия не должно содержать менее 1 и более 255 символов"))
            .andExpect(jsonPath("$.errors.firstName").isArray())
            .andExpect(jsonPath("$.errors.chatId[0]").value("Поле не должно быть пустым"))
            .andExpect(jsonPath("$.errors.patronymic[0]").value("Отчество не должно содержать менее 1 и более 255 символов"));
    }

    @Test
    @Sql(scripts = "/sql/participant.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void getParticipant_ParticipantHave_ReturnOk() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("date", "25.12"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.data.participants[0].firstName")
                                .value("John"));
    }

    @Test
    @Sql(scripts = "/sql/participant.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void deleteParticipant_IsDeleted_ReturnNoContent() throws Exception {
        mockMvc.perform(delete(baseUrl + "/" + existingId)).andExpect(status().isNoContent());
        assertEquals(0, participantRepository.count());
    }
    @Test
    @Sql(scripts = "/sql/participant.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void deleteParticipant_IsDeleted_NotFound() throws Exception {
        mockMvc.perform(delete(baseUrl + "/%s".formatted(notFoundExistentId)))
                .andExpect(status().isNotFound());
    }
}