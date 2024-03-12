CREATE TABLE IF NOT EXISTS direction_test
(
    direction_id integer   NOT NULL,
    test_id      integer   NOT NULL,
    FOREIGN KEY (direction_id) REFERENCES direction (id) ON UPDATE CASCADE,
    FOREIGN KEY (test_id) REFERENCES test (id) ON UPDATE CASCADE,
    PRIMARY KEY (direction_id, test_id)
);
