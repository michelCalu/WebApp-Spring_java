-- bootstrap data
insert into t_request_types values(1,"nationalityCertificate");
insert into t_request_types values(2,"citizenParkingCard");
insert into t_request_types values(3,"companyParkingCard");

insert into t_request_field_definitions values("nationalityCertificateReference", 1, "string", false);

insert into t_request_field_definitions values("citizenParkingCardCarMake", 2, "string", true);
insert into t_request_field_definitions values("citizenParkingCardCarModel", 2, "string", true);
insert into t_request_field_definitions values("citizenParkingCardCarColour", 2, "string", true);
insert into t_request_field_definitions values("citizenParkingCardPlateNumber", 2, "string", true);
insert into t_request_field_definitions values("citizenParkingCardGreenCard", 2, "binary", true);
insert into t_request_field_definitions values("citizenParkingCardUserProof", 2, "binary", false);

insert into t_request_field_definitions values("companyParkingCardContactPersonEmail", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardUserFirstName", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardUserLastName", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardCarMake", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardCarModel", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardCarColour", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardPlateNumber", 3, "string", true);
insert into t_request_field_definitions values("companyParkingCardGreenCard", 3, "binary", true);
insert into t_request_field_definitions values("companyParkingCardUserProof", 3, "binary", false);

insert into t_req_statusses values(1,"new");
insert into t_req_statusses values(2,"ongoing");
insert into t_req_statusses values(3,"awaitingInfo");
insert into t_req_statusses values(4,"rejected");
insert into t_req_statusses values(5,"accepted");


insert into t_event_types values(null,"created");
insert into t_event_types values(null,"updated");
insert into t_event_types values(null,"closed");
insert into t_event_types values(null,"assigneeChange");


insert into t_document_titles values (1, "citizenParkingCard");
insert into t_document_titles values (2, "citizenParkingPayment");
insert into t_document_titles values (3, "citizenParkingDecision");
insert into t_document_titles values (4, "nationalityCertificate");

-- test data
-- -- user accounts
-- 5 citizens (password root)
insert into t_user_accounts values(1,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- thomas_citizen
insert into t_user_accounts values(2,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- killian_citizen
insert into t_user_accounts values(3,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- fabian_citizen
insert into t_user_accounts values(4,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- michel_citizen
insert into t_user_accounts values(5,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- david_citizen
-- 6 employees (password Hermes)
insert into t_user_accounts values(6,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- thomas_empl
insert into t_user_accounts values(7,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- killian_empl
insert into t_user_accounts values(8,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- fabian_empl
insert into t_user_accounts values(9,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- michel_empl
insert into t_user_accounts values(10,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- david_empl
insert into t_user_accounts values(11,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- suppl empl
insert into t_user_accounts values(13,"ROLE_OFFICER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- fabian employé
-- 1 admin (password admin)
insert into t_user_accounts values(12,"ROLE_ADMIN","$2a$10$4w9Wm8gc93DpDQdyZ7SR5O.17XQMsfElDZRVbKuzaQJVi.0s.5xn.","active"); -- admin




-- Addresses municipalities
insert into t_addresses values(1,"Avenue grandgagnage", 21,"A",5000,"Namur", "Province de Namur", "Belgique");
insert into t_addresses values(2,"Avenue de Bastogne", 1,null,6600,"Bastogne", "Province de liege", "Belgique");
insert into t_addresses values(3,"Avenue des abeilles", 12, "A",6044,"Roux", "Hainaut", "Belgique");
insert into t_addresses values(4,"Rue des combattants", 59, null, 1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(5,"Rue de Liege", 44, null, 4000,"Liege", "Province de Liege", "Belgique");
insert into t_addresses values(6,"Rue de Charleroi", 2, null, 6000,"Charleroi", "Province de Liege", "Belgique");

-- Addresses citizens
insert into t_addresses values(7,"une rue à namur",         1,"A",      5000,"Namur",     "Wallonie",                "Belgique");
insert into t_addresses values(8,"une rue à Bastogne",      2,null,     6600,"Bastogne",  "Wallonie",                "Belgique");
insert into t_addresses values(9,"une rue à Roux",             3, "A",     6044,"Roux",         "Wallonie",                "Belgique");
insert into t_addresses values(10,"une rue à La Hulpe",  4, null,    1310,"La Hulpe",  "Brabant Wallon",       "Belgique");
insert into t_addresses values(11,"une rue à Liege",        5, null,    4000,"Liege",     "Province de Liege",    "Belgique");
insert into t_addresses values(12,"une rue à Charleroi",    6, null,    6000,"Charleroi", "Province de Liege",    "Belgique");

-- -- Municipalities
insert into t_municipalities values(1, "Namur",       1, "info@namur.be", "082/654.12.20", "Maxime Prévot");
insert into t_municipalities values(2, "Bastogne",    2, "bastogne@bastogne.be", "061/465.12.77", "Robert Genard");
insert into t_municipalities values(3, "Roux",        3, "roux@municipality.com", "071/12.34.56", "Pierre Paul-Jacques");
insert into t_municipalities values(4, "La Hulpe",    4, "lh@municipality.com", "02/653.16.37", "Gonzague Pull-Rose");
insert into t_municipalities values(5, "Liege",       5, "liege@municipality.com", "04/239.35.62", "Stéphane Moreau");
insert into t_municipalities values(6, "Charleroi", 6, "charleroi@municipality.com", "071/23.35.62", "Paul Magnette");

-- -- Citizens
insert into t_citizens values(1,"Thomas","Elskens",      1,"thomaselskens@hotmail.com",null, "111",      "1983-11-13",        1);
insert into t_citizens values(2,"Killian","Degryse",  2,"killian@unamur.be",null,               "222",      "1976-12-16",        2);
insert into t_citizens values(3,"Fabian","Germeau",      3,"germeau.fabian@gmail.com",null,  "333",      "1991-07-02",        3);
insert into t_citizens values(4,"Michel","Calu",         4,"michel@unamur.be",null,             "76120330920","1976-12-16",   4);
insert into t_citizens values(5,"David","Fernandez",  5,"dfernandez1612@gmail.com",null,  "555","1983-12-16",              5);



-- -- Employees
insert into t_employees values(1,"liege","population ",7,
                                 "michel@mail.com",
                                 "0495/12.34.56","111111",'1970-01-01',
                                 "123456789",'2018-01-01','M',"single",0,0,6);
insert into t_employees values(2,"Liège","Stationnement",8,
                                 "pierre@mail.com",
                                 "0495/12.34.56","222222",'1970-01-01',
                                 "123456789",'2018-01-01','M',"single",0,0,7);
insert into t_employees values(3,"La hulpe","Population",9,
                                 "marie@mail.com",
                                 "0495/12.34.56","333333",'1970-01-01',
                                 "123456789",'2018-01-01','F',"single",0,0,8);
insert into t_employees values(4,"La hulpe","Population",10,
                                 "anne@mail.com",
                                 "0495/12.34.56","444444",'1970-01-01',
                                 "123456789",'2018-01-01','F',"single",0,0,9);
insert into t_employees values(5,"La hulpe","Stationnement",11,
                                 "jon@mail.com",
                                 "0495/12.34.56","555555",'1970-01-01',
                                 "123456789",'2018-01-01','M',"single",0,0,10);
insert into t_employees values(6,"Roux","Population",11,
                                 "chuck@mail.com",
                                 "0495/12.34.56","666666",'1970-01-01',
                                 "123456789",'2018-01-01','M',"single",0,0,11);

insert into t_employees values(8,"Fabian","Germeau-Empl",12,
                                 "fabian@mail.com",
                                 "0495/12.34.56","333",'1970-01-01',
                                 "123456789",'2018-01-01','M',"single",0,0,13);

insert into t_employees values(7,"Admin","Account",11,
                                 "admin@mail.com",
                                 "0495/12.34.56","999999",'1970-01-01',
                                 "123456789",'2018-01-01','M',"single",0,0,12);


-- -- Departments
insert into t_departments values(1, 1, "Population & état civil", 1, null,null); -- namur
insert into t_departments values(2, 1, "Mobilité",                         1, null,null); -- namur
insert into t_departments values(3, 2, "Population & état civil", 1, null,null); -- bastogne
insert into t_departments values(4, 2, "Mobilité",                         1, null,null); -- bastogne
insert into t_departments values(5, 3, "Population & état civil", 1, null,null); -- roux
insert into t_departments values(6, 3, "Mobilité",                         1, null,null); -- roux
insert into t_departments values(7, 4, "Population & état civil", 1, null,null); -- la hulpe
insert into t_departments values(8, 4, "Mobilité",                         1, null,null); -- la hulpe
insert into t_departments values(9, 5, "Population & état civil", 1, null,null); -- liege
insert into t_departments values(10,5, "Mobilité",                         1, null,null); -- liege
insert into t_departments values(11,6, "Population & état civil", 1, null,null); -- charleroi
insert into t_departments values(12,6, "Mobilité",                         1, null,null); -- charleroi



-- -- -- Departments managed requests types
insert into t_departments_request_types values(1,1);
insert into t_departments_request_types values(2,2);
insert into t_departments_request_types values(2,3);
insert into t_departments_request_types values(3,1);
insert into t_departments_request_types values(4,2);
insert into t_departments_request_types values(4,3);
insert into t_departments_request_types values(5,1);
insert into t_departments_request_types values(6,2);
insert into t_departments_request_types values(6,3);
insert into t_departments_request_types values(7,1);
insert into t_departments_request_types values(8,2);
insert into t_departments_request_types values(8,3);
insert into t_departments_request_types values(9,1);
insert into t_departments_request_types values(10,2);
insert into t_departments_request_types values(10,3);
insert into t_departments_request_types values(11,1);
insert into t_departments_request_types values(12,2);
insert into t_departments_request_types values(12,3);

-- -- Employees assignations
;
insert into t_departments_employees values (9,1); -- liege_popul
insert into t_departments_employees values (10,2); -- liege_stat
insert into t_departments_employees values (7,3); -- la hulpe_popul
insert into t_departments_employees values (7,4); -- la hulpe_popul
insert into t_departments_employees values (8,5); -- la hulpe_stat
insert into t_departments_employees values (5,6); -- roux_popul
insert into t_departments_employees values (6,8); -- roux_stationnement



-- -- Companies
insert into t_companies values ("123456", "BE123456", 11, "SPRL", 5, "Fernandez@Co","active"); -- 1ere société de david

-- Mandataries
insert into t_mandataries values(1, 5, "123456", "owner");



-- Municipality dependant requests parameters
-- -- parameters for nationality certificate
insert into t_parameters values(1, 1, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 2, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 3, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 4, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 5, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");

-- -- parameters for citizen parking card
insert into t_parameters values(2, 1, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>2</parameter>
<parameter id='parkingCard.fee'>2000</parameter></parameters>");
insert into t_parameters values(2, 2, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>2</parameter>
<parameter id='parkingCard.fee'>2000</parameter></parameters>");
insert into t_parameters values(2, 3, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>2</parameter>
<parameter id='parkingCard.fee'>2000</parameter></parameters>");
insert into t_parameters values(2, 4, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>2</parameter>
<parameter id='parkingCard.fee'>2000</parameter></parameters>");
insert into t_parameters values(2, 5, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>2</parameter>
<parameter id='parkingCard.fee'>2000</parameter></parameters>");

-- -- parameters for company parking card
insert into t_parameters values(3, 1, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>4</parameter>
<parameter id='parkingCard.fee'>2500</parameter></parameters>");
insert into t_parameters values(3, 2, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>3</parameter>
<parameter id='parkingCard.fee'>3000.5</parameter></parameters>");
insert into t_parameters values(3, 3, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>4</parameter>
<parameter id='parkingCard.fee'>7000</parameter></parameters>");
insert into t_parameters values(3, 4, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>10</parameter>
<parameter id='parkingCard.fee'>5000</parameter></parameters>");
insert into t_parameters values(3, 5, "<?xml version='1.0' encoding='UTF-8'?><parameters>
<parameter id='activated'>true</parameter>
<parameter id='parkingCard.periodValidity'>18</parameter>
<parameter id='parkingCard.termPayment'>20</parameter>
<parameter id='parkingCard.fee'>4000</parameter></parameters>");
