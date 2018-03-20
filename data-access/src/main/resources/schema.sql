DROP TABLE IF EXISTS t_people;
CREATE TABLE t_people (
  peopleID      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  firstname      VARCHAR(255)     NOT NULL,
  lastname      VARCHAR(255)     NOT NULL
);


DROP TABLE IF EXISTS t_inhabitants;
CREATE TABLE t_inhabitants (
  inhabitantID  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  peopleID      INT             NOT NULL,
  activated     BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS t_employees;
CREATE TABLE t_employees (
  employeeID    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  peopleID      INT             NOT NULL
)