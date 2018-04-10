DROP TABLE IF EXISTS t_requests;
DROP TABLE IF EXISTS t_request_types;
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
  nationalRegisterNb VARCHAR(255) NOT NULL,
  birthdate     VARCHAR(255),
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
  nationalRegisterNb VARCHAR(255) NOT NULL,
  birthdate     VARCHAR(255)      NOT NULL,
  accountNumber VARCHAR(255)      NOT NULL,
  arrivalDate   DATETIME          NOT NULL,
  gender        CHAR(1)           NOT NULL,
  civilStatus   VARCHAR(255)      NOT NULL,
  dependentChildren INT           NOT NULL,
  dependentPeople   INT           NOT NULL,
  FOREIGN KEY (addressID)  REFERENCES t_addresses(addressID)
);

CREATE TABLE t_request_types (
	requestTypeID INT PRIMARY KEY NOT NULL,
	description VARCHAR(255)
);

CREATE TABLE t_requests (
  requestID		INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  requestTypeID	INT 			NOT NULL,
  citizenID		INT				NOT NULL,
  employeeID	INT						,
  status 		INT				NOT NULL,
  
  FOREIGN KEY(requestTypeID)
    REFERENCES t_request_types(requestTypeID),
  FOREIGN KEY(citizenID)
    REFERENCES t_citizens(citizenID),
  FOREIGN KEY(employeeID)
    REFERENCES t_employees(employeeID)
);
