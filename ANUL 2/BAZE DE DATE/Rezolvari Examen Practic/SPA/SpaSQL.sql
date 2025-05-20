--cerinta 1
--Scrieți un script SQL care creează un model relațional pentru a reprezenta datele
create table Orase
(
	id_oras int primary key identity,
	nume_oras varchar(100)
);

create table CentreSpa
(
	id_centru int primary key identity,
	nume_centru varchar(100),
	site_web varchar(100),
	id_oras int foreign key references Orase(id_oras)
);

create table ServiciiSpa
(
	id_serviciu int primary key identity,
	nume_serviciu varchar(100),
	descriere varchar(100),
	pret int,
	recomandare varchar(100),
	id_centru int foreign key references CentreSpa(id_centru)
);

create table Clienti
(
	id_client int primary key identity,
	nume_client varchar(100),
	prenume_client varchar(100),
	ocupatie varchar(100)
);

create table ClientiServicii
(
	id_client int foreign key references Clienti(id_client),
	id_serviciu int foreign key references ServiciiSpa(id_serviciu),
	nota int,
	constraint pk_ClientiServicii primary key(id_client, id_serviciu)
);

insert into Orase(nume_oras) values
('Cluj-Napoca'),
('Brasov'),
('Dej'),
('Sibiu'),
('Targu Mures'),
('Timisoara');

insert into CentreSpa(nume_centru, site_web, id_oras) values
('Centrul Regal', 'www.regal.ro', 1),
('Centrul Spa Maria', 'www.centrulmaria.com', 1),
('Centrul Happy Place', 'www.happy.ro', 2),
('Centrul Nou', 'www.noulcentru.com', 3),
('Centrul Welness and Spa', 'www.centrulwelnessandspa.ro', 4),
('Centrul Spa Luxury Goods', 'www.luxurygoods.com', 5);

insert into ServiciiSpa(nume_serviciu, descriere, pret, recomandare, id_centru) values
('masaj cu uleiuri esentiale', 'descriere1', 200, 'recomandare1', 1),
('masaj simplu', 'descriere2', 60, 'recomandare2', 1),
('piscina', null, 200, 'recomandare3', 1),
('sauna', 'descriere4', 200, 'recomandare4', 2),
('sauna', null, 200, 'recomandare5', 3),
('jacuzzi', 'descriere6', 200, 'recomandare6', 4),
('piscina', 'descriere7', 200, 'recomandare7', 5),
('apa termala', null, 200, 'recomandare8', 6),
('apa salina', null, 200, 'recomandare9', 5);



insert into Clienti(nume_client, prenume_client, ocupatie) values
('Karina', 'Kentsch', 'studenta'),
('Tania', 'Nemes', 'studenta'),
('Ionut', 'Pop', 'inginer'),
('Maria', 'Ionescu', 'medic'),
('Maria', 'Popescu', 'cardiolog'),
('Teodora', 'Pop', 'artist');



insert into ClientiServicii(id_client, id_serviciu, nota) values
(1, 10, 10),
(1, 12, 10),
(1, 13, 9),
(2, 18, 5),
(3, 18, 10),
(4, 13, 9),
(5, 16, 10),
(6, 11, 10);


--cerinta 2
--Creați o procedură stocată care primește un serviciu spa, un client și o notă și adaugă noul serviciu spa clientului. 
--Dacă serviciul spa a fost deja adăugat, se va actualiza valoarea notei
go
create or alter procedure adaugaServicuSpaUnuiClient
	@id_client int,
	@id_serviciu int,
	@nota int
as
begin
	if (select count(*) from ClientiServicii where id_client = @id_client and id_serviciu = @id_serviciu) = 0
		insert into ClientiServicii(id_client, id_serviciu, nota) values
		(@id_client, @id_serviciu, @nota)
	else
		UPDATE ClientiServicii
		SET
			nota = @nota
		WHERE
			id_client = @id_client and 
			id_serviciu = @id_serviciu
end

select * from ClientiServicii
exec adaugaServicuSpaUnuiClient 6, 10, 10


--cerinta 3
--Creați o funcție definită de utilizator care afișează numele centrului spa, 
--numele și descrierea serviciului spa, nota și numele clientului pentru toate serviciile spa care au descrierea diferită de NULL
go
create function dateServiciiSpa()
returns table
as
	return 
		select CS.nume_centru, SS.nume_serviciu, SS.descriere, Cserv.nota, C.nume_client
		from CentreSpa CS
		join ServiciiSpa SS on CS.id_centru = SS.id_centru
		join ClientiServicii CServ on SS.id_serviciu = CServ.id_serviciu
		join Clienti C on CServ.id_client = C.id_client
		where SS.descriere is not null

select * from dbo.dateServiciiSpa()