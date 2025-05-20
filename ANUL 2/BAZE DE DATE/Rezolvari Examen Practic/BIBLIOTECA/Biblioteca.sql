--cerinta 1
create table Autori
(
	id_autor int primary key identity,
	nume_autor varchar(100),
	prenume_autor varchar(100)
);

create table Domenii
(
	id_domeniu int primary key identity,
	descriere_domeniu varchar(100)
);

create table Carti
(
	id_carte int primary key identity,
	titlu_carte varchar(100),
	id_domeniu int foreign key references Domenii(id_domeniu)
);

create table CartiAutori
(
	id_carte int foreign key references Carti(id_carte),
	id_autor int foreign key references Autori(id_autor),
	constraint pk_CartiAutor primary key(id_carte, id_autor)
);

create table Librarii
(
	id_librarie int primary key identity,
	nume_librarie varchar(100),
	adresa varchar(100)
);

create table LibrariiCarti
(
	id_carte int foreign key references Carti(id_carte),
	id_librarie int foreign key references Librarii(id_librarie),
	data_cumparare date,
	constraint pk_CartiLibrarii primary key(id_carte, id_librarie)
);

insert into Autori(nume_autor,prenume_autor) values
('ion', 'pop'),
('ana', 'pop'),
('maria', 'pop'),
('andra', 'pop'),
('elena', 'pop'),
('tudor', 'pop');

insert into Domenii(descriere_domeniu) values
('literatura'),
('stiintific'),
('SF'),
('aventura'),
('romance');

insert into Carti(titlu_carte, id_domeniu) values
('titlu1', 1),
('titlu2', 1),
('titlu3', 2),
('titlu4', 3),
('titlu5', 3),
('titlu6', 4),
('titlu7', 5);

insert into CartiAutori(id_carte, id_autor) values
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(3, 6),
(4, 5),
(5, 5),
(6, 3),
(7, 4);

insert into Librarii(nume_librarie, adresa) values
('librarie1', 'adresa1'),
('librarie2', 'adresa2'),
('librarie3', 'adresa3'),
('librarie4', 'adresa4'),
('librarie5', 'adresa5'),
('librarie6', 'adresa6');

insert into LibrariiCarti(id_carte, id_librarie, data_cumparare) values 
(7, 1, '2023-12-01'),
(6, 1, '2021-01-01'),
(5, 2, '2023-12-01'),
(4, 3, '2009-03-03'),
(3, 4, '2023-05-19'),
(2, 1, '2021-12-01'),
(1, 6, '2019-12-01');

--cerinta2
go
create or alter procedure stp_asociazaAutorCarte
	@id_carte int, 
	@nume_autor varchar(100),
	@prenume_autor varchar(100)
as
begin
	declare @id_autor int = 0
	if (select count(*) from Autori where nume_autor = @nume_autor and prenume_autor = @prenume_autor) = 0
	begin
		insert into Autori(nume_autor, prenume_autor) values
		(@nume_autor, @prenume_autor);

		set @id_autor = (select top 1 id_autor from Autori where nume_autor = @nume_autor and prenume_autor = @prenume_autor)
		
		insert into CartiAutori(id_carte, id_autor) values
		(@id_carte, @id_autor);
	end
	else
	begin
		set @id_autor = (select top 1 id_autor from Autori where nume_autor = @nume_autor and prenume_autor = @prenume_autor)
		if (select count(*) from CartiAutori where id_carte = @id_carte and id_autor = @id_autor) <> 0
			print 'Autoru e deja asociat cartii!'
		else
			insert into CartiAutori(id_carte, id_autor) values
			(@id_carte, @id_autor);
	end
end;

--cerinta3

create or alter view vw_nrCarti as
	select L.nume_librarie, count(C.id_carte) as NumarCarti
	from Librarii L
	join LibrariiCarti LC on L.id_librarie = LC.id_librarie and LC.data_cumparare >= '2010-01-01'
	join Carti C on LC.id_carte = C.id_carte
	group by L.nume_librarie
	having count(C.id_carte) > 5

select * from vw_nrCarti


--cerinta4

go
create or alter function listaCarti(@nr_autori int)
returns table
as
	return
		select L.nume_librarie, L.adresa, C.titlu_carte, count(*) as NrAutori
		from Carti C
		join CartiAutori CA on C.id_carte = CA.id_carte
		join LibrariiCarti LC on CA.id_carte = LC.id_carte
		join Librarii L on LC.id_librarie = L.id_librarie
		group by C.titlu_carte, L.nume_librarie, L.adresa
		having count(*) = @nr_autori


select * from listaCarti(3)