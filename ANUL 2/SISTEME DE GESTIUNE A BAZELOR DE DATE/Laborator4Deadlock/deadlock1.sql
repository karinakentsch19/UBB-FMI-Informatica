--daca nu pun prioritatea se face cand cum 
--cod bun SET DEADLOCK_PRIORITY HIGH

SET TRANSACTION ISOLATION LEVEL SERIALIZABLE 
BEGIN TRANSACTION
	update TipuriAnalize set nume_tip_analiza = 'Test1' where id_tip_analiza = 1
	WAITFOR DELAY '00:00:07'
	update Analize set descriere_analiza = 'descriere1' where id_analiza = 2

COMMIT TRANSACTION

select * from TipuriAnalize
select * from Analize

--update TipuriAnalize set nume_tip_analiza = 'teste de hematologie' where id_tip_analiza = 1
--update Analize set descriere_analiza = 'hemograma cu formula leucocitara' where id_analiza = 2
	