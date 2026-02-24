-- liquibase formatted sql

-- changeset Sergalas:1771907862222-1
ALTER TABLE participants
    ADD phone VARCHAR(255);

