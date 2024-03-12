INSERT INTO direction(id, name, description, ts_created)
VALUES (1, 'Front-end', 'Front end development', current_timestamp);

INSERT INTO test(id, name, description, ts_created)
VALUES (1, 'Base test', 'Base test for all directions', current_timestamp);

INSERT INTO candidate(id, name, surname, patronymic, description, ts_created)
VALUES (1, 'Name', 'Surname', 'Patronymic', 'Test candidate', current_timestamp);

INSERT INTO direction_candidate(direction_id, candidate_id)
VALUES (1, 1);

INSERT INTO direction_test(direction_id, test_id)
VALUES (1, 1);
