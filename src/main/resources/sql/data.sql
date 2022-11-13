INSERT INTO card (number)
VALUES ('1');

INSERT INTO student (id, name, surname, patronymic, card_id)
VALUES ('i21s610', 'Ivan', 'Ivanov', 'Ivanovich', 1);

INSERT INTO respondent (name, surname, patronymic, phone_number)
VALUES ('Pavel', 'Ivanovich', 'Pavlovich', '79501234567');

INSERT INTO respondent_config (respondent_id)
VALUES (1);

INSERT INTO students_respondents (student_id, respondent_id)
VALUES (1, 1);