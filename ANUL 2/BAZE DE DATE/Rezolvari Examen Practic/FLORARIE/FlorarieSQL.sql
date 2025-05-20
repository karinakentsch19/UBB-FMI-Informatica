--cerinta 1
--Scrieți un script SQL care creează un model relațional pentru a reprezenta datele

create table CategoriiDeAranjamenteFlorale
(
	id_categorie int primary key identity,
	nume_categorie varchar(100)
);

create table Florarii
(
	id_florarie int primary key identity,
	nume_florarie varchar(100),
	telefon varchar(100),
	adresa varchar(100)
);


create table AranjamenteFlorale
(
	id_aranjament int primary key identity,
	nume_aranjament varchar(100),
	pret int,
	descriere varchar(100),
	inaltime int,
	id_categorie int foreign key references CategoriiDeAranjamenteFlorale(id_categorie),
	id_florarie int foreign key references Florarii(id_florarie)
);

create table Plante
(
	id_planta int primary key identity,
	nume_planta varchar(100),
	descriere_planta varchar(100)
);


create table AranjamentePlante
(
	id_aranjament int foreign key references AranjamenteFlorale(id_aranjament),
	id_planta int foreign key references Plante(id_planta),
	numar_exemplare int,
	constraint pk_aranjamentePlante primary key(id_aranjament, id_planta)
);

insert into CategoriiDeAranjamenteFlorale(nume_categorie) values
('nunta'), 
('botez'),
('inmormantare'),
('petrecere'),
('absolvire');

insert into Florarii(nume_florarie, telefon, adresa) values
('Magnolia', '0732324567', 'strada Lacramioarelor'),
('La vie en Rose', '0767172132', 'strada Aurica'),
('Masonila', '0783546253', 'strada Lalea'),
('Maison fleur', '0786868473', 'strada Memo'),
('Magique Fleur', '0786899476', 'strada Albastrelelor'),
('Astoria', '0799869976', 'strada Lacramioarelor'),
('Belle Fleur', '0799999966', 'strada Mia');

insert into AranjamenteFlorale(nume_aranjament, pret, descriere, inaltime, id_categorie, id_florarie) values
('Lola', 100, 'aranjament1', 10, 1, 1),
('Special', 200, 'aranjament2', 22, 2, 3),
('Aranjament nou', 100, 'aranjament3', 13, 4, 4),
('New me', 150, 'aranjament4', 15, 4, 5),
('Fantastic', 130, 'aranjament5', 16, 5, 1),
('Minunat', 110, 'aranjament6', 45, 3, 1),
('Buchet de trandafiri', 330, 'aranjament7', 19, 1, 2),
('Lila', 1000, 'aranjament8', 50, 1, 6),
('Milosto', 100, 'aranjament9', 12, 3, 7),
('Greek God', 100, 'aranjament10', 12, 3, 1);

insert into Plante(nume_planta, descriere_planta) values
('trandafir alb', 'descriere1'),
('lalea', 'descriere2'),
('zambila', 'descriere3'),
('trandafir rosu', 'descriere4'),
('trandafir roz', 'descriere5'),
('trandafir galben', 'descriere6'),
('frezi', 'descriere7'),
('craciunita', 'descriere8'),
('liliac', 'descriere9'),
('trandafir mov', 'descriere10'),
('trandafir albastru', 'descriere11');

insert into AranjamentePlante(id_aranjament, id_planta, numar_exemplare) values
(1, 1, 10),
(2, 1, 1),
(3, 2, 12),
(4, 11, 1),
(5, 4, 4),
(6, 5, 5),
(7, 6, 8),
(8, 10, 9),
(9, 1, 10),
(10, 3, 2),
(1, 7, 2),
(1, 8, 11),
(2, 9, 20);


--cerinta 2
--Creați o procedură stocată care primește un aranjament floral, o plantă și un număr de exemplare 
--și adaugă noua plantă aranjamentului floral. 
--Dacă planta a fost deja adăugată aranjamentului floral, se va actualiza numărul de exemplare. 

go
create or alter procedure adaugaPlantaAranjamentuluiFloral
	@id_aranjament int,
	@id_planta int,
	@nr_exemplare int
as
begin
	if (select count(*) from AranjamentePlante where id_aranjament = @id_aranjament and id_planta = @id_planta) = 0
		insert into AranjamentePlante(id_aranjament, id_planta, numar_exemplare) values
		(@id_aranjament, @id_planta, @nr_exemplare)
	else
		UPDATE AranjamentePlante
		SET
			numar_exemplare = @nr_exemplare
		WHERE
			id_aranjament = @id_aranjament and 
			id_planta = @id_planta
end

select * from AranjamentePlante
exec adaugaPlantaAranjamentuluiFloral 10, 1, 2

--cerinta 3
--Creați un view care afișează numele florăriei, numele aranjamentului floral, prețul aranjamentului floral, 
--numărul de exemplare și numele plantei pentru toate florăriile al căror nume nu începe cu litera ‘M’
go
create or alter view dateDespreAranjamenteDinFlorarii
as
	select F.nume_florarie, AF.nume_aranjament, AF.pret, AP.numar_exemplare, P.nume_planta
	from Florarii F
	join AranjamenteFlorale AF on F.id_florarie = AF.id_florarie
	join AranjamentePlante AP on AF.id_aranjament = AP.id_aranjament 
	join Plante P on AP.id_planta = P.id_planta
	where F.nume_florarie not like 'M%'

select * from dateDespreAranjamenteDinFlorarii

go
create or alter view dateDespreAranjamenteDinFlorariiVarianta2
as
	select F.nume_florarie, AF.nume_aranjament, AF.pret, AP.numar_exemplare, P.nume_planta
	from Florarii F
	join AranjamenteFlorale AF on F.id_florarie = AF.id_florarie
	join AranjamentePlante AP on AF.id_aranjament = AP.id_aranjament 
	join Plante P on AP.id_planta = P.id_planta
	group by F.nume_florarie, AF.nume_aranjament, AF.pret, AP.numar_exemplare, P.nume_planta
	having F.nume_florarie not like 'M%'

select * from dateDespreAranjamenteDinFlorariiVarianta2

