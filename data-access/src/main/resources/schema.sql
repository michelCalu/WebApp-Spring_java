DROP TABLE IF EXISTS t_claims;
DROP TABLE IF EXISTS t_claim_types;
DROP TABLE IF EXISTS t_employees;
DROP TABLE IF EXISTS t_citizens;
DROP TABLE IF EXISTS t_addresses;



CREATE TABLE t_addresses (
  addressID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  country VARCHAR(255)    NOT NULL,
  state VARCHAR(255)      NOT NULL,
  zipCode INT             NOT NULL,
  street VARCHAR(255)     NOT NULL,
  streetNb INT            NOT NULL
);


CREATE TABLE t_citizens (
  citizenID  INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  addressID     INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255),
  nationalRegistreNb VARCHAR(255) NOT NULL,
  birthdate     VARCHAR(255)              NOT NULL,
  activated     BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (addressID) REFERENCES t_addresses(addressID)
);

CREATE TABLE t_employees (
  employeeID    INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  addressID     INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255)      NOT NULL,
  nationalRegistreNb VARCHAR(255) NOT NULL,
  birthdate     VARCHAR(255)              NOT NULL,
  accountNumber VARCHAR(255)      NOT NULL,
  arrivalDate   DATETIME          NOT NULL,
  gender        CHAR(1)           NOT NULL,
  civilStatus   VARCHAR(255)      NOT NULL,
  dependentChildren INT           NOT NULL,
  dependentPeople   INT           NOT NULL,
  FOREIGN KEY (addressID)  REFERENCES t_addresses(addressID)
);

CREATE TABLE t_claim_types (
	claimTypeID INT PRIMARY KEY NOT NULL,
	description VARCHAR(255)
);

CREATE TABLE t_claims (
  claimID		INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  claimTypeID	INT 			NOT NULL,
  citizenID	INT				NOT NULL,
  employeeID	INT				NOT NULL,
  
  FOREIGN KEY(claimTypeID)
    REFERENCES t_claim_types(claimTypeID),
  FOREIGN KEY(citizenID)
    REFERENCES t_citizens(citizenID),
  FOREIGN KEY(employeeID)
    REFERENCES t_employees(employeeID)
);

/*
-- test data
insert into t_claim_types values(1,"Certificat de nationalité");
insert into t_people values(null,"Thomas","Elskens");
insert into t_people values(null,"Fabian","Germeau");
insert into t_citizens values (null, 1, 1);
insert into t_employees values(null,2);
insert into t_claims values(null,1,1,1);
*/
