INSERT INTO card (number)
VALUES ('1'),
       ('2'),
       ('3');

INSERT INTO student (id, name, surname, patronymic, card_id)
VALUES ('i21s600', 'Ivan', 'Ivanov', 'Ivanovich', 1),
       ('i21s601', 'Petya', 'Sidorov', 'Bogdanovich', 2),
       ('i21s602', 'Vova', 'Kakoy-to', 'Chel', 3);

INSERT INTO respondent (name, surname, patronymic, phone_number, student_id)
VALUES ('Ivan', 'Ivanov', 'IvanIvanovich', '+7(846)311-47-78', 'i21s600'),
       ('Bogdan', 'Sidorov', 'BogdanBogdanovich', '+7(4732)72-25-89', 'i21s601'),
       ('Chel', 'Kakoy-to', 'Vladimirovich', '7(39550)3-08-70 ', 'i21s602');


