create table Tari
(
	id_tara int primary key identity,
	nume_tara varchar(100)
);

create table Regizori
(
	id_regizor int primary key identity,
	nume_regizor varchar(100),
	data_nasterii date
);

create table Clienti
(
	id_client int primary key identity,
	nume_client varchar(100)
);

create table Actori
(
	id_actor int primary key identity,
	nume_actor varchar(100),
	id_tara int foreign key references Tari(id_tara)
);

create table Filme
(
	id_film int primary key identity,
	titlu_film varchar(100),
	durata int,
	an int,
	pret float,
	id_regizor int foreign key references Regizori(id_regizor)
);

create table FilmeActori
(
	id_film int foreign key references Filme(id_film),
	id_actor int foreign key references Actori(id_actor)
);

create table Inchirieri
(
	id_client int foreign key references Clienti(id_client),
	id_film int foreign key references Filme(id_film),
	data_inchiriere datetime,
	data_returnare datetime
);

create table Tipuri
(
	id_tip int primary key identity,
	descriere_tip varchar(100)
);

create table TipuriFilme
(
	id_tip int foreign key references Tipuri(id_tip),
	id_film int foreign key references Filme(id_film)
);

insert into Tari(nume_tara) values
('Romania'),
('USA');

insert into Regizori(nume_regizor, data_nasterii) values
('Nume1', '1987-03-14'),
('Nume2', '1990-06-18');

insert into Actori(nume_actor, id_tara) values
('Actor1', 1),
('Actor2', 2),
('Actor3',1);

insert into Clienti(nume_client) values
('Client1'),
('Client2');

insert into Filme(titlu_film, durata, an, pret, id_regizor) values
('Poveste x', 120, 2020, 15,1),
('Poveste y', 90, 2012, 10,2),
('titlu', 120, 2010, 15,1);

insert into Filme(titlu_film, durata, an, pret, id_regizor) values
('titlu4', 120, 2020, 15, 1),
('titlu5', 120, 2020, 15, 1),
('titlu6', 120, 2021, 34, 1),
('titlu7', 120, 2021, 34, 1);

insert into FilmeActori(id_actor, id_film) values
(1,2),
(1, 3),
(2, 1),
(2, 2),
(3,2),
(3,3),
(1,1),
(1, 4);

insert into Inchirieri(id_client, id_film, data_inchiriere, data_returnare) values
(1,1,'2023-01-12 13:00:00', '2023-01-18 17:00:00'),
(1,2,'2023-01-12 13:00:00', '2023-01-18 17:00:00'),
(2,3,'2023-01-10 13:00:00', '2023-01-11 17:00:00'),
(2,1,'2023-01-10 13:00:00', '2023-01-11 17:00:00');

insert into Clienti(nume_client) values
('Nume3'),
('Nume4');

insert into Inchirieri(id_client, id_film, data_inchiriere, data_returnare) values
(1,7,'2023-01-12 13:00:00', '2023-01-18 17:00:00');

--interogare care sa afiseze toate filmele care au in titlu cuvantul poveste
select *
from Filme F
where F.titlu_film like '%poveste%'

--view care afișează acei actori care au jucat in mai mult de 3 filme
go
create view actoriMaiMult3Filme
as
	select A.nume_actor
	from Actori A
	join FilmeActori FA on A.id_actor = FA.id_actor
	group by A.nume_actor
	having count(*) > 3

select * from actoriMaiMult3Filme

--procedura stocata care afiseaza datele despre filmele care nu au fost inchiriate de niciun client.
go
create procedure filmeNeinchiriate
as
begin
	select F.titlu_film, F.durata, F.an, F.id_regizor
	from Filme F
	left join Inchirieri I on I.id_film = F.id_film
	where I.id_film is Null
end

exec filmeNeinchiriate

--functie care returneaza cati clienti au inchiriat cel putin un film cu pret > 30 in anul 2021
go
create function numarClienti()
returns int
as
begin
	declare @cate int
	select @cate = count(*) from (
									select I.id_client 
									from Inchirieri I
									join Filme F on F.id_film = I.id_film
									where F.pret > 30 and an = 2021
									group by I.id_client) as Clienti
	return @cate
end

--print dbo.numarClienti()