from Business.service_studenti import Service_Studenti
from Domain.studenti import Student
from Exceptions.erori import RepoError, ValidationError
from Persistance.repo_studenti import Repository_Studenti
from Validator.validator_studenti import Validator_Studenti
import unittest

class Teste_Studenti(unittest.TestCase):

    def setUp(self):
        self.__idStudent = 12
        self.__nume = "Kentsch Karina"
        self.__nume_nou = "Irimies Vasile"
        self.__idStudentInvalid = -32
        self.__numeInvalid = ""
        self.__student_invalid = Student(self.__idStudentInvalid, self.__numeInvalid)
        self.__student = Student(self.__idStudent, self.__nume)
        self.__lista_studenti = Repository_Studenti()
        self.__validator_student = Validator_Studenti()
        self.__service_student = Service_Studenti(self.__lista_studenti, self.__validator_student)
        self.__student_nou = Student(self.__idStudent, self.__nume)
        self.__lista_studenti.adauga_student_repo(self.__student)
    '''
    def ruleaza_toate_testele(self):
        """
        Ruleaza toate testele pentru studenti, repo studenti, service studenti si validator studenti
        :return: -
        """
        self.ruleaza_teste_domain()
        self.ruleaza_teste_repository()
        self.ruleaza_teste_validator()
        self.ruleaza_teste_service()

    '''
    '''
    def ruleaza_teste_domain(self):
        """
        Ruleaza testele pentru domain studenti
        :return: -
        """
        self.test_creare_student()
        self.test_set_student()
        self.test_eq()
    '''

    def test_creare_student(self):
        """
        Testeaza metoda de creare a unui student
        :return: -
        """
        self.assertEqual(self.__student.get_idStudent(), 12)
        self.assertEqual(self.__student.get_nume(), "Kentsch Karina")

    def test_set_student(self):
        """
        Testeaza metoda de setare a unui nume pt un student
        :return: -
        """
        self.__student_nou.set_nume("Irimies Vasile")
        self.assertEqual(self.__student_nou.get_idStudent(), 12)
        self.assertEqual(self.__student_nou.get_nume(), "Irimies Vasile")

    def test_eq(self):
        """
        Testeaza metoda __eq__
        :return: -
        """
        self.assertEqual(self.__student, self.__student_nou)

    '''
    def ruleaza_teste_repository(self):
        """
        Ruleaza testele pentru repository studenti
        :return: -
        """
        
        self.test_adaugare_student()
        self.test_modifica_student()
        self.test_cauta_student()
        self.test_get_all()
        self.test_sterge_student()
    '''

    def test_adaugare_student(self):
        """
        Testeaza metoda de adaugare a unui student
        :return: -
        """
        self.assertTrue(len(self.__lista_studenti) == 1)
        self.assertTrue(self.__lista_studenti.cautare_student_dupa_id_repo(12) == self.__student)
        self.assertRaises(RepoError, self.__lista_studenti.adauga_student_repo, self.__student)
        """
        try:
            self.__lista_studenti.adauga_student_repo(self.__student)
            assert False
        except RepoError as re:
            assert str(re) == "Student existent!\n"
        """

    def test_modifica_student(self):
        """
        Testeaza metoda modifica numele unui student
        """
        self.__lista_studenti.modifica_nume_student_repo(self.__student_nou)
        self.assertTrue(self.__lista_studenti.cautare_student_dupa_id_repo(12).get_nume() == self.__student_nou.get_nume())
        self.assertRaises(RepoError, self.__lista_studenti.modifica_nume_student_repo, self.__student_invalid)
        """
        try:
            self.__lista_studenti.modifica_nume_student_repo(self.__student_invalid)
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        """

    def test_cauta_student(self):
        """
        Testeaza metoda cauta student dupa id
        """
        self.assertTrue(self.__lista_studenti.cautare_student_dupa_id_repo(12).get_nume() == self.__student_nou.get_nume())
        self.assertRaises(RepoError, self.__lista_studenti.cautare_student_dupa_id_repo, -32)
        """
        try:
            self.__lista_studenti.cautare_student_dupa_id_repo(-32)
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        """

    def test_sterge_student(self):
        """
        Testeaza metoda sterge student dupa id
        """
        self.__lista_studenti.sterge_student_dupa_id_repo(12)
        self.assertTrue(len(self.__lista_studenti) == 0)
        self.assertRaises(RepoError, self.__lista_studenti.sterge_student_dupa_id_repo, -32)
        """
        try:
            self.__lista_studenti.sterge_student_dupa_id_repo(-32)
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        """

    def test_get_all(self):
        """
        Testeaza metoda get_all
        """
        self.assertTrue(self.__lista_studenti.get_all() == [self.__student_nou])

    '''
    def ruleaza_teste_validator(self):
        """
        Ruleaza testele pentru validator studenti
        """
        self.test_validator_studenti()
    '''

    def test_validator_studenti(self):
        """
        Testeaza metoda de validare a unui student
        """
        self.__validator_student.validare_student(self.__student)
        self.assertRaises(ValidationError, self.__validator_student.validare_student, self.__student_invalid)
        """
        try:
            self.__validator_student.validare_student(self.__student_invalid)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\nNume invalid!\n"
        """

    '''
    def ruleaza_teste_service(self):
        """
        Ruleaza testele pentru service studenti
        """
        
        self.test_adaugare_student_service()
        self.test_modifica_student_service()
        self.test_cauta_student_service()
        self.test_sterge_student_service()
        self.test_adaugare_random_student_service()
    '''

    def test_adaugare_student_service(self):
        """
        Testeaza metoda de adaugare a unui student din service
        """
        #self.__service_student.adauga_student_service(12, "Kentsch Karina")
        self.assertTrue(self.__service_student.size() == 1)
        self.assertRaises(ValidationError, self.__service_student.adauga_student_service, -32, "")
        self.assertRaises(RepoError, self.__service_student.adauga_student_service, 12, "Kentsch Karina")
        """
        try:
            self.__service_student.adauga_student_service(-32, "")
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\nNume invalid!\n"
        try:
            self.__service_student.adauga_student_service(12, "Kentsch Karina")
            assert False
        except RepoError as re:
            assert str(re) == "Student existent!\n"
        """

    def test_modifica_student_service(self):
        """
        Testeaza metoda de modificare nume a unui student din service
        """
        self.__service_student.modifica_nume_student_service(12, "Irimies Vasile")
        self.assertTrue(self.__service_student.cautare_student_dupa_id_service(12).get_nume() == "Irimies Vasile")
        self.assertRaises(RepoError, self.__service_student.modifica_nume_student_service, 32, "Irimies Vasile")
        self.assertRaises(ValidationError, self.__service_student.modifica_nume_student_service, 12, "")
        """
        try:
            self.__service_student.modifica_nume_student_service(32, "Irimies Vasile")
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        try:
            self.__service_student.modifica_nume_student_service(12, "")
            assert False
        except ValidationError as ve:
            assert str(ve) == "Nume invalid!\n"
        """

    def test_cauta_student_service(self):
        """
        Testeaza metoda de cautare dupa id a unui student din service
        """
        self.assertTrue(self.__service_student.cautare_student_dupa_id_service(12) == self.__student)
        self.assertRaises(RepoError, self.__service_student.cautare_student_dupa_id_service, 13)
        self.assertRaises(ValidationError, self.__service_student.cautare_student_dupa_id_service, -32)
        """
        try:
            self.__service_student.cautare_student_dupa_id_service(13)
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        try:
            self.__service_student.cautare_student_dupa_id_service(-32)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\n"
        """

    def test_sterge_student_service(self):
        """
        Testeaza metoda de stergere dupa id a unui student din service
        black box testing
        """
        self.__service_student.sterge_student_dupa_id_service(self.__idStudent)
        self.assertTrue(self.__service_student.size() == 0)
        self.assertRaises(RepoError, self.__service_student.sterge_student_dupa_id_service, 13)
        self.assertRaises(ValidationError, self.__service_student.sterge_student_dupa_id_service, self.__idStudentInvalid)
        """
        try:
            self.__service_student.sterge_student_dupa_id_service(13)
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        try:
            self.__service_student.sterge_student_dupa_id_service(-32)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\n"
        """

    def test_adaugare_random_student_service(self):
        """
        Testeaza metoda de adaugare random student service
        :return: -
        """
        self.__service_student.sterge_student_dupa_id_service(self.__idStudent)
        self.assertTrue(self.__service_student.size() == 0)
        self.__service_student.adauga_student_random_service(3)
        self.assertTrue(self.__service_student.size() == 3)

