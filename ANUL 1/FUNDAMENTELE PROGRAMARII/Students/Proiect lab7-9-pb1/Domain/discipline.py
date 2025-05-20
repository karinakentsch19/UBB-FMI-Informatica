class Disciplina:

    def __init__(self, idDisciplina, nume, profesor):
        self.__idDisciplina = idDisciplina
        self.__nume = nume
        self.__profesor = profesor

    def set_nume(self, nume_nou):
        """
        Seteaza numele disciplinei
        :param nume_nou: string
        :return: -
        """
        self.__nume = nume_nou

    def set_profesor(self, profesor_nou):
        """
        Seteaza numele profesorului disciplinei
        :param profesor_nou: string
        :return: -
        """
        self.__profesor = profesor_nou

    def get_idDisciplina(self):
        """
        Returneaza id-ul disciplinei
        :return: idDisciplina - int
        """
        return self.__idDisciplina

    def get_nume(self):
        """
        Returneaza numele disciplinei
        :return: nume
        """
        return self.__nume

    def get_profesor(self):
        """
        Returneaza numele profesorului
        :return: nume profesor
        """
        return self.__profesor

    def __str__(self):
        """
        Returneaza atributele disciplinei in format string
        :return: disciplina
        """
        return f"Id Disciplina: {self.__idDisciplina}\nNume: {self.__nume}\nProfesor: {self.__profesor}\n"
        #return f"Id Disciplina: {self.__idDisciplina}   Nume: {self.__nume}   Profesor: {self.__profesor}"

    def __eq__(self, other):
        """
        Verifica daca doua discipline au acelasi id
        :param other: disciplina
        :return: True  - disciplinele au acelasi id
                 False - disciplinele nu au acelasi id
        """
        return self.get_idDisciplina() == other.get_idDisciplina()