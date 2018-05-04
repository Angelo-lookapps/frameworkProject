/*SEQUENCE */
CREATE sequence seq_Avion start with 1 increment by 1;
CREATE sequence seq_Client  start with 1 increment by 1;
CREATE sequence seq_TypeVol  start with 1 increment by 1;
CREATE sequence seq_TypeAvion  start with 1 increment by 1;
CREATE sequence seq_TypeClasse  start with 1 increment by 1;
CREATE sequence seq_Billet  start with 1 increment by 1;
CREATE sequence seq_Destination  start with 1 increment by 1;
CREATE sequence seq_Reservation  start with 1 increment by 1;

create table Client(
	id varchar(15) primary key,
	nom varchar(50),
	prenom varchar(50));

create table TypeVol(
	id varchar(15) primary key,
	valeur varchar(50));

create table TypeClasse(
	id varchar(15) primary key,
	valeur varchar(50));
	
create table TypeAvion(
	id varchar(15) primary key,
	valeur varchar(50));

create table Destination(
	id varchar(15) primary key,
	valeur varchar(50),
	prix decimal (10,2)
);
	
create table Billet(
	id varchar(15) primary key,
	idDestination varchar(50),
	daty datetime,
	prixUnitaire decimal (10,2)
);
	
create table Avion(
	id varchar(15) primary key,
	idTypeAvion varchar(15),
	idTypeVol varchar(15),
	nom varchar(50),
	foreign key (idTypeAvion) references TypeAvion(id),
	foreign key (idTypeVol) references TypeVol(id)
);

create table Reservation(
	id varchar(15) primary key,
	idClient varchar(15),
	idBillet varchar(15),
	idTypeClasse varchar(15),
	dateReservation datetime,
	
	foreign key (idClient) references Client(id),
	foreign key (idTypeClasse) references TypeClasse(id),
	foreign key (idBillet) references Billet(id));
	
	
INSERT INTO Client VALUES('CLT0001','LOCK TO HANG','Angelo');
INSERT INTO Client VALUES('CLT0002','CHAN','Ryan');
INSERT INTO Client VALUES('CLT0003','RABEMANANTSOA','Jason');

INSERT INTO TypeVol VALUES('TYPV0001','Regional');
INSERT INTO TypeVol VALUES('TYPV0002','International');

INSERT INTO TypeClasse VALUES('TYPC0001','Economique');	
INSERT INTO TypeClasse VALUES('TYPC0002','Affaire');	
INSERT INTO TypeClasse VALUES('TYPC0003','Premiere');	

INSERT INTO TypeAvion VALUES('TYPA0001','Airbus');	
INSERT INTO TypeAvion VALUES('TYPA0002','Boeing');	
INSERT INTO TypeAvion VALUES('TYPA0003','Junkers');	
INSERT INTO TypeAvion VALUES('TYPA0004','Bernard');	

INSERT INTO Destination VALUES('DES0001','Antananarivo',500000);
INSERT INTO Destination VALUES('DES0002','Mahajanga',550000);
INSERT INTO Destination VALUES('DES0003','Nosy Be',700000);
INSERT INTO Destination VALUES('DES0004','Sainte-Marie',650000);
INSERT INTO Destination VALUES('DES0005','Toamasina',200000);

INSERT INTO Destination VALUES('DES0006','Chine/Guangzhou',2400000);
INSERT INTO Destination VALUES('DES0007','France/Marseille',3000000);
INSERT INTO Destination VALUES('DES0008','France/Paris',3500000);
INSERT INTO Destination VALUES('DES0009','Maurice/Port Louis',1050000);
INSERT INTO Destination VALUES('DES0010','Réunion/Saint Denis',1000000);

INSERT INTO Billet VALUES('BLT0001','DES0001','08-08-2017',1500000);
INSERT INTO Billet VALUES('BLT0002','DES0001','01-09-2015',5000000);
INSERT INTO Billet VALUES('BLT0003','DES0003','06-06-2016',3000000);
INSERT INTO Billet VALUES('BLT0004','DES0006','02-05-2013',4500000);
INSERT INTO Billet VALUES('BLT0005','DES0009','10-10-2014',6500000);

INSERT INTO Reservation VALUES('RSV0001','CLT0001','BLT0001','TYPC0001','06-08-2017');
INSERT INTO Reservation VALUES('RSV0002','CLT0003','BLT0003','TYPC0003','04-06-2016');
	
