from Testing.teste_discipline import Teste_Discipline
from Testing.teste_dto import Teste_DTO
from Testing.teste_file_discipline import Teste_file_discipline
from Testing.teste_file_note import Teste_file_note
from Testing.teste_file_studenti import Teste_file_studenti
from Testing.teste_note import Teste_Note
from Testing.teste_studenti import Teste_Studenti
import unittest

class Teste(object):

    def __init__(self):
        self.__teste_studenti = Teste_Studenti()
        self.__teste_discipline = Teste_Discipline()
        self.__teste_note = Teste_Note()
        self.__teste_dto = Teste_DTO()
        self.__teste_studenti_file = Teste_file_studenti()
        self.__teste_discipline_file = Teste_file_discipline()
        self.__teste_note_file = Teste_file_note()

    def ruleaza_toate_testele(self):
        #self.__teste_studenti.ruleaza_toate_testele()
        #self.__teste_discipline.ruleaza_toate_testele()
        #self.__teste_note.ruleaza_toate_testele()
        #self.__teste_dto.ruleaza_toate_testele()
        self.__teste_studenti_file.ruleaza_toate_testele()
        self.__teste_discipline_file.ruleaza_toate_testele()
        self.__teste_note_file.ruleaza_toate_testele()
