create table Specializari
(
	id_specializare int primary key identity,
	denumire_specializare varchar(100)
);

create table Medici
(
	id_medic int primary key identity,
	nume_medic varchar(100),
	prenume_medic varchar(100),
	id_specializare int foreign key references Specializari(id_specializare)
);

create table Pacienti
(
	id_pacient int primary key identity,
	nume_pacient varchar(100),
	prenume_pacient varchar(100),
	adresa varchar(100)
);

create table Diagnostice
(
	id_diagnostic int primary key identity,
	denumire_diagnostic varchar(100),
	descriere varchar(100)
);

create table ConsultatiiProgramari 
(
	id_medic int foreign key references Medici(id_medic),
	id_pacient int foreign key references Pacienti(id_pacient),
	id_diagnostic int foreign key references Diagnostice(id_diagnostic),
	data_consultarii date,
	ora_consultarii time,
	observatii varchar(100),
	constraint pk_consultatii primary key(id_medic, id_pacient, data_consultarii)
);


insert into Specializari(denumire_specializare) values
('specializare1'),
('specializare2'),
('specializare3'),
('specializare4');

insert into Medici(nume_medic, prenume_medic, id_specializare) values
('Ionut', 'Pop', 1),
('Tania', 'Nemes', 1),
('Karina', 'Kentsch', 2),
('Maria', 'Marinescu', 3),
('George', 'Popescu', 3);

insert into Pacienti(nume_pacient, prenume_pacient, adresa) values
('Paula', 'Stupar', 'adresa1'),
('Ioana','Pop','adresa2'),
('Maria','Mera','adresa3'),
('Alex','Ionescu','adresa4'),
('Andrei','Ion','adresa5'),
('Paula','Popita','adresa6'),
('Paul','Ples','adresa7'),
('Matei','Mitret','adresa8'),
('Diana','Lonescu','adresa9'),
('Denisa','Lungescu','adresa10'),
('Viviana','Jichisan','adresa11'),
('Raluca','Duta','adresa12');

insert into Diagnostice(denumire_diagnostic, descriere) values
('diagnostic1', 'descriere1'),
('diagnostic2', 'descriere2'),
('diagnostic3', 'descriere3'),
('diagnostic4', 'descriere4'),
('diagnostic5', 'descriere5'),
('diagnostic6', 'descriere6'),
('diagnostic7', 'descriere7'),
('diagnostic8', 'descriere8');

insert into ConsultatiiProgramari(id_medic, id_pacient, id_diagnostic, data_consultarii, ora_consultarii, observatii) values
(1, 1, 1, '2024-01-01', '12:00:00', 'obs'),
(1, 2, 1, '2024-01-02', '12:00:00', 'obs'),
(1, 3, 1, '2024-01-03', '13:00:00', 'obs'),
(1, 4, 1, '2024-01-04', '12:00:00', 'obs'),
(1, 5, 1, '2024-01-05', '12:00:00', 'obs'),
(1, 6, 1, '2024-01-06', '12:00:00', 'obs'),
(1, 7, 1, '2024-01-07', '12:00:00', 'obs'),
(1, 8, 1, '2024-01-08', '12:00:00', 'obs'),
(1, 9, 1, '2024-01-09', '12:00:00', 'obs'),
(1, 10, 1, '2024-01-10', '12:00:00', 'obs'),
(1, 11, 1, '2024-01-11', '12:00:00', 'obs'),
(1, 12, 1, '2024-01-12', '12:00:00', 'obs'),
(1, 1, 2, '2024-01-13', '12:00:00', 'obs'),
(1, 2, 2, '2024-01-14', '12:00:00', 'obs'),
(1, 3, 2, '2024-01-15', '12:00:00', 'obs'),
(1, 4, 2, '2024-01-16', '12:00:00', 'obs'),
(1, 5, 2, '2024-01-17', '12:00:00', 'obs'),
(1, 6, 2, '2024-01-18', '12:00:00', 'obs'),
(1, 7, 2, '2024-01-19', '12:00:00', 'obs'),
(1, 8, 2, '2024-01-20', '12:00:00', 'obs'),
(1, 9, 2, '2024-01-21', '12:00:00', 'obs'),
(2, 1, 1, '2024-01-01', '13:00:00', 'obs'),
(2, 2, 1, '2024-01-02', '13:00:00', 'obs'),
(2, 3, 1, '2024-01-03', '14:00:00', 'obs'),
(2, 4, 1, '2024-01-04', '13:00:00', 'obs'),
(2, 5, 1, '2024-01-05', '13:00:00', 'obs'),
(2, 6, 1, '2024-01-06', '13:00:00', 'obs'),
(2, 7, 1, '2024-01-07', '13:00:00', 'obs'),
(2, 8, 1, '2024-01-08', '13:00:00', 'obs'),
(2, 9, 1, '2024-01-09', '13:00:00', 'obs'),
(2, 10, 1, '2024-01-10', '13:00:00', 'obs'),
(2, 11, 1, '2024-01-11', '13:00:00', 'obs'),
(2, 12, 1, '2024-01-12', '13:00:00', 'obs'),
(2, 1, 2, '2024-01-13', '13:00:00', 'obs'),
(2, 2, 2, '2024-01-14', '13:00:00', 'obs'),
(2, 3, 2, '2024-01-15', '13:00:00', 'obs'),
(2, 4, 2, '2024-01-16', '13:00:00', 'obs'),
(2, 5, 2, '2024-01-17', '13:00:00', 'obs'),
(2, 6, 2, '2024-01-18', '13:00:00', 'obs'),
(2, 7, 2, '2024-01-19', '13:00:00', 'obs'),
(2, 8, 2, '2024-01-20', '13:00:00', 'obs'),
(2, 9, 2, '2024-01-21', '13:00:00', 'obs'),
(3, 1, 1, '2024-02-12', '12:00:00', 'obs'),
(3, 2, 2, '2024-02-12', '12:00:00', 'obs'),
(3, 3, 3, '2024-02-12', '12:00:00', 'obs'),
(4, 6, 1, '2024-03-12', '12:00:00', 'obs'),
(4, 7, 1,  '2024-03-12', '12:00:00', 'obs'),
(4, 8, 2, '2024-03-12', '12:00:00', 'obs'),
(5, 1, 3, '2024-12-12', '18:00:00', 'obs');


--procedura care primeste un pacient, un medic, un diagnostic, observatii si data si adauga o noua programare in baza de date
--daca pacient+medic+data exista, se face update la diagnostic, observatii si ora
go
create or alter procedure addOrUpdate
	@id_pacient int,
	@id_medic int,
	@id_diagnostic int,
	@observatii varchar(100),
	@data date,
	@ora time
as
begin
	if (select count(*) from ConsultatiiProgramari where id_pacient = @id_pacient and id_medic = @id_medic and data_consultarii = @data) = 0
		insert into ConsultatiiProgramari(id_pacient, id_medic, id_diagnostic, data_consultarii, ora_consultarii, observatii) values
		(@id_pacient, @id_medic, @id_diagnostic, @data, @ora, @observatii)
	else
		update ConsultatiiProgramari
		set
			id_diagnostic = @id_diagnostic,
			ora_consultarii = @ora,
			observatii = @observatii
		where
			id_pacient = @id_pacient and id_medic = @id_medic and data_consultarii = @data
end

--select * from ConsultatiiProgramari
--exec addOrUpdate 12, 5, 1, 'test1', '2024-08-09', '12:10:00'

--view care afiseaza numele si prenumele medicilor care au avut un numar mai mare de 20 de consultatii in luna curenta
create or alter view mediciLunaCurenta
as
	select M.nume_medic, M.prenume_medic
	from Medici M 
	join ConsultatiiProgramari CP on M.id_medic = CP.id_medic and month(CP.data_consultarii) = month(GETDATE())
	group by M.nume_medic, M.prenume_medic
	having count(*) > 20


select * from mediciLunaCurenta
order by nume_medic, prenume_medic

--functie care afiseaza medicii care au mai mult de o consultatie intr-o zi si la o ora data
go
create or alter function mediciiConsultatii(@data date, @ora time)
returns table
as
	return 
		select M.id_medic, M.nume_medic, M.prenume_medic
		from Medici M
		join ConsultatiiProgramari CP on M.id_medic = CP.id_medic and CP.data_consultarii = @data and CP.ora_consultarii = @ora
		group by M.id_medic, M.nume_medic, M.prenume_medic
		having count(*) > 1

select * from dbo.mediciiConsultatii('2024-03-12', '12:00:00')
