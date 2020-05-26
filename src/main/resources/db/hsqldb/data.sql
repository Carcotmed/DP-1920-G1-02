-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');


INSERT INTO vets (id, first_name, last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets (id, first_name, last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets (id, first_name, last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets (id, first_name, last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets (id, first_name, last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets (id, first_name, last_name) VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties (id, name) VALUES (1, 'radiology');
INSERT INTO specialties (id, name) VALUES (2, 'surgery');
INSERT INTO specialties (id, name) VALUES (3, 'dentistry');

INSERT INTO vet_specialties (vet_id, specialty_id) VALUES (2, 1);
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES (3, 3);
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES (4, 2);
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES (5, 1);

INSERT INTO types (id, name) VALUES (1, 'cat');
INSERT INTO types (id, name) VALUES (2, 'dog');
INSERT INTO types (id, name) VALUES (3, 'lizard');
INSERT INTO types (id, name) VALUES (4, 'snake');
INSERT INTO types (id, name) VALUES (5, 'bird');
INSERT INTO types (id, name) VALUES (6, 'hamster');

INSERT INTO owners (id,first_name,last_name,address,city,telephone,username) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');
INSERT INTO owners (id,first_name,last_name,address,city,telephone) VALUES (11, 'Vet', 'Vet', '2395 Independence La.', 'Waunakee', '6089999487');

INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (14, 'Adoptable1', '2010-06-08', 1, 11);
INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (15, 'Adoptable2', '2012-06-08', 2, 11);

INSERT INTO pets (id,name,birth_date,type_id,owner_id) VALUES (16, 'Luna', '2012-06-08', 1, 1);

INSERT INTO adoptions (id,date,pet_id,owner_id) VALUES (1,'2021-04-20',1,1);

INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (1, 1, '2013-01-01', 'rabies shot', 'Owners Father');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (2, 8, '2013-01-02', 'rabies shot', 'Owners Mother');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (3, 8, '2013-01-03', 'neutered', 'Owners Brother');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (4, 7, '2013-01-04', 'spayed', 'Owners Sister');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (5, 1, '2013-01-01', 'peluqueria', 'Owners Father');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (6, 1, '2019-01-01', 'Test1', 'Owners Father');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (7, 1, '2019-01-01', 'Test2', 'Owners Father');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (8, 1, '2019-01-01', 'Test3', 'Owners Father');
INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (9, 1, '2019-01-01', 'Test4', 'Owners Father');

INSERT INTO visits (id,pet_id,visit_date,description, bringer) VALUES (10, 16, '2020-01-01', 'Test1', 'Daniel Trujillo Gallardo');


INSERT INTO providers (id,name,phone,address,email) VALUES (1,'Pipo1','123456789','Calle Pipo nº1','pipo1@gmail.com');
INSERT INTO providers (id,name,phone,address,email) VALUES (2,'Pipo2','223456789','Calle Pipo nº2','pipo2@gmail.com');
INSERT INTO providers (id,name,phone,address,email) VALUES (3,'Pipo3','323456789','Calle Pipo nº3','pipo3@gmail.com');
INSERT INTO providers (id,name,phone,address,email) VALUES (4,'Pipo4','423456789','Calle Pipo nº4','pipo4@gmail.com');
INSERT INTO providers (id,name,phone,address,email) VALUES (5,'Pipo5','523456789','Calle Pipo nº5','pipo5@gmail.com');


INSERT INTO products (id,name,price,quantity,all_available,provider_id,enabled) VALUES (1,'Pomadita',20.50,5,true,1,true);
INSERT INTO products (id,name,price,quantity,all_available,provider_id,enabled) VALUES (2,'Collar',21.50,5,true,2,true);
INSERT INTO products (id,name,price,quantity,all_available,provider_id,enabled) VALUES (3,'Juguete',40.50,5,true,4,true);
INSERT INTO products (id,name,price,quantity,all_available,provider_id,enabled) VALUES (4,'Jeringuilla',20.50,0,true,1,true);

INSERT INTO interventions (id, visit_id, vet_id, name) VALUES (1, 1, 1, 'Castracion');
INSERT INTO interventions (id, visit_id, vet_id, name) VALUES (2, 5, 2, 'Peluquería');

INSERT INTO interventions (id, visit_id, vet_id, name) VALUES (4, 7, 2, 'Peluquería');
INSERT INTO interventions (id, visit_id, vet_id, name) VALUES (5, 8, 2, 'Peluquería');
INSERT INTO interventions (id, visit_id, vet_id, name) VALUES (6, 9, 2, 'Peluquería');

UPDATE visits SET intervention_id = 1 WHERE visits.id = 1;
UPDATE visits SET intervention_id = 5 WHERE visits.id = 8;
UPDATE visits SET intervention_id = 4 WHERE visits.id = 7;
UPDATE visits SET intervention_id = 2 WHERE visits.id = 5;
UPDATE visits SET intervention_id = 6 WHERE visits.id = 9;

INSERt INTO events (id, date, published, description, capacity, place, provider_id) VAlUES (1, '2020-09-01', true, 'Descripcion1', 10, 'Place1', 1);
INSERt INTO events (id, date, published, description, capacity, place) VAlUES (2, '2020-07-02', true, 'Descripcion2', 20, 'Place2');
INSERt INTO events (id, date, published, description, capacity, place) VAlUES (3, '2029-03-03', true, 'Descripcion3', 10000, 'Place3');
INSERt INTO events (id, date, published, description, capacity, place) VAlUES (4, '2031-04-04', false, 'Descripcion4', 40, 'Place4');
INSERt INTO events (id, date, published, description, capacity, place) VAlUES (5, '2031-04-04', false, 'Descripcion5', 40, '');

INSERt INTO participations (id, event_id, owner_id) VAlUES (1, 1, 1);
INSERt INTO participations (id, event_id, owner_id) VAlUES (2, 2, 2);
INSERt INTO participations (id, event_id, owner_id) VAlUES (3, 1, 3);

INSERT INTO discounts (id,product_id,provider_id,percentage,quantity,enabled) VALUES (1, 1, 1, 45.0, 10,true);
INSERT INTO discounts (id,product_id,provider_id,percentage,quantity,enabled) VALUES (2, 2, 1, 55.0, 50,true);
INSERT INTO discounts (id,product_id,provider_id,percentage,quantity,enabled) VALUES (3, 1, 3, 65.0, 20,false);


INSERT INTO orders(id,quantity,order_date,arrival_date,sent,provider_id,product_id,discount_id) VALUES (1,3,'2013-01-01',null,true,1,1,1);
INSERT INTO orders(id,quantity,order_date,arrival_date,sent,provider_id,product_id,discount_id) VALUES (2,55,'2013-02-01',null,false,2,2,2);
INSERT INTO orders(id,quantity,order_date,arrival_date,sent,provider_id,product_id,discount_id) VALUES (3,7,'2013-03-01','2013-03-02',true,3,3,3);