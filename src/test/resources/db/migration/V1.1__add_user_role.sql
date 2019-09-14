CREATE TYPE authorities AS ENUM
(
    'USER', 'ADMIN'
);

ALTER TABLE users ADD user_role authorities NOT NULL DEFAULT 'USER';