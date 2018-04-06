DROP TABLE IF EXISTS t_inhabitants;
CREATE TABLE t_inhabitants (
  inhabitantID  INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  address       INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255),
  nationalRegistreNb VARCHAR(255) NOT NULL,
  birthdate     DATE              NOT NULL,
  activated     BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS t_employees;
CREATE TABLE t_employees (
  employeeID    INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  address       INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255)      NOT NULL,
  nationalRegistreNb VARCHAR(255) NOT NULL,
  birthdate     DATE              NOT NULL,
  accountNumber VARCHAR(255)      NOT NULL,
  arrivalDate   DATETIME          NOT NULL,
  gender        CHAR(1)           NOT NULL,
  civilStatus   VARCHAR(255)      NOT NULL,
  dependentChildren INT           NOT NULL,
  dependentPeople   INT           NOT NULL
);

DROP TABLE IF EXISTS t_claim_types;
CREATE TABLE t_claim_types (
	claimTypeID INT PRIMARY KEY NOT NULL,
	description VARCHAR(255)
);

DROP TABLE IF EXISTS t_claims;
CREATE TABLE t_claims (
  claimID		INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  claimTypeID	INT 			NOT NULL,
  inhabitantID	INT				NOT NULL,
  employeeID	INT				NOT NULL,
  
  FOREIGN KEY(claimTypeID)
    REFERENCES t_claim_types(claimTypeID),
  FOREIGN KEY(inhabitantID)
    REFERENCES t_inhabitants(inhabitantID),
  FOREIGN KEY(employeeID)
    REFERENCES t_employees(employeeID)
);

/*
-- test data
insert into t_claim_types values(1,"Certificat de nationalité");
insert into t_people values(null,"Thomas","Elskens");
insert into t_people values(null,"Fabian","Germeau");
insert into t_inhabitants values (null, 1, 1);
insert into t_employees values(null,2);
insert into t_claims values(null,1,1,1);
*/
