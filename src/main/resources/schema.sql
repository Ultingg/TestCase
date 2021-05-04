CREATE DATABASE  NTCTest;
DROP TABLE IF EXISTS NTCTest.users;
CREATE TABLE NTCTest.users
(
    id               serial      not null,
    name             varchar(80) not null,
    phone_number     varchar(12),
    email            varchar UNIQUE,
    status           varchar(7),
    online_timestamp timestamp without time zone,
    primary key (id)
);

