from random import randint
import random
from Domain.studenti import Student
from Exceptions.erori import RepoError


class Service_Studenti:

    def __init__(self, __repo_studenti, __validator_studenti):
        self.__lista_studenti = __repo_studenti
        self.__validator_studenti = __validator_studenti

    def adauga_student_service(self, idStudent, nume):
        """
        Adauga un student in repository studenti ("lista de studenti")
        :return: -
        """
        student = Student(idStudent, nume)
        self.__validator_studenti.validare_student(student)
        self.__lista_studenti.adauga_student_repo(student)

    def adauga_student_random_service(self, numar_studenti):
            """
            Adauga un student random in repository studenti ("lista de studenti")
            :return: -
            """
            while numar_studenti != 0:
                idStudent = randint(0,10000000)
                if idStudent <= 0:
                    continue
                try:
                    self.__lista_studenti.cautare_student_dupa_id_repo(idStudent)
                    continue
                except RepoError as re:
                    lista_nume = ["Pop Ion", "Mihai Ionescu", "Karina Kentsch", "Irimies Vasile", "Pop Maria", "Ioana Dumitrescu", "Diana Popescu", "Muresan Tania", "Eugen Mircea"]
                    nume = random.choice(lista_nume)
                    student = Student(idStudent, nume)
                    self.__lista_studenti.adauga_student_repo(student)
                    numar_studenti = numar_studenti - 1

    def sterge_student_dupa_id_service(self, idStudent):
        """
        Sterge un student dupa id din repository studenti
        :return: -
        """
        student = Student(idStudent, "Pop Ion")
        self.__validator_studenti.validare_student(student)
        self.__lista_studenti.sterge_student_dupa_id_repo(idStudent)

    def modifica_nume_student_service(self, idStudent, nume_nou):
        """
        Modifica numele unui student din repository studenti
        :return: -
        """
        student_nou = Student(idStudent, nume_nou)
        self.__validator_studenti.validare_student(student_nou)
        self.__lista_studenti.modifica_nume_student_repo(student_nou)

    def cautare_student_dupa_id_service(self, idStudent):
        """
        Cauta student dupa id in repository studenti
        :return: student
        """
        student = Student(idStudent, "Pop Ion")
        self.__validator_studenti.validare_student(student)
        return self.__lista_studenti.cautare_student_dupa_id_repo(idStudent)

    def size(self):
        """
        Returneaza lungimea listei de studenti
        :return: lungime - int
        """
        return len(self.__lista_studenti)

    def get_all_service(self):
        """
        Returneaza lista de studenti
        :return: lista
        """
        return self.__lista_studenti.get_all()
