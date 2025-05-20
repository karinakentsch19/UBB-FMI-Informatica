create table TipuriDeTren
(
	id_tip int primary key identity,
	descriere varchar(100)
);

create table Trenuri
(
	id_tren int primary key identity,
	nume_tren varchar(100),
	id_tip int foreign key references TipuriDeTren(id_tip),
);

create table Rute
(
	id_ruta int primary key identity,
	nume_ruta varchar(100),
	id_tren int foreign key references Trenuri(id_tren)
);

create table Statie 
(
	id_statie int primary key identity,
	nume_statie varchar(100)
);

create table RuteStatie
(
	id_ruta int foreign key references Rute(id_ruta),
	id_statie int foreign key references Statie(id_statie),
	ora_sosirii time,
	ora_plecarii time,
	constraint pk_RuteStatie primary key(id_ruta, id_statie)
);

insert into TipuriDeTren(descriere) values ('calatori'), ('marfa'), ('de mare viteza')

insert into Trenuri(id_tip, nume_tren) values
(1, 'Locomotiva Thomas'),
(1, 'Trenul Foamei'),
(2, 'CFR-1'),
(2, 'CFR-2'),
(3, 'TGV');

insert into Rute(id_tren, nume_ruta) values
(1, 'Ruta vesela'),
(2, 'Iasi-Timisoara'),
(3, 'Cluj Napoca - Oradea'),
(4, 'Cluj - Suceava'),
(5, 'Cluj - Constanta'),
(1, 'Cluj - Budapesta');

insert into Statie(nume_statie) values
('oradea sud'),
('saratel'),
('Bistrita Nord'),
('Cluj Vest'),
('veresti'),
('mangalia'),
('budapesta nord'),
('timisoara vest'),
('suceava nord');

insert into RuteStatie(id_ruta, id_statie, ora_sosirii, ora_plecarii) values
(1, 1, '6:00', '6:05'),
(2, 4, '5:00', '6:00'),
(3, 9, '10:00', '10:30'),
(4, 7, '12:05',  '12:15'),
(4, 3, '6:00', '6:45'),
(5, 5, '12:12', '12:30'),
(6, 3, '4:00', '4:05'),
(6, 6, '7:00', '7:05');


go
create or alter procedure adauga_traseu 
	@id_ruta int,
	@id_statie int,
	@ora_sosire time, 
	@ora_plecare time
as
begin
	if (exists(select 1 from RuteStatie where id_ruta = @id_ruta and id_statie = @id_statie))
	begin
		update RuteStatie
		set
			ora_plecarii = @ora_plecare,
			ora_sosirii = @ora_sosire
		where
			id_statie = @id_statie and 
			id_ruta = @id_ruta
	end
	else
	begin
		insert into RuteStatie(id_ruta, id_statie, ora_sosirii, ora_plecarii) values
		(@id_ruta, @id_statie, @ora_sosire, @ora_plecare)
	end
end

select * from RuteStatie
exec adauga_traseu 1, 4, '4:00', '4:05'
exec adauga_traseu 1, 2, '15:00', '15:05'
exec adauga_traseu 1, 5, '8:00', '8:05'
exec adauga_traseu 1, 6, '9:00', '9:05'
exec adauga_traseu 1, 7, '10:00', '10:05'
exec adauga_traseu 1, 8, '11:00', '11:05'
exec adauga_traseu 1, 9, '14:00', '14:05'



create or alter view viewRute 
as
	select R.nume_ruta as NumeRuta
	from Rute R
	join RuteStatie RS on R.id_ruta = RS.id_ruta
	group by R.id_ruta, R.nume_ruta
	having count(RS.id_statie) = (select count(*) from Statie)


select * from viewRute

