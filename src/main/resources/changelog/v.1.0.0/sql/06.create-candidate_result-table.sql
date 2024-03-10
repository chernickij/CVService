CREATE TABLE IF NOT EXISTS candidate_result
(
    id           serial    NOT NULL,
    candidate_id integer   NOT NULL,
    test_id      integer   NOT NULL,
    date         timestamp NOT NULL,
    mark         integer   NOT NULL,
    ts_created   timestamp NOT NULL,
    ts_updated   timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (candidate_id) REFERENCES candidate (id) ON UPDATE CASCADE,
    FOREIGN KEY (test_id) REFERENCES test (id) ON UPDATE CASCADE
);
