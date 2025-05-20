from Exceptions.erori import RepoError


class Repository_Studenti:

    def __init__(self):
        #self.__lista_studenti = []
         self.__lista_studenti = {}

    def adauga_student_repo(self, student):
        """
        Adauga un student in repository studenti ("lista de studenti")
        :raise: RepoError - daca id student se regaseste in lista
        """

        """
        Lista
        for stud in self.__lista_studenti:
            if stud.get_idStudent() == student.get_idStudent():
                raise RepoError("Student existent!\n")
        self.__lista_studenti.append(student)
        """
        if student.get_idStudent() not in self.__lista_studenti:
            self.__lista_studenti[student.get_idStudent()] = student
        else:
            raise RepoError("Student existent!\n")


    def sterge_student_dupa_id_repo(self, idStudent):
        """
        Sterge un student dupa id din repository studenti
        :raise: RepoError - daca id student nu se regaseste in lista
        """

        """
        Lista
        for student in self.__lista_studenti:
            if student.get_idStudent() == idStudent:
                self.__lista_studenti.remove(student)
                return
        raise RepoError("Student inexistent!\n")
        """
        if idStudent not in self.__lista_studenti:
            raise RepoError("Student inexistent!\n")
        else:
            del self.__lista_studenti[idStudent]


    def modifica_nume_student_repo(self, student_nou):
        """
        Modifica numele unui student din repository studenti
        :raise: RepoError - daca id student nu se regaseste in lista
        """
        """
        Lista
        for student in self.__lista_studenti:
            if student.get_idStudent() == student_nou.get_idStudent():
                student.set_nume(student_nou.get_nume())
                return
        raise RepoError("Student inexistent!\n")
        """
        if student_nou.get_idStudent() not in self.__lista_studenti:
            raise RepoError("Student inexistent!\n")
        else:
            self.__lista_studenti[student_nou.get_idStudent()] = student_nou

    def cautare_student_dupa_id_repo(self, idStudent):
        """
        Cauta student dupa id in repository studenti
        :return: student
        :raise: RepoError - daca id student nu se regaseste in lista
        """
        """
        Lista
        for student in self.__lista_studenti:
            if student.get_idStudent() == idStudent:
                return student
        raise RepoError("Student inexistent!\n")
        """
        if idStudent in self.__lista_studenti:
            return self.__lista_studenti[idStudent]
        else:
            raise RepoError("Student inexistent!\n")


    def __len__(self):
        """
        Returneaza lungimea listei de studenti
        :return: lungime - int
        """
        return len(self.__lista_studenti)

    def get_all(self):
        """
        Returneaza lista de studenti
        :return: lista
        """
        """
        Lista
        return self.__lista_studenti
        """
        lista = []
        for student in self.__lista_studenti:
            lista.append(self.__lista_studenti[student])
        return lista[:]

    def sterge_toata_lista(self):
        self.__lista_studenti = {}