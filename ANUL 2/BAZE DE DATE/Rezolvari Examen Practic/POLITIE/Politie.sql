create table SectiiDePolitie
(
	id_sectie int primary key identity,
	denumire_sectie varchar(100),
	adresa varchar(100)
);

create table Grade
(
	id_grad int primary key identity,
	denumire_grad varchar(100)
);

create table Sectoare
(
	id_sector int primary key identity,
	denumire_sector varchar(100)
);

create table Politisti
(
	id_politist int primary key identity,
	nume varchar(100),
	prenume varchar(100),
	id_sectie int foreign key references SectiiDePolitie(id_sectie),
	id_grad int foreign key references Grade(id_grad)
);




create table Programari
(
	id_politist int foreign key references Politisti(id_politist),
	id_sector int foreign key references Sectoare(id_sector),
	intrare DateTime,
	iesire DateTime,
	constraint pk_programari primary key(id_politist, id_sector)
);

alter table Programari
add constraint check_hours
check (datediff(hour, intrare, iesire) = 8)


insert into SectiiDePolitie(denumire_sectie, adresa) values
('sectia1', 'adresa1'),
('sectia2', 'adresa2'),
('sectia3', 'adresa3'),
('sectia4', 'adresa4'),
('sectia5', 'adresa5');

insert into Grade(denumire_grad) values
('grad1'),
('grad2'),
('grad3'),
('grad4'),
('grad5'),
('grad6');


insert into Sectoare(denumire_sector) values
('sector1'),
('sector2'),
('sector3'),
('sector4'),
('sector5'),
('sector6');

insert into Politisti(nume, prenume, id_sectie, id_grad) values
('Ion', 'Popescu', 1, 6),
('Matei', 'Marinescu', 1, 3),
('Karina', 'Kentsch', 2, 1),
('Tania', 'Nemes', 2, 2),
('Mihai', 'Pop', 3, 1),
('Andrei', 'Ionescu', 4, 5),
('Ioana', 'Pop', 5, 1);

insert into Programari(id_politist, id_sector, intrare, iesire) values
(1, 3, '2024-01-01 12:00:00', '2024-01-01 20:00:00'),
(1, 2, '2024-01-01 12:00:00', '2024-01-01 20:00:00'),
(1, 4, '2024-01-01 12:00:00', '2024-01-01 20:00:00'),
(2, 6, '2024-05-16 07:00:00', '2024-05-16 15:00:00'),
(2, 5, '2024-05-16 07:00:00', '2024-05-16 15:00:00'),
(4, 3, '2024-01-02 14:00:00', '2024-01-02 22:00:00');

--creati o procedura stocata care primeste un politist, o sectie, data si ora de intrare si iesire si le adauga in lista programarilor
--daca programarea pt sectia, politistul si data de intrare respectiva exista deja, se face update
go
create or alter procedure addOrUpdate
	@id_politist int,
	@id_sector int,
	@intrare datetime,
	@iesire datetime
as
begin
	if (select count(*) from Programari where id_politist = @id_politist and id_sector = @id_sector) = 0
		insert into Programari(id_politist, id_sector, intrare, iesire) values
		(@id_politist, @id_sector, @intrare, @iesire);
	else
		update Programari
		set
			intrare = @intrare,
			iesire = @iesire
		where
			id_politist = @id_politist and id_sector = @id_sector
end

--exec addOrUpdate 4,5,'2023-01-03 11:00:00', '2023-01-03 19:00:00'


--view care afiseaza o lista cu politistii ordonati alfabetic dupa sectie si dupa numele politistului care sa prezinte
--numarul total de ore de munca pentru fiecare politist pentru luna ianuarie a anului curent
go
create or alter view listaPolitisti
as
	select P.nume, P.prenume, SDP.denumire_sectie, sum(datediff(hour, Pr.intrare, Pr.iesire)) as NrOre
	from Politisti P
	join SectiiDePolitie SDP on SDP.id_sectie = P.id_sectie
	join Programari Pr on Pr.id_politist = P.id_politist and (month(Pr.intrare) = 1 or month(Pr.iesire) = 1)
	group by SDP.denumire_sectie, P.nume, P.prenume 

select * from listaPolitisti;

--functie care afiseaza lista politistilor care au mai multe programari la o anumita ora si data
go
create or alter function politistiCuMaiMulteProgramari(@intrare datetime)
returns table
as	
	return
		select P.nume, P.prenume
		from Politisti P
		join Programari Pr on P.id_politist = Pr.id_politist and Pr.intrare = @intrare
		group by P.nume, P.prenume
		having count(*) > 1

--select * from dbo.politistiCuMaiMulteProgramari('2024-01-01 12:00:00')

