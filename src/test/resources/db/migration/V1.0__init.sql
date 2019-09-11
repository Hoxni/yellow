CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS joggings
(
    id BIGSERIAL NOT NULL,
    user_id BIGSERIAL NOT NULL,
    distance BIGINT,
    duration BIGINT,
    created_at TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
