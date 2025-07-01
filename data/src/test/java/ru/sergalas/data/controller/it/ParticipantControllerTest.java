package ru.sergalas.data.controller.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.sergalas.data.entities.participant.repository.ParticipantRepository;


import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private String nonExistentId;

    @BeforeEach
    void setUp() {
        baseUrl = "/participant";
        existingId = "550e8400-e29b-41d4-a716-446655440000";
        nonExistentId = "00000000-0000-0000-0000-000000000000";
    }

    @Test
    @Sql(scripts = "/sql/participant_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void createParticipant_ShowReturnCreated() throws Exception {
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content("""
                {
                  "chatId": "new-chat-id",
                  "firstName": "John",
                  "lastName": "Doe",
                  "patronymic": "Test",
                  "date": "01.01"
                }
                """))
        .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.firstName").value("John"));
        assertEquals(1, participantRepository.count());
    }

    @Test
    void updateParticipant() {
    }

    @Test
    void deleteParticipant() {
    }
}