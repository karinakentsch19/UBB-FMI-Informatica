create table Produse
(
	id_produs int primary key identity,
	denumire varchar(100),
	unitate_de_masura varchar(100)
);

create table Clienti
(
	id_client int primary key identity,
	denumire_client varchar(100),
	cod_fiscal varchar(100)
);

create table AgentiDeVanzare
(
	id_agent int primary key identity,
	nume_agent varchar(100),
	prenume_agent varchar(100)
);

create table Facturi
(
	numar_factura int primary key identity,
	data_emiterii date,
	id_client int foreign key references Clienti(id_client),
	id_agent int foreign key references AgentiDeVanzare(id_agent)
);

create table ProduseFacturi
(
	id_produse_facturi int primary key identity,
	id_produs int foreign key references Produse(id_produs),
	numar_factura int foreign key references Facturi(numar_factura),
	numar_ordine int,
	pret float, 
	cantitate int
);


insert into Produse(denumire, unitate_de_masura) values
('Shaorma', 'gram'),
('lapte', 'litru'),
('paine', 'gram'),
('iaurt', 'litru'),
('salam', 'gram'),
('cascaval', 'gram'),
('cartofi', 'kg');

insert into Clienti(denumire_client, cod_fiscal) values
('Client1','codfiscal1'),
('Client2','codfiscal2'),
('Client3','codfiscal3'),
('Client4','codfiscal4'),
('Client5','codfiscal5'),
('Client6','codfiscal6');

insert into AgentiDeVanzare(nume_agent, prenume_agent) values
('Ioana', 'Pop'),
('Karina', 'Kentsch'),
('Simona', 'Popescu'),
('Tania', 'Nemes'),
('Vlad', 'Ionescu');

insert into Facturi(data_emiterii, id_client, id_agent) values
('2023-01-12', 1, 1),
('2023-01-11', 1, 2),
('2023-01-10', 1, 3),
('2023-02-01', 2, 1),
('2023-02-11', 3, 1),
('2023-02-23', 4, 4),
('2024-01-01', 1, 1),
('2024-01-04', 3, 1),
('2023-10-01', 1, 4),
('2023-11-02', 3, 4);

insert into ProduseFacturi(id_produs, numar_factura, numar_ordine, pret, cantitate) values
(1, 1, 1, 30, 12),
(1, 2, 2, 30, 13),
(1, 3, 3, 10, 10),
(2, 1, 3, 10, 20),
(3, 1, 4, 3, 10),
(1, 4, 10, 30, 12),
(2, 5, 5, 12, 5),
(3, 6, 7, 12, 10),
(1, 7, 8, 2, 5),
(7, 8, 9, 12, 2),
(6, 9, 11, 2, 3),
(5, 10, 12, 4, 5);

--procedura care primeste o factura, un produs, numarul de ordine, pretul, cantitatea si adauga noul produs facturii
--daca exista deja produsul la aceea factura se adauga un rand nou cu cantitatea negativata
go
create or alter procedure adaugaProdusFacturi
	@id_produs int,
	@numar_factura int,
	@numar_ordine int,
	@pret float,
	@cantitate int
as
begin
	if (select count(*) from ProduseFacturi where id_produs = @id_produs and numar_factura = @numar_factura) = 0
		insert into ProduseFacturi(id_produs, numar_factura, numar_ordine, pret, cantitate) values
		(@id_produs, @numar_factura, @numar_ordine, @pret, @cantitate)
	else
	begin
		declare @cantitate_curenta int 
		set @cantitate_curenta = (select cantitate from ProduseFacturi where id_produs = @id_produs and numar_factura = @numar_factura)
		declare @cantitate_negativa int
		set @cantitate_negativa = - @cantitate_curenta
		insert into ProduseFacturi(id_produs, numar_factura, numar_ordine, pret, cantitate) values
		(@id_produs, @numar_factura, @numar_ordine, @pret, @cantitate_negativa)
	end
end

--select * from ProduseFacturi
--exec adaugaProdusFacturi 1, 10, 100, 10, 2

--view care afiseaza lista facturilor ce contin produsul 'Shaorma' si a caror valoare este mai mare 300 lei
--lista va afisa pt fiecare factura, denumire clientului, numarul facturii, data emiterii si valoarea totala a facturii
go
create or alter view facturiShaorma
as
	select F.numar_factura, F.data_emiterii, C.denumire_client, sum(PF.pret * PF.cantitate) as ValoareTotala
	from Facturi F
	join Clienti C on f.id_client = C.id_client
	join ProduseFacturi PF on PF.numar_factura = F.numar_factura
	join Produse P on P.id_produs = PF.id_produs and P.denumire like '%Shaorma%'
	group by F.numar_factura, F.data_emiterii, C.denumire_client
	having sum(PF.pret * PF.cantitate) > 300

select * from facturiShaorma

--functie care afiseaza valoarea totala a facturilor grupate pe lunile anului si pe agentii de vanzare pt un anumit an
--ordonate crescator in ordinea lunilor si a numelor agentilor
--coloane Luna, NumeAgent, PrenumeAgent, ValoareTotala
go
create or alter function listaFacturi(@an int)
returns table
as
	return
		select month(F.data_emiterii) as Luna, A.nume_agent, A.prenume_agent, sum(PF.pret * PF.cantitate) as ValoareTotala
		from Facturi F 
		join AgentiDeVanzare A on F.id_agent = A.id_agent
		join ProduseFacturi PF on F.numar_factura = PF.numar_factura
		where year(F.data_emiterii) = @an
		group by month(F.data_emiterii), A.nume_agent, A.prenume_agent

select * from dbo.listaFacturi('2023')