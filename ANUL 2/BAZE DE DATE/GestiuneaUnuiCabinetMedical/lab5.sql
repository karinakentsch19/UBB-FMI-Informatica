--constrangeri Pacient
alter table Pacienti
WITH CHECK ADD  CONSTRAINT [CK_Pacienti_nume_p] CHECK  ((Trim([nume_p])<>''))

alter table Pacienti
WITH CHECK ADD  CONSTRAINT [CK_Pacienti_prenume_p] CHECK  ((Trim([prenume_p])<>''))

alter table Pacienti
WITH CHECK ADD  CONSTRAINT [CK_Pacienti_cnp] CHECK  (LEN(CAST(cnp as varchar(100))) = 13)

--constrangeri Consultatii
alter table Consultatii
WITH CHECK ADD  CONSTRAINT [CK_Consultatii_nume_p] CHECK  ((Trim([descriere])<>''))

--constrangeri CadreMedicale
alter table CadreMedicale
WITH CHECK ADD  CONSTRAINT [CK_CadreMedicale_nume_p] CHECK  ((Trim([nume_m])<>''))

alter table CadreMedicale
WITH CHECK ADD  CONSTRAINT [CK_CadreMedicale_prenume_p] CHECK  ((Trim([prenume_m])<>''))


--view Pacienti Consultatii Cadre Medicale = detalii consultatii ale pacientilor cu email de yahoo
--foloseste indexul: ix_Consultatii_id_pacient
create or alter view vw_Consultatii as
	select 
		C.data_c as DataConsultatie,
		C.descriere as DescriereConsultatie,
		C.tarif_c as TarifConsultatie,
		P.nume_p as NumePacient,
		P.prenume_p as PrenumePacient,
		CM.nume_m as NumeMedic,
		CM.prenume_m as PrenumeMedic
	from Pacienti P
	join Consultatii C on C.id_pacient = P.id_pacient
	join CadreMedicale CM on C.id_medic = CM.id_medic
	where P.email_p like '%@yahoo%'

--view Pacienti Consultatii Cadre Medicale = numarul de pacienti consultati de fiecare doctor
--foloseste indecsii: ix_Consultatii_id_medic si ix_Consultatii_nume_medic
create or alter view vw_NumarConsultatiiPerMedic as
	select 
		CM.nume_m as NumeMedic,
		count(P.id_pacient) as NrPacientiConsultati
	from Pacienti P
	join Consultatii C on C.id_pacient = P.id_pacient
	join CadreMedicale CM on C.id_medic = CM.id_medic
	group by CM.nume_m


--index Consultatii
CREATE NONCLUSTERED INDEX ix_Consultatii_id_pacient ON Consultatii
(
	[id_pacient] ASC
)

CREATE NONCLUSTERED INDEX ix_Consultatii_id_medic ON Consultatii
(
	[id_medic] ASC
)
CREATE NONCLUSTERED INDEX ix_Consultatii_nume_medic ON CadreMedicale
(
	[nume_m] ASC
)


--functie pentru validarea unui Pacient
create or alter function fn_validarePacient(
	@cnp bigint, 
	@nume varchar(100), 
	@prenume varchar(100), 
	@email varchar(100)
)
returns bit
as
begin
	declare @result bit
	set @result = 0

	--daca numele e vid sau null => eroare
	if TRIM(ISNULL(@nume, '')) = ''
		set @result = 1
	else if TRIM(ISNULL(@prenume, '')) = ''
		set @result = 1
	else if ISNULL(LEN(CAST(@cnp as varchar(100))), 0) <> 13
		set @result = 1
	else if @email not like '%@%'
		set @result = 1
	
	return @result
end

--functie pentru validarea unei Consultatii
create or alter function fn_validareConsultatie(
	@idPacient int, 
	@idMedic int
)
returns bit
as
begin
	declare @result bit
	set @result = 0

	if (select count(*) from Pacienti where id_pacient = @idPacient) = 0
		set @result = 1
	else if (select count(*) from CadreMedicale where id_medic = @idMedic) = 0
		set @result = 1
	
	return @result
end

--functie pentru validarea unui CadruMedical
create or alter function fn_validareCadruMedical(
	 @codParafa bigint,
	 @nume varchar(100),
	 @prenume varchar(100)
)
returns bit
as
begin
	declare @result bit
	set @result = 0

	if TRIM(ISNULL(@nume, '')) = ''
		set @result = 1
	else if TRIM(ISNULL(@prenume, '')) = ''
		set @result = 1
	else if @codParafa is null
		set @result = 1
	return @result
end





--operatii CRUD tabela Pacienti

--procedura pentru add in tabela Pacienti
go
create or alter procedure stp_AddPacient 
	@cnp bigint,
	@nume varchar(100),
	@prenume varchar(100),
	@nrTel bigint,
	@email varchar(100),
	@dataNasterii date

as
begin
	if dbo.fn_validarePacient(@cnp, @nume, @prenume, @email) = 0
	begin
		insert into Pacienti(CNP, nume_p, prenume_p, nr_tel_p, email_p, data_nasterii_p) values
		(@cnp, @nume, @prenume, @nrTel, @email, @dataNasterii)
	end
	else
		RAISERROR('Pacient invalid',16, 1)
end


--procedura pentru find in tabela Pacienti
go

create or alter procedure stp_FindPacient 
	@idPacient int
as
begin
	if (select count(*) from Pacienti where id_pacient = @idPacient) <> 0
	begin
		select *
		from Pacienti
		where id_pacient = @idPacient
	end
	else
		RAISERROR('Nu exista acest pacient', 16, 1)
end

--procedura pentru getALL in tabela Pacienti
go
create or alter procedure stp_getPacienti 
as
begin
	select *
	from Pacienti
end

--procedura pentru update in tabela Pacienti
go
create or alter procedure stp_UpdatePacient 
	@idPacient int,
	@cnp bigint,
	@nume varchar(100),
	@prenume varchar(100),
	@nrTel bigint,
	@email varchar(100),
	@dataNasterii date
as
begin 
	if dbo.fn_validarePacient(@cnp, @nume, @prenume, @email) = 0 and (select count(*) from Pacienti where id_pacient = @idPacient) <> 0
	begin
		update Pacienti 
		set 
			CNP = @cnp, 
			nume_p = @nume, 
			prenume_p = @prenume, 
			nr_tel_p = @nrTel, 
			email_p = @email, 
			data_nasterii_p = @dataNasterii 
		where 
			id_pacient = @idPacient
	end
	else
		RAISERROR('Date invalide',16, 1)
end


--procedura pentru delete in tabela Pacienti
go
create or alter procedure stp_DeletePacient 
	@idPacient int
as
begin
	if (select count(*) from Pacienti where id_pacient = @idPacient) <> 0
		delete from Pacienti where id_pacient = @idPacient
	else
		RAISERROR('Pacient inexistent',16, 1)
end



--operatii CRUD tabela Consultatii

--procedura pentru add in tabela Consultatii
go
create or alter procedure stp_AddConsultatie
	@idPacient int,
	@idMedic int,
	@data date,
	@tarif money, 
	@descriere varchar(1000)
as
begin	
	if dbo.fn_validareConsultatie(@idPacient, @idMedic) = 0
		insert into Consultatii(id_pacient, id_medic, data_c, tarif_c, descriere) values
		(@idPacient, @idMedic, @data, @tarif, @descriere)
	else
		RAISERROR('Consultatie invalida', 16, 1)
end


--procedura pentru find in tabela Consultatii
go
create or alter procedure stp_FindConsultatie
	@idConsultatie int
as
begin
	if (select count(*) from Consultatii where id_consultatie = @idConsultatie) <> 0
	begin
		select *
		from Consultatii
		where id_consultatie = @idConsultatie
	end
	else
		RAISERROR('Nu exista aceasta consultatie', 16, 1)
end


--procedura pentru update in tabela Consultatii
go
create or alter procedure stp_UpdateConsultatie
	@idConsultatie int,
	@idPacient int,
	@idMedic int, 
	@data date,
	@tarif money,
	@descriere varchar(1000)
as
begin
	if dbo.fn_validareConsultatie(@idPacient, @idMedic) = 0 and (select count(*) from Consultatii where id_consultatie = @idConsultatie) <> 0
		update Consultatii
		set
			id_pacient = @idPacient,
			id_medic = @idMedic,
			data_c = @data,
			tarif_c = @tarif,
			descriere = @descriere
		where id_consultatie = @idConsultatie
	else
		RAISERROR('Consultatie inexistenta', 16, 1)
end


--procedura pentru delete in tabela Consultatii
go
create or alter procedure stp_DeleteConsultatie
	@idConsultatie int
as
begin
	if (select count(*) from Consultatii where id_consultatie = @idConsultatie) <> 0
		delete from Consultatii where id_consultatie = @idConsultatie
	else
		RAISERROR('Consultatie inexistenta',16, 1)
end

--procedura pentru getALL in tabela Consultatii
go
create or alter procedure stp_getConsultatii
as
begin
	select *
	from Consultatii
end


--operatii CRUD tabela CadreMedicale

--procedura pentru add in tabela CadreMedicale
go
create or alter procedure stp_AddCadruMedical
	@codParafa bigint,
	@nume varchar(100),
	@prenume varchar(100),
	@nrTel bigint,
	@email varchar(100)
as
begin
	if dbo.fn_validareCadruMedical(@codParafa, @nume, @prenume) = 0
		insert into CadreMedicale(cod_parafa, nume_m, prenume_m, nr_tel_m, email_m) values
		(@codParafa, @nume, @prenume, @nrTel, @email)
	else
		RAISERROR('Cadru Medical invalid', 16, 1)
end



--procedura pentru find in tabela CadreMedicale
go
create or alter procedure stp_FindCadruMedical
	@idMedic int
as
begin
	if (select count(*) from CadreMedicale where id_medic = @idMedic) <> 0
	begin
		select *
		from CadreMedicale
		where id_medic = @idMedic
	end
	else
		RAISERROR('Nu exista aceast medic', 16, 1)
end



--procedura pentru update in tabela CadreMedicale
go
create or alter procedure stp_UpdateCadruMedical
	@idMedic int,
	@codParafa bigint,
	@nume varchar(100),
	@prenume varchar(100),
	@nrTel bigint,
	@email varchar(100)
as
begin
	if dbo.fn_validareCadruMedical(@codParafa, @nume, @prenume) = 0 and (select count(*) from CadreMedicale where id_medic = @idMedic) <> 0
		update CadreMedicale
		set
			cod_parafa = @codParafa,
			nume_m = @nume,
			prenume_m = @prenume,
			nr_tel_m = @nrTel,
			email_m = @email
		where
			id_medic = @idMedic
	else
		RAISERROR('Date invalide', 16, 1)
end



--procedura pentru delete in tabela CadreMedicale
go
create or alter procedure stp_DeleteCadruMedical
	@idMedical int
as
begin
	if (select count(*) from CadreMedicale where id_medic = @idMedical) <> 0
		delete from CadreMedicale where id_medic = @idMedical
	else
		RAISERROR('Cadru medical inexistenta',16, 1)
end

--procedura pentru getALL in tabela CadreMedicale
go
create or alter procedure stp_getCadreMedicale
as
begin
	select *
	from CadreMedicale
end

select * from CadreMedicale
select * from Consultatii
select * from Pacienti


exec stp_AddCadruMedical 12, 'Sergiu', 'Mircescu', 1764127811, 's@gmail.com'
exec stp_AddConsultatie 1,2,'2023-12-19',100,'consultatie'
exec stp_AddPacient 1234512342131, 'Paulina', 'Petrescu', 163168212, 'paulinap@gmail.com', '2002-12-01'

