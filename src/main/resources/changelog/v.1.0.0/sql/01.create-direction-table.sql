CREATE TABLE IF NOT EXISTS direction
(
    id          serial              NOT NULL,
    name        varchar(255) UNIQUE NOT NULL,
    description varchar(255)        NOT NULL,
    ts_created  timestamp           NOT NULL,
    ts_updated  timestamp,
    PRIMARY KEY (id)
);
