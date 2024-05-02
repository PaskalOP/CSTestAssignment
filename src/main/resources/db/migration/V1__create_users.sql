CREATE TABLE users (
    user_id BIGSERIAL  PRIMARY KEY,
    email VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    birthday DATE NOT NULL,
    address VARCHAR,
    phone VARCHAR
);
