BEGIN TRANSACTION 
	WAITFOR DELAY '00:00:07'
	UPDATE CadreMedicale
	SET
		nume_m = 'Ioana'
	WHERE
		id_medic = 1
COMMIT TRAN

--UPDATE CadreMedicale SET nume_m = 'Mihaela' WHERE id_medic = 1
