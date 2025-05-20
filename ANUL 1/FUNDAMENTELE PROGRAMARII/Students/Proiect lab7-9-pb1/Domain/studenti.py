class Student(object):

    def __init__(self, idStudent, nume):
        """
        Creeaza un obiect Student cu atributele idStudent, nume
        :param idStudent: int
        :param nume: string
        """
        self.__idStudent = idStudent
        self.__nume = nume

    def set_nume(self, nume_nou):
        """
        Actualizam numele unui student
        :param nume_nou: string
        :return: -
        """
        self.__nume = nume_nou

    def get_idStudent(self):
        """
        Returneaza idStudent al studentului
        :return: idStudent - int
        """
        return self.__idStudent

    def get_nume(self):
        """
        Returneaza numele studentului
        :return: nume - string
        """
        return self.__nume

    def __str__(self):
        """
        Returneaza datele studentului in format string
        :return: date student - string
        """
        return f"Id Student: {self.__idStudent}\nNume: {self.__nume}\n"
        #return f"Id Student: {self.__idStudent}      Nume: {self.__nume}"

    def __eq__(self, other):
        """
        Returneaza True/False in functie de egalitatea dintre id-urile studentilor
        :param other: student
        :return: True  - studentii au idStudent egal
                 False - studentii nu au idStudent egal
        """
        return self.__idStudent == other.get_idStudent()

