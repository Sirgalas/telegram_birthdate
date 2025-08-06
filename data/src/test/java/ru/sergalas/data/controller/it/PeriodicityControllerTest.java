package ru.sergalas.data.controller.it;

import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;
import ru.sergalas.data.entities.periodicity.repository.PeriodicityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class PeriodicityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PeriodicityRepository periodicityRepository;


    @Autowired
    private DataSource dataSource;

    private String baseUrl;
    private String existingId;
    private String notFoundExistentId;



    @BeforeEach
    void setUp() {
        this.baseUrl = "/periodicity";
        this.existingId = "550e8400-e29b-41d4-a716-446655440000";
        this.notFoundExistentId = UUID.randomUUID().toString();
    }

    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void getByDate_returnPeriodicity_statusOk() throws Exception {
        mockMvc.perform(
            get(baseUrl)
            .param("date", "25.12")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.periodicities[0].title").value("title"));
    }
    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void getByDate_notFound_statusOk() throws Exception {
        mockMvc.perform(
            get(baseUrl)
            .param("date", "26.12")
        )
        .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void create_createPeriodicity_statusOk() throws Exception {
        mockMvc.perform(
                post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "chatId": "new-chat-id",
                    "title": "title",
                    "description": "description",
                    "date": "20.12"
                }
                """)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.title").value("title"))
                .andExpect(jsonPath("$.data.description").value("description"));
    }

    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void create_notValid_statusOk() throws Exception {
        mockMvc.perform(
                post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "chatId": null,
                    "title": 1
                }
                """)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.date[0]").value("Поле не должно быть пустым"))
                .andExpect(jsonPath("$.errors.chatId[0]").value("Поле не должно быть пустым"))
                .andExpect(jsonPath("$.errors.description[0]").value("Поле не должно быть пустым"));
    }

    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void update_filedValid_returnNoContent() throws Exception {

            mockMvc.perform(
                    put(baseUrl +"/" + existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                      {
                        "chatId": "update-chat-id",
                        "title": "title-update",
                        "description": "description-update",
                        "date": "26.12"
                      }
                    """)
            )
            .andExpect(status().isNoContent());
        Periodicity updated = periodicityRepository.findById(UUID.fromString(existingId)).orElseThrow();
        assertEquals("title-update", updated.getTitle());
        assertEquals("description-update", updated.getDescription());
        assertEquals("update-chat-id", updated.getChatId());
        assertEquals("26.12",updated.getDatePeriodicity().getDate());
    }


    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void update_filedNotValid_returnNoContent() throws Exception {
        mockMvc.perform(
                    put(baseUrl +"/" + existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                      {
                        "chatId": 1,
                        "title": "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem.",
                        "description": null
                      }
                    """)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors.date[0]").value("Поле не должно быть пустым"))
            .andExpect(jsonPath("$.errors.description[0]").value("Поле не должно быть пустым"))
            .andExpect(jsonPath("$.errors.title[0]").value("Поле заголовок должно содержать от 1 до 255 символов"));
    }


    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void update_notFound() throws Exception {
        mockMvc.perform(
            put(baseUrl +"/" + UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
               {
                "chatId": "update-chat-id",
                "title": "title-update",
                "description": "description-update",
                "date": "26.12"
              }
            """)
        )
        .andExpect(status().isNotFound());
}

    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void delete_delete_returnNoContent() throws Exception {
        mockMvc.perform(delete(baseUrl + "/" + existingId)).andExpect(status().isNoContent());
        assertEquals(0, periodicityRepository.count());
    }
    @Test
    @Sql(scripts = "/sql/periodicity.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/periodicity_clean.sql", executionPhase = AFTER_TEST_METHOD)
    void delete_isNotFound() throws Exception {
        mockMvc.perform(delete(baseUrl + "/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

    }
}