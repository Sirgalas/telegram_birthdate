-- Очищаем таблицы
DELETE FROM periodicity;
DELETE FROM date_periodicity;

-- Создаем тестовую периодичность
INSERT INTO date_periodicity (id, date)
VALUES ('10e88400-e29b-41d4-a716-446655440000', '25.12');

-- Создаем тестового участника
INSERT INTO periodicity (id, chat_id, title, description, date_periodicity_id)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'chat123', 'title', 'Description', '10e88400-e29b-41d4-a716-446655440000');