CREATE TABLE IF NOT EXISTS tasks (id SERIAL NOT NULL PRIMARY KEY, user_id VARCHAR(255), current_duration_ms BIGINT, average_duration_ms BIGINT);
