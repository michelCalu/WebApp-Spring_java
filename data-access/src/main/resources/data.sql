-- test data
insert into t_request_types values(1,"nationalityCertificate");

insert into t_addresses values(null,"Belgium","Bruxelles",1020,"Avenue de la Reine", 279);
insert into t_addresses values(null,"Belgium","Bruxelles",1080,"Quelque Rue", 13);

insert into t_citizens values(null,"Thomas","Elskens",1,"thomaselskens@hotmail.com",null,"NRN","1983-11-13",1);
insert into t_employees values(null,"Fabian","Germeau",2,
			"fabian.germeau@cetic.be",
			"somePhone","someNRN",'1970-01-01',
			"someAccountNumber",'2018-04-01','M',"bachelor",0,0);
			
insert into t_requests values(null,1,1,1,4);
insert into t_requests values(null,1,1,null,0);
