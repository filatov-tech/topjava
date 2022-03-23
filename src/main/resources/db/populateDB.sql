DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (created, description, calories, user_id)
VALUES ('2022-03-16 13:00:00.000000', 'Pizza', 700, 100000),
       ('2022-03-16 14:00:00.000000', 'Pasta', 650, 100000),
       ('2022-03-16 15:00:00.000000', 'Apple', 150, 100000),
       ('2022-03-16 16:00:00.000000', 'Orange juice', 200, 100000),
       ('2022-03-16 11:00:00.000000', 'Pinza', 650, 100001),
       ('2022-03-16 12:00:00.000000', 'Coca-Cola', 300, 100001);
