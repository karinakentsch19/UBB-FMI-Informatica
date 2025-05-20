SET TRANSACTION ISOLATION LEVEL SERIALIZABLE 
BEGIN TRANSACTION
	update Analize set descriere_analiza = 'descriere2' where id_analiza = 2
	WAITFOR DELAY '00:00:07'
	update TipuriAnalize set nume_tip_analiza = 'Test2' where id_tip_analiza = 1
COMMIT TRANSACTION

select * from TipuriAnalize
select * from Analize