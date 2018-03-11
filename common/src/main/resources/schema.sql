CREATE TABLE t_addressess (
  addressID     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  country       VARCHAR(255)     NOT NULL,
  city          VARCHAR(255)     NOT NULL,
  zipCode       INT             NOT NULL,
  street        VARCHAR(255)     NOT NULL,
  streetNumber  INT             NOT NULL,
  box           INT
);

# Do we need to add a description ? (Maybe because some status are weird)
CREATE TABLE t_civil_status (
  civilStatusID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  status        VARCHAR(255)    NOT NULL,
  description   VARCHAR(255)
);

#TODO : Check the nationalRgstrNr webService provided by the teacher
#TODO : Rethink some attributes (e.g.: phone, mail,
CREATE TABLE t_people (
  peopleID      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  forename      VARCHAR(255)     NOT NULL,
  lastname      VARCHAR(255)     NOT NULL,
  addressID     INT             NOT NULL,
  mail          VARCHAR(255)     NOT NULL,
  nationalRgstrNr VARCHAR(255)   NOT NULL UNIQUE,
  birthDate     TIMESTAMP       NOT NULL,
  activated     BOOLEAN DEFAULT FALSE
);


CREATE TABLE t_inhabitants (
  inhabitantID  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  peopleID      INT             NOT NULL,
  phone         VARCHAR(10)
);

#TODO change the value name (hireDate)
#TODO do we have to think about transgender and so one ?
CREATE TABLE t_empoyees (
  employeeID    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  peopleID      INT             NOT NULL,
  phone         VARCHAR(10)     NOT NULL,
  accountNumber INT             NOT NULL,
  hireDate      TIMESTAMP       NOT NULL,
  sex           ENUM('H','F')   NOT NULL,
  civilStatusID   INT             NOT NULL,
  dependentChildren INT         NOT NULL DEFAULT 0,
  dependentPeople   INT         NOT NULL DEFAULT 0
)