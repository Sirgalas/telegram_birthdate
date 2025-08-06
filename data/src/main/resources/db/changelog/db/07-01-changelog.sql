-- liquibase formatted sql

-- changeset Sergalas:1751898632549-5
ALTER TABLE periodicity ADD chat_id VARCHAR(255);

-- changeset Sergalas:1751898632549-1
ALTER TABLE participants DROP COLUMN date_periodicity_id;

-- changeset Sergalas:1751898632549-2
ALTER TABLE participants ADD date_periodicity_id UUID;
ALTER TABLE participants ADD CONSTRAINT fk_participants_date_periodicity FOREIGN KEY (date_periodicity_id) REFERENCES date_periodicity (id);

-- changeset Sergalas:1751898632549-3
ALTER TABLE periodicity DROP COLUMN date_periodicity_id;

-- changeset Sergalas:1751898632549-4
ALTER TABLE periodicity ADD date_periodicity_id UUID;
ALTER TABLE periodicity ADD CONSTRAINT fk_periodicity_date_periodicity FOREIGN KEY (date_periodicity_id) REFERENCES date_periodicity (id);

