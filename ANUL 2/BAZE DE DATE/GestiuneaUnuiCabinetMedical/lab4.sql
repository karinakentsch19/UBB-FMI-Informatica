CREATE TABLE PacientiFake
	(id_pacient_f INT,
	CNP_f BIGINT NOT NULL,
	nume_p_f VARCHAR(100) NOT NULL,
	prenume_p_f VARCHAR(100) NOT NULL,
	nr_tel_p_f BIGINT,
	email_p_f VARCHAR(100),
	data_nasterii_p_f DATE NOT NULL,
	CONSTRAINT PK_Pacienti_f PRIMARY KEY(id_pacient_f)
	);



CREATE TABLE ConsultatiiFake
	(id_consultatie_f INT,
	id_pacient_F INT,
	id_medic_f INT,
	data_c_f DATE NOT NULL,
	tarif_c_f MONEY NOT NULL,
	descriere_f VARCHAR(1000),
	CONSTRAINT PK_Consultatii_f PRIMARY KEY(id_consultatie_f),
	CONSTRAINT FK_Consultatii_CadreMedicale_f FOREIGN KEY(id_medic_f) REFERENCES CadreMedicale(id_medic),
	CONSTRAINT FK_Consultatii_Pacienti_f FOREIGN KEY(id_pacient_f) REFERENCES PacientiFake(id_pacient_f)
	);




CREATE TABLE ConsultatiiAnalizeFake
	(id_consultatie_f INT,
	id_analiza_f INT,
	valoare_consultatie_analiza_f INT,
	CONSTRAINT FK_ConsultatiiAnalize_Consultatii_f FOREIGN KEY(id_consultatie_f) REFERENCES ConsultatiiFake(id_consultatie_f),
	CONSTRAINT FK_ConsultatiiAnalize_Analize_f FOREIGN KEY(id_analiza_f) REFERENCES Analize(id_analiza),
	CONSTRAINT PK_ConsultatiiAnalize_f PRIMARY KEY(id_consultatie_f, id_analiza_f)
	);




--creare view in care se selecteaza tot din tabela Pacienti
create or alter view vwPacienti as
	select * from PacientiFake;

select * from vwPacienti;

--creare view in care se selecteaza descrierea consultatiei si valoarea analizei verificate in acea consultatie
create or alter view vwValoareAnalizaConsultatii as
	select C.id_consultatie_f, C.descriere_f,  CA.valoare_consultatie_analiza_f
	from ConsultatiiFake C
	join ConsultatiiAnalizeFake CA on C.id_consultatie_f = CA.id_consultatie_f;

select * from vwValoareAnalizaConsultatii


--creare view care afiseaza numarul de consultatii al fiecarui pacient
create or alter view vwNumarConsultatiiPerPacient as
	select P.id_pacient_f ,P.nume_p_f, P.prenume_p_f,count(*) as NrConsultatii
	from PacientiFake P
	join ConsultatiiFake C on P.id_pacient_f = C.id_pacient_f
	group by P.nume_p_f, P.prenume_p_f, P.id_pacient_f;

select * from vwNumarConsultatiiPerPacient

--procedura stocata pentru inserarea unui Pacient
go
create or alter procedure stpTestInsertPacienti as
begin
	declare @idCurent bigint;
	set @idCurent = (select max(id_pacient_f) from PacientiFake);
	if (@idCurent is null)
		set @idCurent = 0
	set @idCurent = @idCurent + 1

	insert into PacientiFake(id_pacient_f,CNP_f, nume_p_f, prenume_p_f, nr_tel_p_f, email_p_f, data_nasterii_p_f) values
	(@idCurent,'0612423421452', 'numeTest', 'prenumeTest', '0720777799', 'numeprenume@gmail.com', '2023-12-02');
end

--procedura stocata pentru inserarea unei consultatii
go
create or alter procedure stpTestInsertConsultatii as
begin
	declare @idCurent bigint;
	set @idCurent = (select max(id_consultatie_f) from ConsultatiiFake);
	if (@idCurent is null)
		set @idCurent = 0
	set @idCurent = @idCurent + 1

	insert into ConsultatiiFake(id_consultatie_f, id_pacient_F, id_medic_f, data_c_f, tarif_c_f, descriere_f) values
	(@idCurent,2, 1, '2023-11-25', 100, 'test consultatie');
end


--procedura stocata pentru inserarea in tabela de legatura ConsultatiiAnalize
--se insereaza pentru consultatia de test
go 
create or alter procedure stpTestInsertConsultatiiAnalize as
begin
	declare @id int;
	set @id = (select top 1 id_consultatie_f from ConsultatiiFake where descriere_f = 'test consultatie');

	insert into ConsultatiiAnalizeFake(id_consultatie_f, id_analiza_f, valoare_consultatie_analiza_f) values
	(@id, 2, 17);
end


--procedura stocata pentru stergerea din tabela Pacienti
go
create or alter procedure stpTestDeletePacienti as
begin
	delete from PacientiFake; 
end


--procedura stocata pentru stergerea din tabela Consultatii
go
create or alter procedure stpTestDeleteConsultatii as
begin
	delete from ConsultatiiFake;
end

--procedura stocata pentru stergerea din tabela ConsultatiiAnalize
go 
create or alter procedure stpTestDeleteConsultatiiAnalize as
begin
	delete from ConsultatiiAnalizeFake;
end


--procedura stocata care apeleaza vwPacienti
go
create or alter procedure stpTestViewvwPacienti as
begin
	select * 
	from vwPacienti;
end

--procedura stocata care apeleaza vwValoareAnalizaConsultatii
go
create or alter procedure stpTestViewvwValoareAnalizaConsultatii as
begin
	select * 
	from vwValoareAnalizaConsultatii;
end

--procedura stocata care apeleaza vwNumarConsultatiiPerPacient
go 
create or alter procedure stpTestViewvwNumarConsultatiiPerPacient as
begin
	select * 
	from vwNumarConsultatiiPerPacient;
end




--procedura stocata pentru testare
go
create or alter procedure stpExecuteTest
	@test_id int
as
begin
	declare @testDescription varchar(50);
	declare @noOfRows int, @tableName varchar(100), @tableID int, @viewName varchar(100), @viewID int;
	declare @startTest datetime, @endTest datetime, @startTestGlobal datetime, @endTestGlobal datetime;
	declare @dynamicString nvarchar(1000);
	declare @testRunID int;
	declare @i int;

	set @testDescription = (select Name from Tests where TestID = @test_id);

	set @startTestGlobal = GETDATE();
	insert into TestRuns (Description, StartAt, EndAt)
		values (@testDescription, @startTestGlobal, null);
	set @testRunID = @@IDENTITY; -- last identity value inserted

	declare cursorTabeleDelete cursor fast_forward for
		select TT.noOFRows, T.Name, T.TableID
		from TestTables TT
		join Tables T on TT.TableID = T.TableID
		where TT.TestID = @test_id
		order by TT.Position;

	open cursorTabeleDelete;
	fetch next from cursorTabeleDelete into @noOfRows, @tableName, @tableID;

	while @@FETCH_STATUS = 0
	begin	
		set @startTest = GETDATE();

		set @dynamicString = 'exec stpTestDelete' + @tableName;
	
		exec sp_executesql @dynamicString;
	
		set @endTest = GETDATE();
	
		--insert into TestRunTables (TestRunID, TableID, StartAt, EndAt)
		--	values (@testRunID, @tableID, @startTest, @endTest);
	
		fetch next from cursorTabeleDelete into @noOfRows, @tableName, @tableID;
	end

	close cursorTabeleDelete;
	deallocate cursorTabeleDelete;

	declare cursorTabeleInsert cursor fast_forward for
		select TT.noOFRows, T.Name, T.TableID
		from TestTables TT
		join Tables T on TT.TableID = T.TableID
		where TT.TestID = @test_id
		order by TT.Position desc;

	open cursorTabeleInsert;
	fetch next from cursorTabeleInsert into @noOfRows, @tableName, @tableID;

	while @@FETCH_STATUS = 0
	begin	
		set @startTest = GETDATE();

		set @dynamicString = 'exec stpTestInsert' + @tableName;
	
		set @i = 1;
		while @i <= @noOfRows
		begin
			exec sp_executesql @dynamicString;
			set @i = @i + 1;
		end;
	
		set @endTest = GETDATE();
	
		insert into TestRunTables (TestRunID, TableID, StartAt, EndAt)
			values (@testRunID, @tableID, @startTest, @endTest);
	
		fetch next from cursorTabeleInsert into @noOfRows, @tableName, @tableID;
	end

	close cursorTabeleInsert;
	deallocate cursorTabeleInsert;

	declare cursorTabeleViews cursor fast_forward for
		select V.Name, V.ViewID
		from TestViews TV
		join Views V on TV.ViewID = V.ViewID
		where TV.TestID = @test_id;

	open cursorTabeleViews;
	fetch next from cursorTabeleViews into @viewName, @viewID;

	while @@FETCH_STATUS = 0
	begin	
		set @startTest = GETDATE();

		set @dynamicString = 'exec stpTestView' + @viewName;
	
		exec sp_executesql @dynamicString;
		
	
		set @endTest = GETDATE();
	
		insert into TestRunViews (TestRunID, ViewID, StartAt, EndAt)
			values (@testRunID, @viewID, @startTest, @endTest);
	
		fetch next from cursorTabeleViews into @viewName, @viewID;
	end

	close cursorTabeleViews;
	deallocate cursorTabeleViews;


	set @endTestGlobal= GETDATE();
	update TestRuns set EndAt = @endTestGlobal where TestRunID = @testRunID;
end



go
create or alter procedure stpDeleteTestRuns
	@stanga bigint, @dreapta bigint
as
begin 
	delete from TestRuns where TestRunID between @stanga and @dreapta

	delete from TestRunTables where TestRunID between @stanga and @dreapta

	delete from TestRunViews where TestRunID between @stanga and @dreapta

end;




go
create procedure stpPopuleazaTabele 
as
begin
	insert into PacientiFake(id_pacient_f, CNP_f, nume_p_f, prenume_p_f, nr_tel_p_f, email_p_f, data_nasterii_p_f)
	select P.id_pacient, P.cnp, P.nume_p, P.prenume_p, P.nr_tel_p, P.email_p, P.data_nasterii_p
	from Pacienti P;

	insert into ConsultatiiFake(id_consultatie_f, id_pacient_F,id_medic_f, data_c_f, tarif_c_f, descriere_f)
	select C.id_consultatie, C.id_pacient, C.id_medic, C.data_c, C.tarif_c, C.descriere
	from Consultatii C;
	
	insert into ConsultatiiAnalizeFake
	select * from ConsultatiiAnalize;
end;



exec stpExecuteTest 2
select * from TestRuns
select * from TestRunTables
select * from TestRunViews
select * from tests

exec stpDeleteTestRuns 1025, 1033;
exec stpPopuleazaTabele;

select * from PacientiFake
select * from ConsultatiiFake
select * from ConsultatiiAnalizeFake


delete from ConsultatiiAnalizeFake
delete from ConsultatiiFake;
delete from PacientiFake;



exec stpTestViewvwValoareAnalizaConsultatii;