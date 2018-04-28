-- bootstrap data
insert into t_request_types values(null,"nationalityCertificate");
insert into t_request_types values(null,"parkingCard");

--test data

insert into t_addresses values(null,"Avenue de la Reine", 279,"A",1020,"Jette", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(null,"Place du miroir", 1,null,1020,"Jette", "Région de Bruxelles-Capitale", "Belgique");
insert into t_addresses values(null,"Rue des combattants", 10,null,1310,"La Hulpe", "Brabant Wallon", "Belgique");


insert into t_citizens values(null,"Thomas","Elskens",1,"thomaselskens@hotmail.com",null,"NRN","1983-11-13",1);
insert into t_employees values(null,"Fabian","Germeau",2,
			"fabian.germeau@cetic.be",
			"somePhone","someNRN",'1970-01-01',
			"someAccountNumber",'2018-04-01','M',"bachelor",0,0);


insert into t_municipalities values(1, "Jette", 1, "info@jette.be", "02/654.12.20", "Hervé Doyen");

insert into t_req_statusses values (1, "New");
insert into t_req_statusses values (2, "Ongoing");

insert into t_requests values(null, 1, 1, null, 1, 1, 1, 1, "HERM001", "my ref", "JETTE001");
