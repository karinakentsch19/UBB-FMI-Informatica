create table Tipuri
(
	id_tip int primary key identity,
	denumire_tip varchar(100),
	descriere_tip varchar(100)
);

create table Locatii
(
	id_locatie int primary key identity,
	tara varchar(100),
	regiune varchar(100),
	oras varchar(100)
);

create table Masini
(
	id_masina int primary key identity,
	nr_inmatriculare varchar(20),
	brand varchar(100),
	model varchar(100),
	culoare varchar(100),
	id_tip int foreign key references Tipuri(id_tip)
);

create table Curse
(
	id_cursa int primary key identity,
	denumire_cursa varchar(100),
	data_inceput date,
	data_final date,
	id_locatie int foreign key references Locatii(id_locatie)
);

create table MasiniCurse
(
	id_masina int foreign key references Masini(id_masina),
	id_cursa int foreign key references Curse(id_cursa),
	punct_pornire int,
	punct_sosire int,
	loc int,
	constraint pk_MasiniCurse primary key(id_masina, id_cursa)
);

insert into Tipuri(denumire_tip, descriere_tip) values 
('denumire1', 'descriere1'),
('denumire2', 'descriere2'),
('denumire3', 'descriere3');

insert into Locatii(tara, regiune, oras) values 
('tara1', 'regiune1', 'oras1'),
('tara2', 'regiune2', 'oras2'),
('tara3', 'regiune3', 'oras3');

insert into Masini(nr_inmatriculare, brand, model, culoare, id_tip) values 
('SM 24 AGP', 'BMW', 2020,'negru',1), 
('BH 77 BLS', 'AUDI', 2021, 'gri', 1),
('SM 07 WMW', 'MERCEDES', 2022, 'alb', 2);

insert into Curse(denumire_cursa, data_inceput, data_final, id_locatie) values
('denumire1', '2021-12-02', '2021-12-10', 1), 
('denumire2','2021-10-02', '2021-10-10', 2),
('denumire3','2021-09-02', '2021-09-10', 2),
('denumire4','2022-01-02', '2022-01-10', 2);

insert into MasiniCurse(id_masina, id_cursa, punct_pornire, punct_sosire, loc) values 
(1,1,1,23,2), 
(1,2,1,15,1), 
(1,3,1,17,2),
(1,4,1,21,3);


--Se da o masina, o cursa, punct pornire, punct sosire si locul ocupat.
--daca masina a participat la cursa se actualizeaza pornire, sosire, loc, altfel se adauga

go
create or alter procedure addOrUpdate
	@id_masina int,
	@id_cursa int,
	@punct_pornire int,
	@punct_sosire int,
	@loc_ocupat int
as
begin
	if (select count(*) from MasiniCurse where id_masina = @id_masina and id_cursa = @id_cursa) = 0
		insert into MasiniCurse(id_masina, id_cursa, punct_pornire, punct_sosire, loc) values
		(@id_masina, @id_cursa, @punct_pornire, @punct_sosire, @loc_ocupat)
	else
		update MasiniCurse
		set
			punct_pornire = @punct_pornire,
			punct_sosire = @punct_sosire,
			loc = @loc_ocupat
		where id_masina = @id_masina and id_cursa = @id_cursa
end;

--select * from MasiniCurse
--exec addOrUpdate 2,1,2,20,1


--functie pt masini care au participat la mai mult de 3 curse
go
create or alter function masiniCuMaiMultDe3Curse()
returns table
as
	return 
		select M.id_masina, M.nr_inmatriculare, count(C.id_cursa) as NrCurse
		from Masini M
		join MasiniCurse MC on M.id_masina = MC.id_masina
		join Curse C on MC.id_cursa = C.id_cursa
		group by M.id_masina, M.nr_inmatriculare
		having count(C.id_cursa) >= 3

select * from dbo.masiniCuMaiMultDe3Curse()