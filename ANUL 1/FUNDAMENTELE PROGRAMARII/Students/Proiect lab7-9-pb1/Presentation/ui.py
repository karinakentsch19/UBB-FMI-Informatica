from Exceptions.erori import RepoError, ValidationError

class UI:

    def __init__(self, __student_service, __disciplina_service, __nota_service):
        self.__lista_studenti = __student_service
        self.__lista_discipline = __disciplina_service
        self.__lista_note = __nota_service

    def ruleaza_ui(self):
        comenzi ={
            "adauga_student": self.adauga_student_ui,
            "adauga_student_random": self.adauga_student_random_ui,
            "sterge_student": self.sterge_student_dupa_id_ui,
            "modifica_student": self.modifica_nume_student_ui,
            "cauta_student": self.cautare_student_dupa_id_ui,
            "afisare_studenti": self.afisare_studenti,
            "adauga_disciplina": self.adauga_disciplina_ui,
            "adauga_disciplina_random": self.adauga_disciplina_random_ui,
            "sterge_disciplina": self.sterge_disciplina_dupa_id_ui,
            "modifica_disciplina": self.modifica_disciplina_ui,
            "cauta_disciplina": self.cauta_disciplina_dupa_id_ui,
            "afisare_discipline": self.afisare_discipline,
            "asignare_nota": self.asignare_nota_ui,
            "asignare_note_random": self.asignare_note_random,
            "afisare_note": self.afisare_note_ui,
            "stud_ord_nota_disciplina": self.lista_studenti_note_disciplina_data_ordonata_ui,
            "top_studenti": self.top_20_la_suta_studenti_dupa_medie_ui,
            "studenti_medie_mai_mare_medie_data": self.studenti_medie_mai_mare_medie_data_ui

        }
        while True:
            comanda = input(">>>")
            comanda = comanda.strip()
            if comanda == "":
                continue
            if comanda == "exit":
                break
            if comanda == "help":
                self.help()
                continue
            parti = comanda.split()
            nume_comanda = parti[0]
            parametri = parti[1:]
            if nume_comanda in comenzi:
                comenzi[nume_comanda](parametri)
            else:
                print("Comanda invalida!\n")

    def help(self):
        print("COMENZI:")
        print("adauga_student                      + idStudent + nume                                 - adauga un student")
        print("adauga_student_random               + nr_studenti_de_adaugat                           - adauga studenti random")
        print("sterge_student                      + idStudent                                        - sterge un student")
        print("modifica_student                    + idStudent + nume_nou                             - modifica numele unui student")
        print("cauta_student                       + idStudent                                        - cauta un student dupa id")
        print("afisare_studenti                                                                       - afiseaza toti studentii")
        print("adauga_disciplina                   + idDisciplina + nume + profesor                   - adauga o disciplina")
        print("adauga_disciplina_random            + nr_discipine_de_adaugat                          - adauga discipline random")
        print("sterge_disciplina                   + idDisciplina                                     - sterge o disciplina")
        print("modifica_disciplina                 + idDisciplina + nume_nou + profesor_nou           - modifica numele si profesorul unei discipline")
        print("cauta_disciplina                    + idDisciplina                                     - cauta o disciplina")
        print("afisare_discipline                                                                     - afiseaza toate disciplinele")
        print("asignare_nota                       + valoare_nota + idStudent + idDisciplina          - asigneaza o nota unui student la o disciplina")
        print("asignare_note_random                + numar_studenti                                   - asigneaza note random unui student la 5 discipline ")
        print("afisare_note                                                                           - afiseaza notele")
        print("stud_ord_nota_disciplina            + idDisciplina                                     - stud. + note la o disciplină dată, ordonat: alfabetic după nume, după media notelor")
        print("top_studenti                                                                           - primi 20% din studenți ordonati dupa media notelor la toate disciplinele ")
        print("studenti_medie_mai_mare_medie_data  + medie_data                                       - studentii care au media > medie data")

    def adauga_student_ui(self, parametri):
        """
        Adauga un student
        :param parametri: string
        :return: -
        """
        if len(parametri) != 3:
            print("Numar parametri invalid!")
            return
        try:
            idStudent = int(parametri[0])
            nume = parametri[1] + " " + parametri[2]
            self.__lista_studenti.adauga_student_service(idStudent,nume)
            print("Student adaugat cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def sterge_student_dupa_id_ui(self, parametri):
        """
        Sterge un student dupa id
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            idStudent = int(parametri[0])
            self.__lista_studenti.sterge_student_dupa_id_service(idStudent)
            print("Student sters cu succes!")
        except RepoError as re:
            print(re)
        except ValueError as ve:
            print(ve)

    def modifica_nume_student_ui(self, parametri):
        """
        Modifica numele unui student
        :param parametri: string
        :return: -
        """
        if len(parametri) != 3:
            print("Numar parametri invalid!")
            return
        try:
            idStudent = int(parametri[0])
            nume_nou = parametri[1] + " " + parametri[2]
            self.__lista_studenti.modifica_nume_student_service(idStudent,nume_nou)
            print("Nume student modificat cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def cautare_student_dupa_id_ui(self, parametri):
        """
        Cauta student dupa id
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            idStudent = int(parametri[0])
            student = self.__lista_studenti.cautare_student_dupa_id_service(idStudent)
            print(student)
        except RepoError as re:
            print(re)
        except ValueError as ve:
            print(ve)

    def afisare_studenti(self, parametri):
        """
        Afiseaza lista de studenti
        :param parametri: string
        :return: -
        """
        if len(parametri) > 0:
            print("Numar parametri invalid!")
            return
        lista = self.__lista_studenti.get_all_service()
        for student in lista:
            print(student)

    def adauga_disciplina_ui(self, parametri):
        """
        Adauga o disciplina
        :param parametri: string
        :return:
        """
        if len(parametri) != 4:
            print("Numar parametri invalid!")
            return
        try:
            idDisciplina = int(parametri[0])
            nume = parametri[1]
            profesor = parametri[2] + " " + parametri[3]
            self.__lista_discipline.adauga_disciplina_service(idDisciplina,nume,profesor)
            print("Disciplina adaugata cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def sterge_disciplina_dupa_id_ui(self, parametri):
        """
        Sterge o disciplina dupa id
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            idDisciplina = int(parametri[0])
            self.__lista_discipline.sterge_disciplina_dupa_id_service(idDisciplina)
            print("Disciplina stearsa cu succes!")
        except RepoError as re:
            print(re)
        except ValueError as ve:
            print(ve)

    def modifica_disciplina_ui(self, parametri):
        """
        Modifica numele si profesorul disciplinei
        :param parametri: string
        :return: -
        """
        if len(parametri) != 4:
            print("Numar parametri invalid!")
            return
        try:
            idDisciplina = int(parametri[0])
            nume_nou = parametri[1]
            profesor_nou = parametri[2] + " " + parametri[3]
            self.__lista_discipline.modifica_disciplina_service(idDisciplina, nume_nou, profesor_nou)
            print("Nume si profesor discipline modificate cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def cauta_disciplina_dupa_id_ui(self, parametri):
        """
        Cauta dupa id o disciplina
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            idDisciplina = int(parametri[0])
            disciplina = self.__lista_discipline.cauta_disciplina_dupa_id_service(idDisciplina)
            print(disciplina)
        except RepoError as re:
            print(re)
        except ValueError as ve:
            print(ve)



    def afisare_discipline(self, parametri):
        """
        Afiseaza toate disciplinele
        :param parametri: string
        :return: -
        """
        if len(parametri) > 0:
            print("Numar parametri invalid!")
            return
        lista = self.__lista_discipline.get_all_service()
        for disciplina in lista:
            print(disciplina)

    def adauga_student_random_ui(self, parametri):
        """
        Adauga studenti random
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            numar_studenti_de_adaugat = int(parametri[0])
            self.__lista_studenti.adauga_student_random_service(numar_studenti_de_adaugat)
            print("Studenti adaugati cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def adauga_disciplina_random_ui(self, parametri):
        """
        Adauga discipline random
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            numar_discipline_de_adaugat = int(parametri[0])
            self.__lista_discipline.adauga_disciplina_random_service(numar_discipline_de_adaugat)
            print("Discipline adaugate cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def asignare_nota_ui(self, parametri):
        """
        Asigneaza o nota unui student la o disciplina
        :param parametri: string
        :return: -
        """
        if len(parametri) != 3:
            print("Numar parametri invalid!")
            return
        try:
            valoare_nota = int(parametri[0])
            idStudent = int(parametri[1])
            idDisciplina = int(parametri[2])
            self.__lista_note.asignare_nota_service(valoare_nota, idStudent, idDisciplina)
            print("Nota asignata cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)


    def afisare_note_ui(self, parametri):
        """
        Afiseaza notele unui student la o materie
        :param parametri: string
        :return: -
        """
        if len(parametri) != 0:
            print("Numar parametri invalid!")
            return
        lista_note = self.__lista_note.get_all_service()
        for nota in lista_note:
            print(nota)

    def asignare_note_random(self, parametri):
        """
        Asigneaza note random
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            numar_note = int(parametri[0])
            self.__lista_note.asignare_random_note_service(numar_note)
            print("Note asignate cu succes!")
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def lista_studenti_note_disciplina_data_ordonata_ui(self, parametri):
        """
        Afiseaza lista de studenți și notele lor la o disciplină dată, ordonat: alfabetic după nume, după media notelor
        :param parametri: string
        :return:-
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            idDisciplina = int(parametri[0])
            lista = self.__lista_note.lista_studenti_note_disciplina_data_ordonata(idDisciplina)
            for element in lista:
                print(element)
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def top_20_la_suta_studenti_dupa_medie_ui(self, parametri):
        """
        Afiseaza primi 20% din studenți ordonati dupa media notelor la toate disciplinele
        :param parametri: string
        :return: -
        """
        if len(parametri) != 0:
            print("Numar parametri invalid!")
            return
        try:
            lista = self.__lista_note.top_20_la_suta_studenti_dupa_medie()
            for element in lista:
                print(element)
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def studenti_medie_mai_mare_medie_data_ui(self, parametri):
        """
        Afiseaza studentii bursieri (cu media > 9)
        :param parametri: string
        :return: -
        """
        if len(parametri) != 1:
            print("Numar parametri invalid!")
            return
        try:
            medie_data = int(parametri[0])
            lista = self.__lista_note.studenti_medie_mai_mare_medie_data(medie_data)
            for student in lista:
                print(student)
        except RepoError as re:
            print(re)
        except ValidationError as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

