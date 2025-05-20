from random import randint
import random

from Domain.discipline import Disciplina
from Exceptions.erori import RepoError


class Service_Discipline:

    def __init__(self, __lista_discipline_repo, __validator_discipline):
        self.__lista_discipline = __lista_discipline_repo
        self.__validator_disciplina = __validator_discipline

    def adauga_disciplina_service(self, idDisciplina, nume, profesor):
        """
        Adauga o disciplina
        :param idDisciplina: int
        :param nume: string
        :param profesor: string
        :return: -
        """
        disciplina = Disciplina(idDisciplina,nume,profesor)
        self.__validator_disciplina.validator_disciplina(disciplina)
        self.__lista_discipline.adauga_disciplina_repo(disciplina)

    def adauga_disciplina_random_service(self, numar_discipline):
        """
        Adauga discipline random service
        :param numar_discipline: int
        :return: -
        """
        while numar_discipline != 0:
            idDisciplina = randint(0, 10000000)
            if idDisciplina <= 0:
                continue
            try:
                self.__lista_discipline.cauta_disciplina_dupa_id_repo(idDisciplina)
                continue
            except RepoError as re:
                lista_nume = ["Algebra", "Analiza", "Romana", "Biologie", "Chimie", "Geografie", "Istorie", "Informatica", "Logica", "Sport", "Desen", "Muzica"]
                nume = random.choice(lista_nume)
                lista_profesori = ["Stefan Berinde", "Ciprian Modoi", "Marinescu Teodora", "Popescu Crina", "Lovinescu Ion", "Pop Traian", "Croitoru Eugen"]
                profesor = random.choice(lista_profesori)
                disciplina = Disciplina(idDisciplina, nume, profesor)
                self.__lista_discipline.adauga_disciplina_repo(disciplina)
                numar_discipline = numar_discipline - 1

    def sterge_disciplina_dupa_id_service(self, idDisciplina):
        """
        Sterge o disciplina
        :param idDisciplina: int
        :return: -
        """
        disciplina = Disciplina(idDisciplina, "Algebra", "Ciprian Ionel")
        self.__validator_disciplina.validator_disciplina(disciplina)
        #self.__lista_discipline.sterge_disciplina_dupa_id_repo(idDisciplina)
        self.__lista_discipline.sterge_disciplina_dupa_id_repo_recursiva(idDisciplina, 0)

    def modifica_disciplina_service(self, idDisciplina, nume_nou, profesor_nou):
        """
        Modifica numele si profesorul unei discipline
        :param idDisciplina: int
        :param nume_nou: string
        :param profesor_nou: string
        :return: -
        """
        disciplina_noua = Disciplina(idDisciplina, nume_nou, profesor_nou)
        self.__validator_disciplina.validator_disciplina(disciplina_noua)
        #self.__lista_discipline.modifica_disciplina_repo(disciplina_noua)
        self.__lista_discipline.modifica_disciplina_repo_recursiv(disciplina_noua,0)

    def cauta_disciplina_dupa_id_service(self, idDisciplina):
        """
        Cauta o disciplina dupa id
        :param idDisciplina: int
        :return: disciplina
        """
        disciplina = Disciplina(idDisciplina, "Mate", "Pop Ion")
        self.__validator_disciplina.validator_disciplina(disciplina)
        return self.__lista_discipline.cauta_disciplina_dupa_id_repo(idDisciplina)

    def get_all_service(self):
        """
        Returneaza toate disciplinele
        :return:disciplinele
        """
        return self.__lista_discipline.get_all_repo()

    def size(self):
        """
        Returneaza lungimea listei de discipline
        :return: lungime
        """
        return self.__lista_discipline.__len__()
