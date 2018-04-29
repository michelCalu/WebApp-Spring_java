-- bootstrap data
insert into t_request_types values(1,"nationalityCertificate");
insert into t_request_types values(2,"parkingCard");

insert into t_user_statusses values(1, "pendingValidation");
insert into t_user_statusses values(2, "active");

insert into t_req_statusses values (1, "New");
insert into t_req_statusses values (2, "Ongoing");

-- test data

insert into t_addresses values(1,"Avenue de la Reine", 279,"A",1020,"Jette", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(2,"Place du miroir", 1,null,1020,"Jette", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(3,"Rue des combattants", 10,null,1310,"La Hulpe", "Brabant Wallon", "Belgique");
insert into t_addresses values(4,"Avenue de Charles-Quint", 140,null,1083,"Ganshoren", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(5,"Avenue Van Overbeke", 12, "B",1083,"Ganshoren", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(6,"Rue du Noyer", 43, null, 1000,"Bruxelles", "Région de Bruxelles-Capitale", "Belgique");

insert into t_citizens values(1,"Thomas","Elskens",1,"thomaselskens@hotmail.com",null,"NRN_Thomas","1983-11-13",2);
insert into t_citizens values(2,"David","Fernandez",5,"dfernandez1612@gmail.com",null,"NRN_David","1983-12-16",2);

insert into t_employees values(1,"Fabian","Germeau",2,
			"fabian.germeau@cetic.be",
			"somePhone","someNRN",'1970-01-01',
			"someAccountNumber",'2018-04-01','M',"bachelor",0,0);

insert into t_employees values(2,"Degryse","Killian",6,
			"killian.degryse@hotmail.com",
			"somePhone","NRN_Killian",'1990-01-01',
			"someAccountNumber",'2018-04-16','M',"bachelor",0,0);
            
            

insert into t_municipalities values(1, "Jette", 1, "info@jette.be", "02/654.12.20", "Hervé Doyen");
insert into t_municipalities values(2, "Ganshoren", 4, "ganshoren@ganshoren.irisnet.be", "02/465.12.77", "Robert Genard");

insert into t_departments values(1, 1, "Population & État civil", 1, null);
insert into t_departments values(2, 1, "État civil étrangers", 1, 1);
insert into t_departments values(3, 1, "Mobilité", 1, null);
insert into t_departments values(4, 2, "Population", 2, null);

insert into t_requests values(1, 1, 1, null, 1, 1, 1, 1, "HERM001", "my ref", "JETTE001");
