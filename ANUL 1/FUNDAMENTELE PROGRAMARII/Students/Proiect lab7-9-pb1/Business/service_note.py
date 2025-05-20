from Domain.DTO import DTO
from Domain.note import Nota
import random

from Utilities.MergeSort import MergeSort


class Service_Note:

    def __init__(self, __repo_note, __validator_nota, __student_repo, __disciplina_repo):
        self.__lista_note = __repo_note
        self.__validator_nota = __validator_nota
        self.__lista_studenti = __student_repo
        self.__lista_discipline = __disciplina_repo

    def asignare_nota_service(self, valoare_nota, idStudent, idDisciplina):
        """
        Adauga o nota unui student cu idStudent la disciplina cu idDisciplina
        :param valoare_nota: int
        :param idStudent: int
        :param idDisciplina: int
        :return: -
        """
        student = self.__lista_studenti.cautare_student_dupa_id_repo(idStudent)
        disciplina = self.__lista_discipline.cauta_disciplina_dupa_id_repo(idDisciplina)
        nota = Nota(len(self.__lista_note)+1, valoare_nota, student, disciplina)
        self.__validator_nota.validator_nota(nota)
        self.__lista_note.adauga_nota_repo(nota)

    def asignare_random_note_service(self, numar_studenti):
        """
        Genereaza un numar_note de note
        :param numar_note: int
        :return: -
        """
        lista_studenti = self.__lista_studenti.get_all()
        lista_discipline = self.__lista_discipline.get_all_repo()
        while numar_studenti != 0:
            student = random.choice(lista_studenti)
            for _ in range(0,5):

                disciplina = random.choice(lista_discipline)
                valoare_nota = random.randint(1,10)
                nota = Nota(len(self.__lista_note) + 1, valoare_nota, student, disciplina)
                self.__lista_note.adauga_nota_repo(nota)
            numar_studenti = numar_studenti - 1

    def size(self):
        """
        Returneaza lungimea listei service
        :return: lungime
        """
        return len(self.__lista_note)

    def get_all_service(self):
        """
        Returneaza lista de note
        :return: lista de note
        """
        return self.__lista_note.get_all_repo()

    def creare_lista_studenti_note_disciplina_data(self, id_disciplina_data):
        """
        Creeaza lista de studenți și notele lor la o disciplină dată
        :param id_disciplina_data: int
        :return: lista
        """
        lista_note = self.__lista_note.get_all_repo()
        lista_dto = {}
        for nota in  lista_note:
            if nota.get_disciplina().get_idDisciplina() == id_disciplina_data:
                student = nota.get_student()
                if student.get_idStudent() in lista_dto:
                    lista_dto[student.get_idStudent()].set_lista_note_dto(nota.get_valoare_nota())
                else:
                    dto = DTO(student)
                    dto.set_lista_note_dto(nota.get_valoare_nota())
                    lista_dto[student.get_idStudent()] = dto

        lista = [lista_dto[i] for i in lista_dto]
        return lista

    def lista_studenti_note_disciplina_data_ordonata(self, id_disciplina_data):
        """
        Returneaza lista de studenți și notele lor la o disciplină dată, ordonat: alfabetic după nume, după media notelor.
        :return: lista ordonata
        """
        lista = self.creare_lista_studenti_note_disciplina_data(id_disciplina_data)
        lista.sort(key = lambda element: (element.get_student_dto().get_nume() , element.medie_note_dto()))
        return lista

    def creare_lista_studenti_note(self):
        """
        Creeaza lista de studenți și notele lor
        :return: lista
        """
        lista_note = self.__lista_note.get_all_repo()
        lista_dto = {}
        for nota in lista_note:
            student = nota.get_student()
            if student.get_idStudent() in lista_dto:
                lista_dto[student.get_idStudent()].set_lista_note_dto(nota.get_valoare_nota())
            else:
                dto = DTO(student)
                dto.set_lista_note_dto(nota.get_valoare_nota())
                lista_dto[student.get_idStudent()] = dto

        lista = [lista_dto[i] for i in lista_dto]
        return lista

    def top_20_la_suta_studenti_dupa_medie(self):
        """
        Returneaza primi 20% din studenți ordonati dupa media notelor la toate disciplinele
        :return:lista
        """
        lista = self.creare_lista_studenti_note()
        #lista.sort(key = lambda element: element.medie_note_dto(), reverse = True)
        MergeSort(lista)
        return lista[0:len(lista)*20//100+1]


    def studenti_medie_mai_mare_medie_data(self, medie_data):
        """
        Returneaza lista studentilor care au media > medie_data
        :return: lista
        """
        bursa = []
        lista = self.creare_lista_studenti_note()
        for student_dto in lista:
            if student_dto.medie_note_dto() > medie_data:
                bursa.append(student_dto.get_student_dto())
        return bursa