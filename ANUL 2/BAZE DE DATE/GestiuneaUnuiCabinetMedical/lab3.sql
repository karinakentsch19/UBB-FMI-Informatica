--tabela va avea intotdeauna o singura linie
create table Versiuni
(id int primary key,
versiune int not null
);

insert into Versiuni(id, versiune) values
(1,0);

create table UpdateVersiuni
(versiune_veche int not null,
versiune_noua int not null,
nume_procedura varchar(200) not null
);

insert into UpdateVersiuni(versiune_veche, versiune_noua, nume_procedura) values
(0,1,'ModificaTipColoana_orase_nume_o'),
(1,0,'ModificaTipColoana_orase_nume_o_undo'),
(1,2, 'AdaugaConstrangereValImplicita_consultatii_tarif_c'),
(2,1, 'AdaugaConstrangereValImplicita_consultatii_tarif_c_undo'),
(2,3, 'AdaugaTabela_Continente'),
(3,2, 'AdaugaTabela_Continente_undo'),
(3,4, 'AdaugaCamp_Tari_id_continent'),
(4,3,'AdaugaCamp_Tari_id_continent_undo'),
(4,5,'AdaugaConstrangereCheieStraina_Tari_id_continent'),
(5,4,'AdaugaConstrangereCheieStraina_Tari_id_continent_undo');
go

create procedure ModificaTipColoana_orase_nume_o
as
begin
	alter table Orase alter column nume_o varchar(200);
end;
go

create procedure ModificaTipColoana_orase_nume_o_undo
as
begin
	alter table Orase alter column nume_o varchar(100);
end;
go

create procedure AdaugaConstrangereValImplicita_consultatii_tarif_c
as
begin
	alter table Consultatii
	add constraint df_tarif_c default 0 for tarif_c;
end;
go

create procedure AdaugaConstrangereValImplicita_consultatii_tarif_c_undo
as
begin
	alter table Consultatii
	drop constraint df_tarif_c;
end;
go

create procedure AdaugaTabela_Continente
as
begin
	--adaugam doar tabela Continente fara a crea legaturi
	create table Continente
	(id_continent int primary key identity,
	nume_continent varchar(100) not null
	);
end;
go

create procedure AdaugaTabela_Continente_undo
as
begin
	--adaugam doar tabela Continente fara a crea legaturi
	drop table Continente;
end;
go

create procedure AdaugaCamp_Tari_id_continent
as
begin
	alter table Tari
	add id_continent int;
end;
go

create procedure AdaugaCamp_Tari_id_continent_undo
as
begin
	alter table Tari
	drop column id_continent;
end;
go

create procedure AdaugaConstrangereCheieStraina_Tari_id_continent
as
begin
	alter table Tari
	add constraint fk_tari_continente foreign key(id_continent) references Continente(id_continent);
end;
go

create procedure AdaugaConstrangereCheieStraina_Tari_id_continent_undo
as
begin
	alter table Tari
	drop constraint fk_tari_continente;
end;
go


create or alter procedure ModificareVersiune 
	@versiune_noua as int
as
begin
	
	if (@versiune_noua >= 0 and @versiune_noua <= 5)
	begin

		declare @versiune_curenta as int;
		set @versiune_curenta = (select versiune from Versiuni where id = 1);

		declare @nume_procedura as varchar(200);

		declare @index as int;

		if (@versiune_curenta < @versiune_noua)
		begin
			set @index = @versiune_curenta + 1;
			while(@index <= @versiune_noua)
			begin
				set @nume_procedura = (
					select nume_procedura
					from UpdateVersiuni 
					where versiune_veche = @versiune_curenta and versiune_noua = @index);
				exec @nume_procedura;
				update Versiuni set versiune = @index where id = 1;
				set @versiune_curenta = @index;
				set @index = @index + 1;

			end
		end
		else
		begin
			set @index = @versiune_curenta - 1;
			while(@index >= @versiune_noua)
			begin
				set @nume_procedura = (
					select nume_procedura
					from UpdateVersiuni 
					where versiune_veche = @versiune_curenta and versiune_noua = @index);
				exec @nume_procedura;
				update Versiuni set versiune = @index where id = 1;
				set @versiune_curenta = @index;
				set @index = @index - 1;

			end
		end
	end
	else
	begin
		print 'Versiunea este invalida'
	end
end;
go


