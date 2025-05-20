BEGIN TRANSACTION
	WAITFOR DELAY '00:00:07'
		insert into Tari(nume_t) values ('China')
COMMIT TRANSACTION

--delete from Tari where nume_t = 'China'