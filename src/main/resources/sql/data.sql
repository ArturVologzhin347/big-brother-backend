INSERT INTO card (number)
VALUES ('1'), ('2');

INSERT INTO student (id, name, surname, patronymic, card_id)
VALUES ('i21s610', 'Ivan', 'Ivanov', 'Ivanovich', 1),
       ('i21s611', 'Petr', 'Sidorov', 'Vladimirovich', 2);

INSERT INTO respondent (name, surname, patronymic, phone_number)
VALUES ('Pavel', 'Ivanov', 'Pavlovich', '79501234567'),
       ('Vladimir', 'Sidorov', 'Pavlovich', '79501234568');

INSERT INTO respondent_config (respondent_id)
VALUES (1), (2);

INSERT INTO student_respondent (student_id, respondent_id)
VALUES ('i21s610', 1) , ('i21s611', 2);