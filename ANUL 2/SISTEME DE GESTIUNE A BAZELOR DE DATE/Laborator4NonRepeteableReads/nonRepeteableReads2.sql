--cod rau: READ COMMITTED
--cod bun: REPEATABLE READ

SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
	select * from CadreMedicale
	WAITFOR DELAY '00:00:10'
	select * from CadreMedicale
COMMIT TRAN

select * from CadreMedicale