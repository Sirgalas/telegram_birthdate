package ru.sergalas.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.sergalas.bot.bot.enums.BindingStatus;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "telegram_bindings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramBinding {
    @Id
    private UUID id;

    @Indexed(unique = true, sparse = true)
    @Field("keycloak_sub")
    private String keycloakSub;
    @Indexed(unique = true)
    @Field("telegram_chat_id")
    private Long telegramChatId;
    @Field("telegram_username")
    private String telegramUsername;
    @Indexed(unique = true, sparse = true)
    @Field("linking_code")
    private String linkingCode;
    @Field("code_expires_at")
    private Instant codeExpiresAt;
    @Field("linked_at")
    private Instant linkedAt;
    @Field("status")
    private BindingStatus status;
    @Field("created_at")
    private Instant createdAt;

    public Boolean isCodeExpired() {
        return codeExpiresAt != null && Instant.now().isAfter(codeExpiresAt);
    }

    public Boolean canBeLinked() {
        return status.equals(BindingStatus.PENDING) && ! isCodeExpired();
    }




    public TelegramBinding(Long telegramChatId, String telegramUsername, String linkingCode, Instant codeExpiresAt) {
        this.id = UUID.randomUUID();
        this.telegramChatId = telegramChatId;
        this.telegramUsername = telegramUsername;
        this.linkingCode = linkingCode;
        this.codeExpiresAt = codeExpiresAt;
        this.status = BindingStatus.PENDING;
        createdAt = Instant.now();
    }
}
