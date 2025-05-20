create table Marci
(
	id_marca int primary key identity,
	nume_marca varchar(100)
);

create table Clienti
(
	id_client int primary key identity,
	nume_client varchar(100),
	prenume_client varchar(100)
);

create table Angajati
(
	id_angajat int primary key identity,
	nume_angajat varchar(100),
	prenume_angajat varchar(100)
);

create table Autovehicule
(
	id_autovehicul int primary key identity,
	nr_inmatriculare varchar(100),
	tip_motorizare varchar(100),
	id_marca int foreign key references Marci(id_marca)
);

create table Inchirieri
(
	id_inchiriere int primary key identity,
	id_angajat int foreign key references Angajati(id_angajat),
	id_client int foreign key references Clienti(id_client),
	id_autovehicul int foreign key references Autovehicule(id_autovehicul),
	data_inchirierii datetime,
	data_returnarii datetime,
	
);

insert into Clienti(nume_client, prenume_client) values
('Nume Client 1', 'Prenume Client 1'),
('Nume Client 2', 'Prenume Client 2'),
('Nume Client 3', 'Prenume Client 3');

insert into Angajati(nume_angajat, prenume_angajat) values
('Nume Angajat 1', 'Prenume Angajat 1'),
('Nume Angajat 2', 'Prenume Angajat 2'),
('Nume Angajat 3', 'Prenume Angajat 3');

insert into Marci(nume_marca) values
('Porsche'),
('BMW'),
('Audi');

insert into Autovehicule(nr_inmatriculare, tip_motorizare, id_marca) values
('BV01CCC', 'benzina', 1),
('BV02CCC', 'motorina', 3),
('BV03CCC', 'benzina', 1),
('BV04CCC', 'benzina', 3),
('BV05CCC', 'motorina', 2),
('BV06CCC', 'gaz', 2);

insert into Inchirieri(id_angajat, id_client, id_autovehicul, data_inchirierii, data_returnarii) values
(1, 1, 1, '2022-12-23 13:00:00', '2022-12-30 15:00:00'),
(1, 2, 1, '2022-01-01 12:00:00', '2022-05-03 16:00:00'),
(2, 3, 2, '2021-08-09 11:00:00', '2021-10-01 11:00:00');


--procedura stocata care primeste un angajat, un autovehicul, un clinet, data inchirierii, data returnarii 
--si un parametru 'tip operatie' de tip bit
--daca 'tip operatie' este True atunci se insereaza un rand in Inchirieri, altfel se actualizeaza data inchirierii si a returnarii
go
create or alter procedure addOrUpdate 
	@id_angajat int,
	@id_client int,
	@id_autovehicul int,
	@data_inchirierii datetime,
	@data_returnarii datetime,
	@tip_operatie bit
as
begin
	if @tip_operatie = 'TRUE'
		insert into Inchirieri(id_angajat, id_client, id_autovehicul, data_inchirierii, data_returnarii) values
		(@id_angajat, @id_client, @id_autovehicul, @data_inchirierii, @data_returnarii);
	else
		update Inchirieri
		set
			data_inchirierii = @data_inchirierii,
			data_returnarii = @data_returnarii
		where
			id_angajat = @id_angajat and id_client = @id_client and id_autovehicul = @id_autovehicul;
end;

--select * from Inchirieri
--exec addOrUpdate 1, 2, 1, '2022-12-23 13:00:00', '2022-12-29 12:00:00', 1
exec addOrUpdate 1, 2, 5, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'
exec addOrUpdate 1, 3, 5, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'
exec addOrUpdate 1, 3, 6, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'
exec addOrUpdate 1, 1, 3, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'

exec addOrUpdate 2, 2, 5, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'
exec addOrUpdate 2, 3, 5, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'
exec addOrUpdate 2, 3, 6, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'
exec addOrUpdate 2, 1, 3, '2023-01-12 14:00:00', '2023-01-18 18:00:00', 'TRUE'

exec addOrUpdate 2, 1, 3, '2023-01-18 14:00:00', '2023-01-19 18:00:00', 'TRUE'
exec addOrUpdate 3, 2, 1, '2023-12-18 14:00:00', '2023-12-19 18:00:00', 'FALSE'

--view ce afiseaza o lista a angajatilor care au inchiriat in luna curenta cel putin un autovehicul
--ce apartine unei marci date
--angajatii ordonati alfabetic si va fi afisat numarul total de inchirieri din luna curenta pt fiecare angajat
go
create or alter view listaAngajati
as
	select A.nume_angajat, A.prenume_angajat, count(AV.id_marca) as Numar
	from Angajati A
	join Inchirieri I on A.id_angajat = I.id_angajat
	join Autovehicule AV on I.id_autovehicul = AV.id_autovehicul
	join Marci M on AV.id_marca = M.id_marca
	group by M.nume_marca, I.id_angajat, A.nume_angajat, A.prenume_angajat, I.data_inchirierii
	having M.nume_marca = 'BMV'and datepart(month, I.data_inchirierii) = DATEPART(month, getdate())

select * from listaAngajati

--functie care returneaza o lista a autovehiculelor(numar, marca, tip motorizare) libere (neinchiriate) pt o data si ora
--coloanele listei sunt : Numar autovehicul, Marca si Tip motorizare
go
create or alter function masiniLibere(@data_ora datetime)
returns table
as
	return
		select AV.nr_inmatriculare, M.id_marca, AV.tip_motorizare 
		from Autovehicule AV
		inner join Marci M on M.id_marca = AV.id_marca
		left join	(
					select id_autovehicul 
					from Inchirieri
					where data_inchirierii <= '2023-01-18 14:00:00' and data_returnarii >= '2023-01-18 14:00:00'
					group by id_autovehicul
					)I
					on I.id_autovehicul = AV.id_autovehicul
		where I.id_autovehicul is NULL
		
select * from dbo.masiniLibere('2023-01-18 14:00:00')