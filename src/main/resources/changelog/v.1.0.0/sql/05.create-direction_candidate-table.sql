CREATE TABLE IF NOT EXISTS direction_candidate
(
    direction_id integer   NOT NULL,
    candidate_id integer   NOT NULL,
    ts_created   timestamp NOT NULL,
    ts_updated   timestamp,
    FOREIGN KEY (direction_id) REFERENCES direction (id) ON UPDATE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES candidate (id),
    PRIMARY KEY (direction_id, candidate_id)
);
