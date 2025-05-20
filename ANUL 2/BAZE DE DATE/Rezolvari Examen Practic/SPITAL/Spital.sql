create table Departamente
(
	id_departament int primary key identity,
	nume_departament varchar(100),
	non_stop bit
);

create table Doctori
(
	id_doctor int primary key identity,
	nume_doctor varchar(100),
	data_nasterii date,
	id_departament int foreign key references Departamente(id_departament)
);

create table Pacienti
(
	id_pacient int primary key identity,
	nume_pacient varchar(100),
	data_nasterii_pacient date
);

create table DoctoriPacienti
(
	id_doctor int foreign key references Doctori(id_doctor),
	id_pacient int foreign key references Pacienti(id_pacient),
	constraint pk_doctoripacienti primary key(id_doctor, id_pacient)
);

create table Boli
(
	id_boala int primary key identity,
	denumire_boala varchar(100)
);

create table BoliPacienti
(
	id_boala int foreign key references Boli(id_boala),
	id_pacient int foreign key references Pacienti(id_pacient),
	constraint pk_bolipacienti primary key(id_boala, id_pacient)
);

create table Tratamente
(
	id_tratament int primary key identity,
	descriere_tratament varchar(100)
);

create table BoliTratamente
(
	id_boala int foreign key references Boli(id_boala),
	id_tratament int foreign key references Tratamente(id_tratament),
	constraint pk_bolitratamente primary key(id_boala, id_tratament)
);

insert into Departamente(nume_departament, non_stop) values
('departament pediatrie 1', 0),
('departament medicina 2', 1),
('departament spital 3', 0)

insert into Doctori(nume_doctor, data_nasterii, id_departament) values
('Pop Marcel', '1970-12-12', 1),
('Nemes Tania', '2003-09-21', 1),
('Kentsch Karina', '2003-05-19', 1),
('Borodi Victor', '2000-12-03', 1),
('Nistor Alexandra', '2002-10-10', 2),
('Maria Mera', '1999-03-12', 3);

insert into Pacienti(nume_pacient, data_nasterii_pacient) values
('Pop Mirela', '2000-12-12'),
('Mihaela Stanescu', '1978-03-21'),
('Popescu Mircea', '2003-12-01'),
('Verdes Monica', '1960-05-05'),
('Miruna Morar', '2017-09-09'),
('Ioana Poepescu', '2020-12-02');

insert into DoctoriPacienti(id_doctor, id_pacient) values
(1, 6),
(2, 3),
(6, 1),
(6, 2),
(6, 4),
(1, 5),
(2, 5),
(2, 6),
(3, 1),
(4, 4),
(5, 1),
(5, 2);

insert into Boli(denumire_boala) values
('boala1'),
('boala3'),
('boala4'),
('boala5'),
('boala6'),
('boala7'),
('boala8'),
('boala9'),
('boala10');

insert into BoliPacienti(id_pacient, id_boala) values
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(1, 2),
(2, 2),
(3, 2),
(5, 2),
(1, 3),
(3, 6),
(4, 7),
(5, 7),
(6, 7),
(1, 7);

insert into Tratamente(descriere_tratament) values
('A tratament'),
('A noul tratament'),
('Augmentin'),
('Andafol'),
('tratament2'),
('tratament 3'),
('paracetamol');

insert into BoliTratamente(id_boala, id_tratament) values
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 2),
(7, 3),
(8, 3),
(9, 4),
(1, 4),
(2, 4),
(3, 4),
(1, 6);

--interogare sql care sa afiseze toate departamentele a caror denumire contine termenul 'pediatrie'
select *
from Departamente D
where D.nume_departament like '%pediatrie%'

--functie care returneaza cate boli de care sufera mai mult de 3 pacienti exista
go
create or alter function numarBoli()
returns int
as
begin
	declare @numarBoli int
	select @numarBoli = count(*) from ( select BP.id_boala	
										from BoliPacienti BP
										group by BP.id_boala
										having count(*) > 3
										) x
	return @numarBoli
end;

print dbo.numarBoli()


--view care afișează acele tratamente care incep cu litera A si sunt folosite pentru mai mult de 2 boli
go
create or alter view tratamenteA
as
	select T.id_tratament, T.descriere_tratament
	from Tratamente T
	join BoliTratamente BT on T.id_tratament = BT.id_tratament and T.descriere_tratament like 'A%'
	group by T.id_tratament, T.descriere_tratament
	having count(*) > 2

select * from tratamenteA

--view care afiseaza acele departamente disponibilie non-stop care au mai mult de 3 doctori asignati
go 
create or alter view departamenteNonStop
as
	select D.id_departament, D.nume_departament, D.non_stop
	from Departamente D
	join Doctori Doc on D.id_departament = Doc.id_departament
	group by D.id_departament, D.nume_departament, D.non_stop
	having count(*) > 3


select * from departamenteNonStop