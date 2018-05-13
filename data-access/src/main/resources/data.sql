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
insert into t_request_field_definitions values("companyParkingCardVisitorProof", 3, "binary", true);

insert into t_req_statusses values(null,"new");
insert into t_req_statusses values(null,"ongoing");
insert into t_req_statusses values(null,"awaitingInfo");
insert into t_req_statusses values(null,"rejected");
insert into t_req_statusses values(null,"accepted");


insert into t_event_types values(null,"created");
insert into t_event_types values(null,"updated");
insert into t_event_types values(null,"closed");
insert into t_event_types values(null,"assigneeChange");

-- test data
insert into t_user_accounts values(1,"ROLE_USER","$2a$10$nci/VfQ6BffNStE8ECXmq.7/fs4FBGTghAOfijxmMcx4r7A7JZOdK","active"); -- thomas
insert into t_user_accounts values(2,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- david
insert into t_user_accounts values(3,"ROLE_USER","$2a$10$rbsCA7cN7vZTcFjV3gN9A.tuNTBlPEOQbOIuPHOW3YTWCM5t0cHd.","active"); -- fab
insert into t_user_accounts values(null,"ROLE_USER","myPassword","active"); -- killian
insert into t_user_accounts values(5,"ROLE_USER","$2a$10$fg0oXDkagxfaMl7e/i56q.BN2wyRw53WlXIV1MyL.ywbISo4bGVqi","active"); -- michel
insert into t_user_accounts values(6,"ROLE_ADMIN","$2a$10$4w9Wm8gc93DpDQdyZ7SR5O.17XQMsfElDZRVbKuzaQJVi.0s.5xn.","active"); -- admin
insert into t_user_accounts values(7,"ROLE_USER","$2a$10$4w9Wm8gc93DpDQdyZ7SR5O.17XQMsfElDZRVbKuzaQJVi.0s.5xn.","active");


insert into t_addresses values(1,"Avenue de la Reine", 279,"A",1090,"Jette", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(2,"Place du miroir", 1,null,1020,"Jette", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(3,"Rue des combattants", 10,null,1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(4,"Avenue de Charles-Quint", 140,null,1083,"Ganshoren", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(5,"Avenue Van Overbeke", 12, "B",1083,"Ganshoren", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(6,"Rue du Noyer", 43, null, 1000,"Bruxelles", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(7,"Rue Sart-lez-moulins", 77, null, 6044,"Roux", "Charleroi", "Belgique");
insert into t_addresses values(9,"Rue grandgagnage", 1, null, 5000,"Namur", "Namur", "Belgique");

insert into t_municipalities values(1, "Jette", 1, "info@jette.be", "02/654.12.20", "Hervé Doyen");
insert into t_municipalities values(2, "Ganshoren", 4, "ganshoren@ganshoren.irisnet.be", "02/465.12.77", "Robert Genard");
insert into t_municipalities values(4, "Roux", 7, "roux@municipality.com", "RouxPhone", "Paul Magnette");
insert into t_municipalities values(3, "La Hulpe", 3, "lh@municipality.com", "lhphone", "palpatine");
insert into t_municipalities values(5, "Namur", 9, "nam@municipality.com", "phone", "gonzalez");

insert into t_citizens values(1,"Thomas","Elskens",1,"thomaselskens@hotmail.com",null,"NRN_Thomas","1983-11-13",1);
insert into t_citizens values(2,"David","Fernandez",5,"dfernandez1612@gmail.com",null,"david","1983-12-16",2);
insert into t_citizens values(3,"Michel","Calu",3,"michel@unamur.be",null,"12345612312","1976-12-16",5);
insert into t_citizens values(4,"Fabian","Germeau",7,"germeau.fabian@gmail.com",null,"NRN_Fabian","1991-07-02",3);
insert into t_citizens values(5,"Vishnu","Barbu",3,"michel@unamur.be",null,"12345612313","1976-12-16",7);


insert into t_employees values(1,"Fabian","Germeau",2,
																 "fabian.germeau@cetic.be",
																 "somePhone","someNRN",'1970-01-01',
																 "someAccountNumber",'2018-04-01','M',"bachelor",0,0,3);

insert into t_employees values(2,"Degryse","Killian",6,
																 "killian.degryse@hotmail.com",
																 "somePhone","NRN_Killian",'1990-01-01',
																 "someAccountNumber",'2018-04-16','M',"bachelor",0,0,4);

insert into t_employees values(3,"Admin","X",6,
																 "admin@admin.com",
																 "somePhone","00000000000",'1990-01-01',
																 "someAccountNumber",'2018-04-16','M',"bachelor",0,0,6);

insert into t_employees values(4,"Jean","dupont",3,
																 "admin@admin.com",
																 "somePhone","11111111111",'1990-01-01',
																 "someAccountNumber",'2018-04-16','M',"singlz",0,0,7);

insert into t_employees values(5,"Jean","dubois",3,
																 "admin@admin.com",
																 "somePhone","22222222222",'1990-01-01',
																 "someAccountNumber",'2018-04-16','M',"singlz",0,0,7);

insert into t_departments values(1, 1, "Population & état civil", 1, null,null);
insert into t_departments values(2, 1, "état civil étrangers", 1, 1,null);
insert into t_departments values(3, 1, "Mobilité", 1, null,null);
insert into t_departments values(4, 2, "Population", 2, null,null);
insert into t_departments values(5, 2, "Stationnement", 2, null,null);
insert into t_departments values(6, 4, "Population", 2, null,null);
insert into t_departments values(7, 4, "Stationnement", 2, null,null);
insert into t_departments values(8, 3, "Population", 2, null,null);
insert into t_departments values(9, 3, "Mobilité", 2, null,null);
insert into t_departments values(10, 5, "Population", 2, null,null);
insert into t_departments values(11, 5, "Stationnement", 2, null,null);

insert into t_departments_employees values (8,4);
insert into t_departments_employees values (9,5);


insert into t_requests values(1, 1, 1, null, 1, 1, 1, "HERM001", "my ref", "JETTE001");
insert into t_requests values(2, 2, 2, null, 2, 5, 2, "HERM002", "a parking ref.", "GANSH001");
insert into t_requests values(3, 2, 3, null, 4, 8, 1, "HERM003", "a parking ref.", "GANSH001");

insert into t_request_field_values values(1, "citizenParkingCardCarMake", 2, "Fiat", null);
insert into t_request_field_values values(2, "citizenParkingCardCarModel", 2, "Punto", null);
insert into t_request_field_values values(3, "citizenParkingCardCarColour", 2, "red", null);
insert into t_request_field_values values(4, "citizenParkingCardPlateNumber", 2, "123456", null);
insert into t_request_field_values values(5, "citizenParkingCardGreenCard", 2, null, "TODO: should be the BLOB");


insert into t_document_titles values (1, "citizeParkingCard");
insert into t_document_titles values (2, "citizenParkingPayment");
insert into t_document_titles values (3, "citizenParkingDecision");
insert into t_document_titles values (4, "nationalityCertificate");









