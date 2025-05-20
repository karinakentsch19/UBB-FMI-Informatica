create table Locatii
(
	id_locatie int primary key identity,
	localitate varchar(100),
	strada varchar(100),
	numar varchar(100),
	cod_postal varchar(100)
);

create table Magazine
(
	id_magazin int primary key identity,
	denumire varchar(100),
	an_deschidere int,
	id_locatie int foreign key references Locatii(id_locatie)
);

create table Clienti
(
	id_client int primary key identity,
	nume varchar(100),
	prenume varchar(100),
	gen varchar(100),
	data_nastere date
);

create table ProduseFavorite
(
	id_produs int primary key identity,
	denumire_produs varchar(100),
	pret float,
	reducere int check(reducere >= 0 and reducere <=100),
	id_client int foreign key references Clienti(id_client)
);

create table ClientiMagazine 
(
	id_client int foreign key references Clienti(id_client),
	id_magazin int foreign key references Magazine(id_magazin),
	data_cumpararii date,
	pret float
	constraint pk_clientimagazine primary key(id_client, id_magazin)
);

insert into Locatii(localitate, strada, numar, cod_postal) values
('localitate1', 'strada1', 'numar1', 'cod1'),
('localitate2', 'strada2', 'numar2', 'cod2');

insert into Magazine(denumire, an_deschidere, id_locatie) values
('magazin1', '2002', 1),
('magazin2', '2020', 1),
('magazin3', '2013', 2);

insert into Clienti(nume, prenume, gen, data_nastere) values
('Ion', 'Pop', 'masculin', '2003-04-03'),
('Karina', 'Kentsch', 'feminin', '2003-05-19'),
('Tania', 'Nemes', 'feminin', '2003-09-21'),
('Mihnea', 'Tusinean', 'masculin', '2003-12-12');

insert into ClientiMagazine(id_client, id_magazin, data_cumpararii, pret) values
(1, 1, '2023-12-01', 12.2),
(1, 2, '2022-01-01', 100.12),
(1, 3, '2014-01-06', 200.99),
(2, 1, '2007-12-29', 200.99),
(2, 3, '2017-12-12', 200.99),
(2, 2, '2023-06-12',100.00),
(3, 1, '2010-09-09', 12.2);

insert into ProduseFavorite(denumire_produs, pret, reducere, id_client) values
('produs1', 12.2, 10, 1),
('produs2', 100.12, 20, 1),
('produs3', 200.99, 50, 1),
('produs3', 200.99, 50, 2),
('produs4', 130.00, 0, 2),
('produs5', 100.00, 10, 1),
('produs1', 12.2, 10, 3);

--procedura care primeste un client, magazin, data cumparare si un pret si adauga clientul magazinului
--daca exista, se actualizeaza data cumpararii si pretul
go
create or alter procedure addOrUpdate
		@id_client int,
		@id_magazin int,
		@data_cumparare date,
		@pret float
as
begin	
	if (select count(*) from ClientiMagazine where id_client = @id_client and id_magazin = @id_magazin) = 0
		insert into ClientiMagazine(id_client, id_magazin, data_cumpararii, pret) values
		(@id_client, @id_magazin, @data_cumparare, @pret)
	else
		update ClientiMagazine
		set
			data_cumpararii = @data_cumparare,
			pret = @pret
		where
			id_client = @id_client and id_magazin = @id_magazin
end

--exec addOrUpdate 4, 1, '2020-07-21', 123.02
--select * from ClientiMagazine

--view care afiseaza numele clientilor ce au cel mult 3 produse favorite
go
create or alter view listaClienti
as
	select C.nume, C.prenume, count(*) as NumarProduseFavorite
	from Clienti C
	join ProduseFavorite PF on C.id_client = PF.id_client
	group by C.nume, C.prenume
	having count(*) <= 3

select * from listaClienti