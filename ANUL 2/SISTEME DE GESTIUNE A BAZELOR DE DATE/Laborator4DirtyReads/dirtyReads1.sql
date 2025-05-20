BEGIN TRANSACTION 
	UPDATE Pacienti 
	SET
		nume_p = 'Popescu'
	WHERE
		id_pacient = 2

	WAITFOR DELAY '00:00:09'
ROLLBACK TRANSACTION
