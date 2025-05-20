--CERINTA 1

create table TipuriCompetitii
(
	id_tip int primary key identity,
	denumire_tip varchar(100),
	descriere_tip varchar(100)
);

create table Orase
(
	id_oras int primary key identity,
	regiune varchar(100),
	tara varchar(100)
);

create table Competitii
(
	id_competitie int primary key identity,
	denumire_competite varchar(100),
	data_inceput date,
	data_final date,
	id_tip int foreign key references TipuriCompetitii(id_tip),
	id_oras int foreign key references Orase(id_oras)
);

create table Participanti
(
	id_participant int primary key identity,
	nume_p varchar(100),
	prenume_p varchar(100),
	gen varchar(100),
	data_nastere date
);

create table CompetitiiParticipanti
(
	id_participant int foreign key references Participanti(id_participant),
	id_competitie int foreign key references Competitii(id_competitie),
	taxa int,
	loc int,
	constraint pf_CompetitiiParticipanti primary key(id_participant, id_competitie)
);

INSERT INTO Orase(regiune,tara) VALUES
('Ardeal','Romania'),
('Banat','Romania'),
('Moldova','Romania'),
('Bucovina','Romania');

INSERT INTO Participanti(nume_p,prenume_p,gen,data_nastere) VALUES
('Bugnar','Andreea','feminin','2001-12-03'),
('Oltean','Anisia','feminin','2003-03-29'),
('Oarga','Claudia','feminin','2002-12-01'),
('Bidian','Alessia','feminin','2002-02-22');

INSERT INTO TipuriCompetitii(denumire_tip,descriere_tip) VALUES
('tip1','descriere1'),
('tip1','descriere2'),
('tip3','descriere3'),
('tip4','descriere4');

INSERT INTO Competitii(denumire_competite,data_inceput,data_final,id_oras,id_tip) VALUES
('competitie1','2022-11-03','2001-11-07',1,1),
('competitie2','2022-12-04','2001-12-08',2,2),
('competitie3','2023-10-05','2001-10-08',3,3),
('competitie4','2020-09-06','2001-09-09',4,4),
('competitie5','2021-08-07','2001-08-10',1,4);

INSERT INTO CompetitiiParticipanti(id_competitie,id_participant,taxa,loc) VALUES
(2,1,100,1),
(2,2,150,2),
(2,3,100,3),
(2,4,100,4),
(3,1,300,45),
(3,4,300,23),
(4,1,20,5),
(4,2,25,1),
(4,3,60,3),
(4,4,80,2);




--CERINTA 2 creați o procedură stocată care primește un participant,o competiție,o taxă de participare,
	--un loc obținut la final și adaugă participantul competiției. Dacă deja există,se 
	--actualizează taxa de participare și locul obținut la final
go
create or alter procedure addOrUpdateCompetitie 
	@id_participant int,
	@id_competitie int,
	@taxa_participare int,
	@loc_obtinut int
as
begin 
	if (select count(*) from CompetitiiParticipanti where id_participant = @id_participant and id_competitie = @id_competitie) = 0
		insert into CompetitiiParticipanti(id_participant, id_competitie, taxa, loc) values
		(@id_participant, @id_competitie, @taxa_participare, @loc_obtinut);
	else
		update CompetitiiParticipanti
		set
			taxa = @taxa_participare,
			loc = @loc_obtinut
		where
			id_participant = @id_participant and id_competitie = @id_competitie;
end;

--select * from CompetitiiParticipanti
exec addOrUpdateCompetitie 1, 5, 100, 1

--CERINTA 3
--creați un view care afișează denumirile competițiilor la care au fost cei mai mulți participanți

create or alter view competitiiCuNumarMaximParticipanti as
	select C.denumire_competite
	from Competitii C
	join CompetitiiParticipanti CP on C.id_competitie = CP.id_competitie
	group by C.denumire_competite
	having count(CP.id_participant) = (
		select max(numar_participanti)
		from (
			select count(id_participant) as numar_participanti
			from CompetitiiParticipanti
			group by id_competitie
		) as ParticipantiPerCompetitie
	)

select * from competitiiCuNumarMaximParticipanti