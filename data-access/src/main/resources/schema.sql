DROP TABLE IF EXISTS t_departments_skills;
DROP TABLE IF EXISTS t_skills;
DROP TABLE IF EXISTS t_departments_employees;
DROP TABLE IF EXISTS t_events;
DROP TABLE IF EXISTS t_event_types;
DROP TABLE IF EXISTS t_request_field_values;
DROP TABLE IF EXISTS t_request_field_definitions;
DROP TABLE IF EXISTS t_requests;
DROP TABLE IF EXISTS t_departments;
DROP TABLE IF EXISTS t_req_statusses;
DROP TABLE IF EXISTS t_request_types;
DROP TABLE IF EXISTS t_mandataries;
DROP TABLE IF EXISTS t_mandatary_roles;
DROP TABLE IF EXISTS t_companies;
DROP TABLE IF EXISTS t_employees;
DROP TABLE IF EXISTS t_citizen_accounts;
DROP TABLE IF EXISTS t_citizens;
DROP TABLE IF EXISTS t_municipalities;
DROP TABLE IF EXISTS t_user_statusses;
DROP TABLE IF EXISTS t_user_accounts;
DROP TABLE IF EXISTS t_addresses;
DROP TABLE IF EXISTS t_documents;

CREATE TABLE t_user_accounts (
  userAccountID			INT PRIMARY KEY 		NOT NULL AUTO_INCREMENT,
  roles		    		ENUM('ROLE_USER','ROLE_ADMIN') 			NOT NULL,
  password				VARCHAR(255) 			NOT NULL,
  userStatus
  	ENUM('created','active','disabled')			NOT NULL 
);
  


CREATE TABLE t_addresses (
  addressID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  street VARCHAR(255)     NOT NULL,
  streetNb INT            NOT NULL,
  nbSuffix VARCHAR(4)     ,
  zipCode INT             NOT NULL,
  municipality VARCHAR (255) NOT NULL,
  state VARCHAR(255)      ,
  country VARCHAR(255)    NOT NULL
);

CREATE TABLE t_municipalities (
  municipalityID  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name    VARCHAR(255)  NOT NULL UNIQUE,
  address   INT       NOT NULL UNIQUE, -- no multiple municipalities at same address
  email   VARCHAR(255)  NOT NULL,
  phone   VARCHAR(255)  NOT NULL,
  mayorName VARCHAR(255) NOT NULL,

  FOREIGN KEY  (address) REFERENCES t_addresses(addressID)

);

CREATE TABLE t_citizens (
  citizenID  INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  addressID     INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255),
  nationalRegisterNb VARCHAR(255) NOT NULL UNIQUE ,
  birthdate     DATE,
  userAccountID	INT,
  FOREIGN KEY (addressID) REFERENCES t_addresses(addressID),
  FOREIGN KEY (userAccountID) REFERENCES t_user_accounts(userAccountID)
);


CREATE TABLE t_employees (
  employeeID    INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  addressID     INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255)      NOT NULL,
  nationalRegisterNb VARCHAR(255) NOT NULL UNIQUE,
  birthdate     DATE              NOT NULL,
  accountNumber VARCHAR(255)      NOT NULL,
  arrivalDate   DATE              NOT NULL,
  gender        CHAR(1)           NOT NULL,
  civilStatus   VARCHAR(255)      NOT NULL,
  dependentChildren INT           NOT NULL,
  dependentPeople   INT           NOT NULL,
  userAccountID	INT,
  FOREIGN KEY (addressID)  REFERENCES t_addresses(addressID),
  FOREIGN KEY (userAccountID) REFERENCES t_user_accounts(userAccountID)
);

CREATE TABLE t_companies (
  companyNb   	VARCHAR(255)  	PRIMARY KEY NOT NULL,
  vatNb     	VARCHAR(255),
  address     	INT       		NOT NULL,
  legalForm  	VARCHAR(255)  	NOT NULL,
  contactPerson INT	NOT NULL,
  companyName   VARCHAR(255)    NOT NULL ,

  FOREIGN KEY (contactPerson) REFERENCES t_citizens(citizenID)
);

CREATE TABLE t_mandataries(
  mandataryID   INT PRIMARY KEY 	NOT NULL AUTO_INCREMENT,
  citizenID   	INT  				NOT NULL,
  companyNb   	VARCHAR(255)  		NOT NULL,
  role      	
  	ENUM('owner','manager','reader')       	
  									NOT NULL,

  FOREIGN KEY (citizenID) REFERENCES t_citizens(citizenID),
  FOREIGN KEY (companyNb) REFERENCES t_companies(companyNb),
  UNIQUE (citizenID, companyNb, role)
);

CREATE TABLE t_request_types (
	requestTypeID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	description VARCHAR(255),
	CONSTRAINT UC_Description UNIQUE (description)
);

CREATE TABLE t_req_statusses (
  statusID    INT   PRIMARY KEY NOT NULL AUTO_INCREMENT,
  statusName    VARCHAR(255),
  CONSTRAINT UC_statusName UNIQUE (statusName)
);

CREATE TABLE t_departments (
  departmentID        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  municipalityID      INT NOT NULL,
  name                VARCHAR(255)  NOT NULL,
  headOfDepartmentID  INT NOT NULL,
  parentDepartmentID  INT,
  addressID			  INT, -- if null, same as municipality address

  FOREIGN KEY(headOfDepartmentID)
    REFERENCES t_employees(employeeID),
  FOREIGN KEY(parentDepartmentID)
    REFERENCES t_departments(departmentID),
  FOREIGN KEY(municipalityID)
    REFERENCES t_municipalities (municipalityID),
  FOREIGN KEY(addressID)
    REFERENCES t_addresses(addressID)
);

CREATE TABLE t_requests (
  requestID		INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  requestTypeID	INT 			NOT NULL,
  citizenID		INT				NOT NULL,
  companyNb   VARCHAR(255),
  employeeID	INT,
  departmentID INT      NOT NULL,
  statusID    INT       NOT NULL,
  /* If lastChangeBy refer an employee it could be null at the creation of the request
  and should be in the event
  lastChangeBy  INT,*/
  systemRef   VARCHAR(255)  NOT NULL,
  userRef   VARCHAR(255)  ,
  municipalityRef VARCHAR(255)  NOT NULL,

  FOREIGN KEY(requestTypeID)
    REFERENCES t_request_types(requestTypeID),
  FOREIGN KEY(citizenID)
    REFERENCES t_citizens(citizenID),
  FOREIGN KEY (companyNb)
    REFERENCES t_companies(companyNb),
  FOREIGN KEY(employeeID)
    REFERENCES t_employees(employeeID),
  FOREIGN KEY(departmentID)
    REFERENCES t_departments(departmentID),
  FOREIGN KEY(statusID)
   REFERENCES t_req_statusses(statusID)
);

CREATE TABLE t_request_field_definitions (
  fieldCode 			VARCHAR(255)  PRIMARY KEY NOT NULL,
  requestTypeID			INT NOT NULL,  
  fieldType				VARCHAR(255) NOT NULL,
  required				BOOLEAN NOT NULL,
  
  FOREIGN KEY(requestTypeID)
    REFERENCES t_request_types(requestTypeID)
);

CREATE TABLE t_request_field_values (
  requestFieldValueID 	INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  fieldCode 			VARCHAR(255) NOT NULL,
  requestID				INT NOT NULL,
  -- fieldValue XOR fieldFile: either one or the other is present (both nullable, therefore)
  fieldValue			VARCHAR(255),
  fieldFile				LONGBLOB,
  
  FOREIGN KEY(fieldCode)
    REFERENCES t_request_field_definitions(fieldCode),
  FOREIGN KEY(requestID)
    REFERENCES t_requests(requestID)
);

CREATE TABLE t_event_types (
  eventTypeID   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  eventDesc VARCHAR(255)  NOT NULL UNIQUE
);

CREATE TABLE t_events (
  eventID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  eventType    INT       NOT NULL,
  at      DATETIME      NOT NULL,
  author    INT       NOT NULL,
  request   INT       NOT NULL,

  FOREIGN KEY (eventType) REFERENCES t_event_types(eventTypeID),
  FOREIGN KEY (author) REFERENCES t_employees(employeeID),
  FOREIGN KEY (request) REFERENCES t_requests(requestID)
);

CREATE TABLE t_departments_employees (
  departmentID  INT NOT NULL,
  employeeID    INT NOT NULL,

  PRIMARY KEY (departmentID,employeeID),
  FOREIGN KEY (departmentID) REFERENCES t_departments(departmentID),
  FOREIGN KEY (employeeID) REFERENCES t_employees(employeeID)
);

CREATE TABLE t_skills (
  skillID       VARCHAR(255) PRIMARY KEY NOT NULL,
  description   VARCHAR(255) UNIQUE
);

CREATE TABLE t_departments_skills (
  departmentID  INT NOT NULL,
  skillID       VARCHAR(255) NOT NULL,

  PRIMARY KEY (departmentID, skillID),
  FOREIGN KEY (departmentID) REFERENCES t_departments(departmentID),
  FOREIGN KEY (skillID) REFERENCES t_skills(skillID)
);

CREATE TABLE t_documents (
  documentID 	INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  requestID 	INT NOT NULL,
  contents 		TEXT NOT NULL 
);
