from Domain.DTO import DTO
from Domain.studenti import Student
import unittest

class Teste_DTO(unittest.TestCase):

    def setUp(self):
        self.__student = Student(12, "Pop Ion")
        self.__dto = DTO(self.__student)

    '''
    def ruleaza_toate_testele(self):
        """
        Ruleaza toate testele DTO
        :return: -
        """
        self.__ruleaza_teste_domain()
    
    def __ruleaza_teste_domain(self):
        """
        Ruleaza testele din domain DTO
        :return: -
        """
        self.__test_creare_dto()
        self.__test_set_lista_note_dto()
        self.__test_medie_note_dto()
    '''

    def test_creare_dto(self):
        """
        Testeaza metoda de creare dto
        :return: -
        """
        #self.__dto = DTO(self.__student)
        self.assertTrue(self.__dto.get_student_dto() == self.__student)

    def test_set_lista_note_dto(self):
        """
        Testeaza metoda de adaugare nota in lista note dto
        :return: -
        """
        self.assertTrue(self.__dto.lungime_lista_note_dto() == 0)
        self.__dto.set_lista_note_dto(4)
        self.assertTrue(self.__dto.get_lista_note_dto() == [4])

    def test_medie_note_dto(self):
        """
        Testeaza metoda medie note dto
        :return: -
        """
        self.__dto.set_lista_note_dto(10)
        self.assertTrue(self.__dto.medie_note_dto() == 10)
    
    
        