CREATE TABLE Customer (
    customerId INT NOT NULL primary key,
    firstName VARCHAR(50),
    lastName VARCHAR(20) NOT NULL,
    tsCreated TIMESTAMP NOT NULL,
    isExternallyManaged VARCHAR(1) NOT NULL
);


insert into Customer values (1, 'Rico', 'Apon', current_timestamp, '0');
insert into Customer values (2, null, 'Visser', current_timestamp, '1');
