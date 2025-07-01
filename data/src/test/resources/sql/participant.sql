-- Очищаем таблицы
DELETE FROM participants;
DELETE FROM date_periodicity;

-- Создаем тестовую периодичность
INSERT INTO date_periodicity (id, date)
VALUES ('10e8400-e29b-41d4-a716-446655440000', '25.12');

-- Создаем тестового участника
INSERT INTO participants (id, chat_id, first_name, last_name, patronymic, date_periodicity_id)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'chat123', 'John', 'Doe', 'Original', '10e8400-e29b-41d4-a716-446655440000');