create table customer
(
    id    SERIAL primary key NOT NULL,
    name  TEXT                  not null,
    email TEXT                  not null,
    age   INT                   not null
);
