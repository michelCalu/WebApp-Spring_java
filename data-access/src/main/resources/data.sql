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


insert into t_addresses values(1,"Avenue de la Reine", 279,"A",5680,"Doische", "Wallonie", "Belgique");
insert into t_addresses values(2,"Place du miroir", 1,null,5680,"Doische", "Wallonie", "Belgique");
insert into t_addresses values(3,"chaussée de la hulpe", 748,null,1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(4,"Avenue de Charles-Quint", 140,null,6600,"Bastogne", "Wallonie", "Belgique");
insert into t_addresses values(5,"Avenue Van Overbeke", 12, "B",6600,"Bastogne", "Wallonie", "Belgique");
insert into t_addresses values(6,"Rue du Noyer", 43, null, 6600,"Bastogne", "Wallonie", "Belgique");
insert into t_addresses values(7,"Rue Sart-lez-moulins", 77, null, 6044,"Roux", "Charleroi", "Belgique");
insert into t_addresses values(8,"Rue des combattants", 59, null, 1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(9,"Rue du burnout", 66, null, 1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(10,"Rue du bobard", 55, null, 1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(11,"Rue des pepettes", 44, null, 4000,"Liege", "Province de Liege", "Belgique");

insert into t_municipalities values(1, "Doische", 1, "info@doische.be", "082/654.12.20", "Hervé Doyen");
insert into t_municipalities values(2, "Bastogne", 4, "bastogne@bastogne.irisnet.be", "061/465.12.77", "Robert Genard");
insert into t_municipalities values(3, "Roux", 7, "roux@municipality.com", "RouxPhone", "Paul Magnette");
insert into t_municipalities values(4, "La Hulpe", 8, "lh@municipality.com", "lhPhone", "Gonzague pull rose");
insert into t_municipalities values(5, "Liege", 11, "litche@municipality.com", "lPhone", "Moreau");

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
insert into t_departments values(6, 3, "Population", 2, null,null);
insert into t_departments values(7, 3, "Stationnement", 2, null,null);
insert into t_departments values(8, 4, "Population", 2, null,null);
insert into t_departments values(9, 4, "Stationnement", 2, null,null);
insert into t_departments values(10, 5, "Population", 2, null,null);
insert into t_departments values(11, 5, "Stationnement", 2, null,null);

insert into t_departments_employees values (8,4);
insert into t_departments_employees values (9,5);


insert into t_requests values(1, 1, 1, null, 1, 1, 1, "HERM001", "my ref", "JETTE001");
insert into t_requests values(2, 2, 2, null, 2, 5, 2, "HERM002", "a parking ref.", "GANSH001");
insert into t_requests values(3, 2, 3, null, 4, 8, 1, "HERM003", "a parking ref.", "GANSH001");

insert into t_request_field_values values(1, "citizenParkingCardCarMake", 2, "Fiat", null, null);
insert into t_request_field_values values(2, "citizenParkingCardCarModel", 2, "Punto", null, null);
insert into t_request_field_values values(3, "citizenParkingCardCarColour", 2, "red", null, null);
insert into t_request_field_values values(4, "citizenParkingCardPlateNumber", 2, "123456", null, null);
insert into t_request_field_values values(5, "citizenParkingCardGreenCard", 2, "file.pdf", "pdf", "TODO: should be the BLOB");


insert into t_document_titles values (1, "citizenParkingCard");
insert into t_document_titles values (2, "citizenParkingPayment");
insert into t_document_titles values (3, "citizenParkingDecision");
insert into t_document_titles values (4, "nationalityCertificate");

insert into t_companies values("111111", "BE111111",9, "sprl", 1, "world company","created");
insert into t_companies values("222222", "BE222222",10, "SA", 1, "world company","created");
insert into t_companies values("333333", "BE333333",11, "SNC", 1, "world company","created");

insert into t_mandataries values(1,1,"111111","owner");
insert into t_mandataries values(2,3,"222222","owner");
insert into t_mandataries values(3,3,"333333","owner");

-- parameters for nationality certificate
insert into t_parameters values(1, 1, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 2, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 3, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 4, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");
insert into t_parameters values(1, 5, "<?xml version='1.0' encoding='UTF-8'?><parameters><parameter id='activated'>true</parameter></parameters>");

-- parameters for citizen parking card
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

-- parameters for company parking card
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
