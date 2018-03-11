CREATE TABLE t_people (
  peopleID      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  firstname      VARCHAR(255)     NOT NULL,
  lastname      VARCHAR(255)     NOT NULL
);


CREATE TABLE t_inhabitants (
  inhabitantID  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  peopleID      INT             NOT NULL,
  activated     BOOLEAN DEFAULT FALSE
);

CREATE TABLE t_empoyees (
  employeeID    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  peopleID      INT             NOT NULL
)