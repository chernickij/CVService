CREATE TABLE IF NOT EXISTS candidate
(
    id          serial       NOT NULL,
    name        varchar(255) NOT NULL,
    surname     varchar(255) NOT NULL,
    patronymic  varchar(255) NOT NULL,
    description varchar(255),
    ts_created  timestamp    NOT NULL,
    ts_updated  timestamp,
    PRIMARY KEY (id)
);
