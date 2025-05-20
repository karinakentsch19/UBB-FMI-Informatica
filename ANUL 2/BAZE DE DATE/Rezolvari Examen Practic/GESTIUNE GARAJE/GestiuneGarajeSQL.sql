--cerinta1
create table TipuriDeGaraje
(
	id_tip int primary key identity,
	nume_tip varchar(100),
	descriere varchar(100)
);

create table Garaje
(
	id_garaj int primary key identity,
	denumire_garaj varchar(100),
	strada varchar(100),
	numar varchar(100),
	localitate varchar(100),
	id_tip int foreign key references TipuriDeGaraje(id_tip)
);

create table Clienti
(
	id_client int primary key identity,
	nume_client varchar(100),
	prenume_client varchar(100),
	gen varchar(100),
	vechime int
);

create table Unelte
(
	id_unealta int primary key identity,
	denumire_unealta varchar(100),
	pret float,
	cantitate int,
	id_client int foreign key references Clienti(id_client)
);

create table ClientiGaraje
(
	id_client int foreign key references Clienti(id_client),
	id_garaj int foreign key references Garaje(id_garaj),
	data_activitatii date,
	beneficiul varchar(100),
	constraint pk_ClientiGaraje primary key(id_client, id_garaj)
);

insert into TipuriDeGaraje(nume_tip, descriere) values
('tip1', 'descriere1'),
('tip2', 'descriere2'),
('tip3', 'descriere3'),
('tip4', 'descriere4'),
('tip5', 'descriere5'),
('tip6', 'descriere6');

insert into Garaje(denumire_garaj, strada, numar, localitate, id_tip) values
('Garaj1', 'strada1', 'nr1', 'localitate1', 1),
('Garaj2', 'strada2', 'nr2', 'localitate2', 1),
('Garaj3', 'strada3', 'nr3', 'localitate3', 2),
('Garaj4', 'strada4', 'nr4', 'localitate4', 3),
('Garaj5', 'strada5', 'nr5', 'localitate5', 3),
('Garaj6', 'strada6', 'nr6', 'localitate6', 4),
('Garaj7', 'strada7', 'nr7', 'localitate7', 5),
('Garaj8', 'strada8', 'nr8', 'localitate8', 6),
('Garaj9', 'strada9', 'nr9', 'localitate9', 1);

insert into Clienti(nume_client, prenume_client, gen, vechime) values
('Karina', 'Kenstch', 'feminin', 3),
('Nemes', 'Tania', 'feminin', 2),
('Alex', 'Pop', 'masculin', 1),
('Andrei', 'Dumitru', 'masculin', 4),
('Andra', 'Pop', 'feminin', 5),
('Paul', 'Mircea', 'masculin', 1);

insert into Unelte(denumire_unealta, pret, cantitate, id_client) values
('ciocan', 12, 1, 1),
('tarnacop', 20, 1, 1),
('dalta', 5, 2, 2),
('lopata', 30, 1, 3);

insert into ClientiGaraje(id_client, id_garaj, data_activitatii, beneficiul) values
(1, 1, '2024-01-01', 'beneficiul1'),
(1, 2, '2024-01-02', 'beneficiul2'),
(1, 3, '2024-01-03', 'beneficiul3'),
(1, 4, '2024-01-04', 'beneficiul4'),
(1, 5, '2023-01-01', 'beneficiul5'),
(2, 1, '2023-12-01', 'beneficiul6'),
(2, 2, '2024-01-10', 'beneficiul7'),
(3, 1, '2024-01-01', 'beneficiul8'),
(3, 5, '2023-11-11', 'beneficiul9'),
(3, 6, '2024-01-15', 'beneficiul10'),
(4, 8, '2022-05-01', 'beneficiul11'),
(5, 9, '2023-06-01', 'beneficiul12');


--cerinta2
--procedura care primeste un client, un garaj, o data a activitatii si un beneficiu si adauga garajul clientului
--daca deja exista, se actualizeaza data si beneficiul
go
create or alter procedure adaugaGarajClient
	@id_client int,
	@id_garaj int,
	@data_activitatii date,
	@beneficiu varchar(100)
as
begin
	if (select count(*) from ClientiGaraje where id_client = @id_client and id_garaj = @id_garaj) = 0
		insert into ClientiGaraje(id_client, id_garaj, data_activitatii, beneficiul) values
		(@id_client, @id_garaj, @data_activitatii, @beneficiu)
	else
		update ClientiGaraje
		set
			data_activitatii = @data_activitatii,
			beneficiul = @beneficiu
		where
			id_client = @id_client and
			id_garaj = @id_garaj
end

select * from ClientiGaraje
exec adaugaGarajClient 6, 1, '2024-01-03', 'beneficiul de test2'


--cerinta3
--functie care afiseaza numele clientilor ce au desfasurat activitate in cel putin n garaje, unde n>=1 este parametrul functiei
go
create or alter function numeClienti(@n int)
returns table
as
	return
		select C.id_client, C.nume_client, C.prenume_client
		from Clienti C
		join ClientiGaraje CG on C.id_client = CG.id_client
		group by C.id_client, C.nume_client, C.prenume_client
		having count(id_garaj) >= @n;


select * from dbo.numeClienti(2)