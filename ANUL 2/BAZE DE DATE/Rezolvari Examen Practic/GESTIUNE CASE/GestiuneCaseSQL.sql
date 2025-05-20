--cerinta 1

create table Proprietari
(
	id_proprietar int primary key identity,
	nume_proprietar varchar(100),
	prenume_proprietar varchar(100),
	gen_proprietar varchar(100),
	functie varchar(100)
);

create table Chiriasi
(
	id_chirias int primary key identity,
	nume_chirias varchar(100),
	prenume_chirias varchar(100),
	gen_chirias varchar(100),
	data_nastere date,
	id_proprietar int foreign key references Proprietari(id_proprietar)
);

create table Casa
(
	id_casa int primary key identity,
	strada varchar(100),
	numar varchar(100),
	localitate varchar(100),
	cod_postal int,
	id_proprietar int foreign key references Proprietari(id_proprietar)
);

create table PieseDeMobilier
(
	id_piesa int primary key identity,
	denumire varchar(100),
	descriere varchar(200),
	cantitate int,
	pret float
);

create table PieseCase
(
	id_casa int foreign key references Casa(id_casa),
	id_piesa int foreign key references PieseDeMobilier(id_piesa),
	data_achizitionarii date,
	data_livrarii date
	constraint pk_id primary key(id_casa, id_piesa)
);

drop table PieseCase

insert into Proprietari(nume_proprietar, prenume_proprietar, gen_proprietar, functie) values
('Ionut', 'Popescu', 'masculin', 'inginer'),
('Adrian', 'Ionescu', 'masculin', 'arhitect'), 
('George', 'Neagu', 'masculin', 'contabil'), 
('Alexandru', 'Bogdan', 'masculin', 'economist'), 
('Denis', 'Constantin', 'masculin', 'tehnician'), 
('Rares', 'David', 'masculin', 'povestitor'), 
('Robert', 'Florin', 'masculin', 'analist'), 
('Nicolae', 'Marian', 'masculin', 'comisar'), 
('Eugen', 'Nicolae', 'masculin', 'psiholog'),
('Catalina', 'Nicoara', 'feminin', 'doctor'), 
('Anamaria', 'Negoita', 'feminin', 'designer'), 
('Ecaterina', 'Popa', 'feminin', 'medic'), 
('Alexandra', 'Radulescu', 'feminin', 'secretara'), 
('Diana', 'Ripa', 'feminin', 'profesoara'), 
('Livia', 'Rosu', 'feminin', 'șef sector'), 
('Cristina', 'Simionescu', 'feminin', 'procuror'), 
('Anastasia', 'Voicu', 'feminin', 'violonista');

insert into Chiriasi(nume_chirias, prenume_chirias, gen_chirias, data_nastere, id_proprietar) values
('Karina', 'Kentsch', 'feminin', '2003-05-19', 1),
('Tania', 'Nemes', 'feminin', '2003-09-21', 1),
('Paula', 'Stupar', 'feminin', '2003-05-20', 2),
('Andra', 'Pop', 'feminin', '2013-06-19', 3),
('Cristina', 'Popescu', 'feminin', '2000-07-19', 4),
('Alex', 'Maximescu', 'masculin', '1999-12-03', 5),
('Stefan', 'Roman', 'masculin', '1997-01-01', 6);

insert into Casa(strada, numar, localitate, cod_postal, id_proprietar) values
('Memorandumului', '12A', 'Cluj-Napoca', 142112, 1),
('Panselutelor', '13', 'Dej', 131241, 2),
('Floricica', '1B', 'Cluj-Napoca', 13121, 3),
('Dorobantilor', '12', 'Cluj-Napoca', 24131, 4),
('Lovinescu', '859', 'Ploiesti', 412415, 5);

insert into PieseDeMobilier(denumire, descriere, cantitate, pret) values
('pat', 'pat matrimonial mare', 1, 5000.00),
('dulap', 'dulap pt haine', 1, 555.99),
('fotoliu', 'fotoliu pentru stat', 1, 229.10),
('canapea', 'canapea pentru living', 1, 1000.00),
('birou', 'birou mic', 1, 200.99),
('scaun', 'scaun de birou', 1, 150.00),
('raft', 'set de rafturi pentru carti', 5, 15.99);

insert into PieseCase(id_casa, id_piesa, data_achizitionarii, data_livrarii) values
(1, 1, '2024-01-01', '2024-01-19'),
(1, 2, '2023-12-01', '2023-12-31'),
(1, 3, '2023-05-05', '2023-05-20'),
(2, 1, '2022-01-01', '2022-01-15'),
(2, 2, '2024-01-13', '2024-02-01'),
(2, 6, '2024-01-06', '2024-03-01'),
(3, 1, '2022-06-07', '2022-06-13'),
(4, 1, '2023-11-11', '2023-11-20'),
(5, 5, '2024-01-01', '2024-02-14');

--cerinta 2
--procedura care primeste un apartament, o piesa de mobilier, o data de achizitionare si o data de livrare si adauga piesa apartamentului
--daca deja exista se actualizeaza data de achizitionare si data de livrare
go
create or alter procedure addPiesaDeMobilierInApartament
	@id_casa int,
	@id_piesa int,
	@data_achizitionarii date,
	@data_livrarii date
as
begin
	if (select count(*) from PieseCase where id_piesa = @id_piesa and id_casa = @id_casa) = 0
		insert into PieseCase(id_casa, id_piesa, data_achizitionarii, data_livrarii) values
		(@id_casa, @id_piesa, @data_achizitionarii, @data_livrarii)
	else	
		update PieseCase
		set
			data_achizitionarii = @data_achizitionarii,
			data_livrarii = @data_livrarii
		where
			id_piesa = @id_piesa and 
			id_casa = @id_casa
end

select * from PieseCase
exec addPiesaDeMobilierInApartament 1, 6, '2024-01-01', '2024-01-20'

--cerinta 3
--view care afiseaza denumire pieselor de mobilier ce se gasesc in cel mult 3 case
go
create or alter view denumirePieseMobilier
as
	select PDM.denumire
	from PieseDeMobilier PDM
	join PieseCase PC on PDM.id_piesa = PC.id_piesa
	group by PDM.denumire
	having count(PC.id_casa) <= 3


select * from denumirePieseMobilier