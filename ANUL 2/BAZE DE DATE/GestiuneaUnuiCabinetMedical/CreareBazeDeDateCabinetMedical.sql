USE CabinetMedical;

CREATE TABLE Pacienti
	(id_pacient INT IDENTITY,
	CNP BIGINT NOT NULL UNIQUE,
	nume_p VARCHAR(100) NOT NULL,
	prenume_p VARCHAR(100) NOT NULL,
	nr_tel_p BIGINT,
	email_p VARCHAR(100),
	data_nasterii_p DATE NOT NULL,
	CONSTRAINT PK_Pacienti PRIMARY KEY(id_pacient)
	);

CREATE TABLE CadreMedicale 
	(id_medic INT IDENTITY,
	cod_parafa BIGINT NOT NULL UNIQUE,
	nume_m VARCHAR(100) NOT NULL,
	prenume_m VARCHAR(100) NOT NULL,
	nr_tel_m BIGINT,
	email_m VARCHAR(100),
	CONSTRAINT PK_CadreMedicale PRIMARY KEY(id_medic)
	);

CREATE TABLE Tari
	(id_tara INT IDENTITY,
	nume_t VARCHAR(100) NOT NULL,
	CONSTRAINT PK_Tari PRIMARY KEY(id_tara)
	);

CREATE TABLE Judete
	(id_judet INT IDENTITY,
	nume_j VARCHAR(100) NOT NULL,
	id_tara INT,
	CONSTRAINT PK_Judete PRIMARY KEY(id_judet),
	CONSTRAINT FK_Judete_Tari FOREIGN KEY(id_tara) REFERENCES Tari(id_tara)
	);

CREATE TABLE Orase
	(id_oras INT IDENTITY,
	nume_o VARCHAR(100) NOT NULL,
	id_judet INT,
	CONSTRAINT PK_Orase PRIMARY KEY(id_oras),
	CONSTRAINT FK_Orase_Judete FOREIGN KEY(id_judet) REFERENCES Judete(id_judet)
	);

CREATE TABLE Institutii
	(id_institutie INT IDENTITY,
	nume_i VARCHAR(100) NOT NULL,
	strada_i VARCHAR(100) NOT NULL,
	numar_i INT NOT NULL,
	id_oras INT,
	CONSTRAINT PK_Institutii PRIMARY KEY(id_institutie),
	CONSTRAINT FK_Institutii_Orase FOREIGN KEY(id_oras) REFERENCES Orase(id_oras)
	);

CREATE TABLE CadreMecicaleInstitutie
	(id_medic INT,
	id_institutie INT,
	CONSTRAINT FK_CadreMedicaleInstitutii_CadreMedicale FOREIGN KEY(id_medic) REFERENCES CadreMedicale(id_medic),
	CONSTRAINT FK_CadreMedicaleInstitutii_Institutii FOREIGN KEY(id_institutie) REFERENCES Institutii(id_institutie),
	CONSTRAINT PK_CadreMedicaleInstitutii PRIMARY KEY(id_medic, id_institutie)
	);

CREATE TABLE Specializari
	(id_specializare INT IDENTITY,
	nume_s VARCHAR(100) NOT NULL,
	CONSTRAINT PK_Specializari PRIMARY KEY(id_specializare)
	);

CREATE TABLE CadreMedicaleSpecializari
	(id_medic INT,
	id_specializare INT,
	CONSTRAINT FK_CadreMedicaleSpecializari_CadreMedicale FOREIGN KEY(id_medic) REFERENCES CadreMedicale(id_medic),
	CONSTRAINT FK_CadreMedicaleSpecializari_Specializari FOREIGN KEY(id_specializare) REFERENCES Specializari(id_specializare),
	CONSTRAINT PK_CadreMedicaleSpecializari PRIMARY KEY(id_medic, id_specializare)
	);

CREATE TABLE Consultatii
	(id_consultatie INT IDENTITY,
	id_pacient INT,
	id_medic INT,
	data_c DATE NOT NULL,
	tarif_c MONEY NOT NULL,
	descriere VARCHAR(1000),
	CONSTRAINT PK_Consultatii PRIMARY KEY(id_consultatie),
	CONSTRAINT FK_Consultatii_CadreMedicale FOREIGN KEY(id_medic) REFERENCES CadreMedicale(id_medic),
	CONSTRAINT FK_Consultatii_Pacienti FOREIGN KEY(id_pacient) REFERENCES Pacienti(id_pacient)
	);

CREATE TABLE Afectiuni 
	(id_afectiune INT IDENTITY,
	nume_afectiune VARCHAR(100),
	CONSTRAINT PK_Afectiuni PRIMARY KEY(id_afectiune)
	);

CREATE TABLE AfectiuniCronice
	(id_pacient INT,
	id_afectiune INT,
	CONSTRAINT FK_AfectiuniCronice_Pacienti FOREIGN KEY(id_pacient) REFERENCES Pacienti(id_pacient),
	CONSTRAINT FK_AfectiuniCronice_Afectiuni FOREIGN KEY(id_afectiune) REFERENCES Afectiuni(id_afectiune),
	CONSTRAINT PK_AfectiuniCronice PRIMARY KEY(id_pacient, id_afectiune)
	);

CREATE TABLE AfectiuniConsultatie
	(id_afectiune INT,
	id_consultatie INT,
	CONSTRAINT FK_AfectiuniConsultatie_Afectiuni FOREIGN KEY(id_afectiune) REFERENCES Afectiuni(id_afectiune),
	CONSTRAINT FK_AfectiuniConsultatie_Consultatie FOREIGN KEY(id_consultatie) REFERENCES Consultatii(id_consultatie),
	CONSTRAINT FK_AfectiuniConsultatie PRIMARY KEY(id_afectiune, id_consultatie)
	);

CREATE TABLE TipuriAnalize
	(id_tip_analiza INT IDENTITY,
	nume_tip_analiza VARCHAR(100) NOT NULL,
	CONSTRAINT PK_TipuriAnalize PRIMARY KEY(id_tip_analiza)
	);

CREATE TABLE Analize
	(id_analiza INT IDENTITY,
	id_tip_analiza INT,
	descriere_analiza VARCHAR(1000) NOT NULL,
	valoare_minima INT NOT NULL,
	valoare_maxima INT NOT NULL,
	pret_analiza MONEY NOT NULL,
	CONSTRAINT PK_Analize PRIMARY KEY(id_analiza),
	CONSTRAINT FK_Analize_TipAnalize FOREIGN KEY(id_tip_analiza) REFERENCES TipuriAnalize(id_tip_analiza)
	);

CREATE TABLE ConsultatiiAnalize
	(id_consultatie INT,
	id_analiza INT,
	valoare_consultatie_analiza INT,
	CONSTRAINT FK_ConsultatiiAnalize_Consultatii FOREIGN KEY(id_consultatie) REFERENCES Consultatii(id_consultatie),
	CONSTRAINT FK_ConsultatiiAnalize_Analize FOREIGN KEY(id_analiza) REFERENCES Analize(id_analiza),
	CONSTRAINT PK_ConsultatiiAnalize PRIMARY KEY(id_consultatie, id_analiza)
	);