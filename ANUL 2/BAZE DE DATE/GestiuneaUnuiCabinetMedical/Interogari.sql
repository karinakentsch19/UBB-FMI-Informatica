-- 1. Toti pacientii minori care au fost consultati de medici din Cluj
select distinct P.nume_p as Nume, P.prenume_p as Prenume
from Pacienti P
join Consultatii C on P.id_pacient = C.id_pacient
join CadreMedicale CM on CM.id_medic = C.id_medic
join CadreMedicaleInstitutie CMI on CM.id_medic = CMI.id_medic
join Institutii I on CMI.id_institutie = I.id_institutie
join Orase O on O.id_oras = I.id_oras and O.nume_o = 'Cluj-Napoca'
where DATEDIFF(YEAR, P.data_nasterii_p, GETDATE()) < 18;

--2. Institutii care au un numar de medici > 1
select I.nume_i as Institutie, count(CMI.id_medic) as NumarMedici
from Institutii I
left join CadreMedicaleInstitutie CMI on I.id_institutie = CMI.id_institutie
group by I.nume_i
having count(CMI.id_medic) > 1
order by NumarMedici desc

--3. Pacientii care si-au facut analiza de un anumit tip
select distinct P.nume_p as Nume, P.prenume_p as Prenume
from Pacienti P
join Consultatii C on P.id_pacient = C.id_pacient
join ConsultatiiAnalize CA on C.id_consultatie = CA.id_consultatie
join Analize A on CA.id_analiza = A.id_analiza
join TipuriAnalize TA on A.id_tip_analiza = TA.id_tip_analiza
where TA.nume_tip_analiza = 'Teste de biochimie';

--4. Specializari care trateaza cel putin o afectiune
select S.nume_s as Specializare, count(A.id_afectiune) as NumarAfectiuni
from Specializari S
left join CadreMedicaleSpecializari CMS on S.id_specializare = CMS.id_specializare
left join Consultatii C on C.id_medic = CMS.id_medic
left join AfectiuniConsultatie AC on C.id_consultatie = AC.id_consultatie
left join Afectiuni A on AC.id_afectiune = A.id_afectiune
group by S.nume_s
having count(A.id_afectiune) >= 1
order by  NumarAfectiuni desc;

--5. Toti pacientii din judetul Cluj care au consultatii mai scumpe de 200 lei
select distinct P.nume_p as Nume, P.prenume_p as Prenume
from Pacienti P
join Consultatii C on C.id_pacient = P.id_pacient
join CadreMedicaleInstitutie CMI on CMI.id_medic = C.id_medic
join Institutii I on I.id_institutie = CMI.id_institutie
join Orase O on O.id_oras = I.id_oras
join Judete J on J.id_judet = O.id_judet and J.nume_j = 'Cluj'
where C.tarif_c > 200;

--6. Lista de specializari si numarul de cadre medicale pentru fiecare care au avut consultatii
select S.nume_s as Specializare, count(distinct CM.id_medic) as NrMedici
from Specializari S
join CadreMedicaleSpecializari CMS on CMS.id_specializare = S.id_specializare
join CadreMedicale CM on CM.id_medic = CMS.id_medic
join Consultatii C on C.id_medic = CM.id_medic
group by S.nume_s

--7. Pacientii de sex feminin care au fost consultati de un medic pediatru
select distinct P.nume_p as Nume, P.prenume_p as Prenume
from Pacienti P
join Consultatii C on P.id_pacient = C.id_pacient
join CadreMedicale CM on CM.id_medic = C.id_medic
join CadreMedicaleSpecializari CMS on CM.id_medic = CMS.id_medic
join Specializari S on S.id_specializare = CMS.id_specializare and S.nume_s = 'Pediatrie'
where  P.CNP / cast (1000000000000 as BIGINT)  in (2, 4, 6);

--8. Pacientii si analizele care sunt in limite
select P.nume_p as Nume, P.prenume_p as Prenume, A.descriere_analiza as NumeAnaliza
from Pacienti P
join Consultatii C on P.id_pacient = C.id_pacient
join ConsultatiiAnalize CA on C.id_consultatie = CA.id_consultatie
join Analize A on CA.id_analiza = A.id_analiza
where CA.valoare_consultatie_analiza between A.valoare_minima and A.valoare_maxima;

--9. Cati pacienti a consultat fiecare cadru medical
select CM.nume_m as Nume, CM.prenume_m as Prenume, count(C.id_pacient) as NumarPacienti
from CadreMedicale CM
left join Consultatii C on CM.id_medic = C.id_medic
group by CM.nume_m, CM.prenume_m

--10. Cate analize a facut fiecare pacient
select P.nume_p as Nume, P.prenume_p as Prenume, count(CA.id_analiza) as NrAnalize
from Pacienti P
left join Consultatii C on P.id_pacient = C.id_pacient
left join ConsultatiiAnalize CA on C.id_consultatie = CA.id_consultatie
group by P.nume_p, P.prenume_p