from Business.service_discipline import Service_Discipline
from Domain.discipline import Disciplina
from Exceptions.erori import RepoError, ValidationError
from Persistance.repo_discipline import Repository_Discipline
from Validator.validator_discipline import Validator_Discipline
import unittest

class Teste_Discipline(unittest.TestCase):

    def setUp(self):
        self.__idDisciplina = 67
        self.__nume = "Analiza"
        self.__profesor = "Stefan Berinde"
        self.__nume_nou = "Algebra"
        self.__profesor_nou = "George Modoi"
        self.__idDisciplinaInvalid = -1
        self.__numeInvalid = "12 ion"
        self.__profesorInvalid = ""
        self.__disciplina = Disciplina(self.__idDisciplina, self.__nume, self.__profesor)
        self.__disciplina_noua = Disciplina(self.__idDisciplina, self.__nume, self.__profesor)
        self.__disciplina_inexistenta = Disciplina(123, "Logica", "Andreea Pop")
        self.__lista_discipline = Repository_Discipline()
        self.__disciplina_invalida = Disciplina(self.__idDisciplinaInvalid, self.__numeInvalid, self.__profesorInvalid)
        self.__validator_discipline = Validator_Discipline()
        self.__disciplina_service = Service_Discipline(self.__lista_discipline, self.__validator_discipline)
        self.__lista_discipline.adauga_disciplina_repo(self.__disciplina)

    '''
    def ruleaza_toate_testele(self):
        """
        Ruleaza toate testele pentru discipline
        :return: -
        """
        self.__ruleaza_teste_domain()
        self.__ruleaza_teste_repository()
        self.__ruleaza_teste_validator()
        self.__ruleaza_teste_service()

    def __ruleaza_teste_domain(self):
        """
        Ruleaza toate testele pentru domain discipline
        :return: -
        """
        self.__test_creare_disciplina()
        self.__test_set_nume()
        self.__test_set_profesor()
        self.__test_eq()
    '''

    def test_creare_disciplina(self):
        """
        Testeaza metoda de creare a unei discipline
        :return: -
        """
        self.assertEqual(self.__disciplina.get_idDisciplina(), 67)
        self.assertEqual(self.__disciplina.get_nume(), "Analiza")
        self.assertEqual(self.__disciplina.get_profesor(), "Stefan Berinde")


    def test_set_nume(self):
        """
        Testeaza metoda de setare a numelui unei discipline
        :return: -
        """
        self.__disciplina.set_nume("Algebra")
        self.assertEqual(self.__disciplina.get_nume(), "Algebra")

    def test_set_profesor(self):
        """
        Testeaza metoda de setare a numelui profesorului unei discipline
        :return: -
        """
        self.__disciplina.set_profesor("George Modoi")
        self.assertEqual(self.__disciplina.get_profesor(), "George Modoi")

    def test_eq(self):
       """
       Testeaza metoda de verificare a egalitatii a doua discipline dupa id
       :return: -
       """
       self.assertTrue(self.__disciplina.__eq__(self.__disciplina_noua) == True)

    '''
    def __ruleaza_teste_repository(self):
        """
        Ruleaza toate testele pentru repo discipline
        :return: -
        """
        
        
        self.__test_adauga_disciplina_repo()
        self.__test_cauta_disciplina_dupa_id_repo()
        self.__test_modifica_disciplina_repo()
        self.__test_sterge_disciplina_dupa_id_repo()
    '''

    def test_adauga_disciplina_repo(self):
        """
        Testeaza metoda de adaugare a unei discipline repo
        :return: -
        """
        self.assertTrue(self.__lista_discipline.__len__() == 1)
        self.assertRaises(RepoError, self.__lista_discipline.adauga_disciplina_repo, self.__disciplina)
        """
        try:
            self.__lista_discipline.adauga_disciplina_repo(self.__disciplina)
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina existenta!\n"
        """


    def test_sterge_disciplina_dupa_id_repo(self):
        """
        Testeaza metoda de stergere a unei discipline dupa id repo
        :return: -
        """
        self.__lista_discipline.sterge_disciplina_dupa_id_repo(67)
        self.assertTrue(self.__lista_discipline.__len__() == 0)
        self.assertRaises(RepoError, self.__lista_discipline.sterge_disciplina_dupa_id_repo, 20)
        """
        try:
            self.__lista_discipline.sterge_disciplina_dupa_id_repo(20)
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina inexistenta!\n"
        """


    def test_cauta_disciplina_dupa_id_repo(self):
        """
        Testeaza metoda de cautarea a unei discipline repo dupa id
        :return: -
        """
        self.assertTrue(self.__lista_discipline.cauta_disciplina_dupa_id_repo(67) == self.__disciplina)
        self.assertRaises(RepoError, self.__lista_discipline.cauta_disciplina_dupa_id_repo, 90)
        """
        try:
            self.__lista_discipline.cauta_disciplina_dupa_id_repo(90)
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina inexistenta!\n"
        """


    def test_modifica_disciplina_repo(self):
        """
        Testeaza metoda de modificare a unei discipline repo
        :return: -
        """
        self.__lista_discipline.modifica_disciplina_repo(self.__disciplina_noua)
        self.assertTrue(self.__lista_discipline.cauta_disciplina_dupa_id_repo(67).get_nume() == "Analiza")
        self.assertTrue(self.__lista_discipline.cauta_disciplina_dupa_id_repo(67).get_profesor() == "Stefan Berinde")
        self.assertRaises(RepoError, self.__lista_discipline.modifica_disciplina_repo, self.__disciplina_inexistenta)
        """
        try:
            self.__lista_discipline.modifica_disciplina_repo(self.__disciplina_inexistenta)
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina inexistenta!\n"
        """

    '''
    def __ruleaza_teste_validator(self):
        """
        Ruleaza testele pentru validator discipline
        :return: -
        """
        
        
        self.__test_validare_disciplina()
    '''
    def test_validare_disciplina(self):
        """
        Testeaza metoda de validare a unei discipline
        :return: -
        """
        self.__validator_discipline.validator_disciplina(self.__disciplina)
        self.assertRaises(ValidationError, self.__validator_discipline.validator_disciplina, self.__disciplina_invalida)
        """
        try:
            self.__validator_discipline.validator_disciplina(self.__disciplina_invalida)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\nNume invalid!\nProfesor invalid!\n"
        """

    '''
    def __ruleaza_teste_service(self):
        """
        Ruleaza testele pentru service discipline
        :return: -
        """
        
        self.__test_adauga_disciplina_service()
        self.__test_cauta_disciplina_dupa_id_service()
        self.__test_modifica_disciplina_service()
        self.__test_sterge_disciplina_dupa_id_service()
        self.__test_adauga_disciplina_random_service()
    '''
    def test_adauga_disciplina_service(self):
        """
        Testeaza metoda de adaugare disciplina service
        :return: -
        """
        #self.__disciplina_service.adauga_disciplina_service(67, "Analiza", "Stefan Berinde")
        self.assertTrue(self.__disciplina_service.size() == 1)
        self.assertRaises(RepoError, self.__disciplina_service.adauga_disciplina_service, 67, "Analiza", "Stefan Berinde")
        self.assertRaises(ValidationError,  self.__disciplina_service.adauga_disciplina_service, -1, "12 ion", "")
        """
        try:
            self.__disciplina_service.adauga_disciplina_service(67, "Analiza", "Stefan Berinde")
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina existenta!\n"

        try:
            self.__disciplina_service.adauga_disciplina_service(-1, "12 ion", "")
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\nNume invalid!\nProfesor invalid!\n"
        """

    def test_cauta_disciplina_dupa_id_service(self):
        """
        Testeaza metoda de cautare disciplina service
        :return: -
        """
        self.assertTrue(self.__disciplina_service.cauta_disciplina_dupa_id_service(67) == self.__disciplina)
        self.assertRaises(RepoError, self.__disciplina_service.cauta_disciplina_dupa_id_service, 80)
        self.assertRaises(ValidationError, self.__disciplina_service.cauta_disciplina_dupa_id_service, -1)
        """
        try:
            self.__disciplina_service.cauta_disciplina_dupa_id_service(80)
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina inexistenta!\n"

        try:
            self.__disciplina_service.cauta_disciplina_dupa_id_service(-1)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\n"
        """

    def test_modifica_disciplina_service(self):
        """
        Testeaza metoda de modificare a unei discipline service
        :return: -
        """
        self.__disciplina_service.modifica_disciplina_service(67, "Algebra", "George Modoi")
        self.assertTrue(self.__disciplina_service.cauta_disciplina_dupa_id_service(67).get_nume() == "Algebra")
        self.assertTrue(self.__disciplina_service.cauta_disciplina_dupa_id_service(67).get_profesor() == "George Modoi")
        self.assertRaises(RepoError, self.__disciplina_service.modifica_disciplina_service, 90,"Algebra", "George Modoi")
        self.assertRaises(ValidationError, self.__disciplina_service.modifica_disciplina_service, -1, "12 ion", "")
        """
        try:
            self.__disciplina_service.modifica_disciplina_service(90,"Algebra", "George Modoi")
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina inexistenta!\n"

        try:
            self.__disciplina_service.modifica_disciplina_service(-1, "12 ion", "")
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\nNume invalid!\nProfesor invalid!\n"
        """

    def test_sterge_disciplina_dupa_id_service(self):
        """
        Testeaza metoda de stergere a unei discipline service
        :return: -
        """
        self.__disciplina_service.sterge_disciplina_dupa_id_service(67)
        self.assertTrue(self.__disciplina_service.size() == 0)
        self.assertRaises(RepoError, self.__disciplina_service.sterge_disciplina_dupa_id_service, 90)
        self.assertRaises(ValidationError, self.__disciplina_service.sterge_disciplina_dupa_id_service, -1)
        """
        try:
            self.__disciplina_service.sterge_disciplina_dupa_id_service(90)
            assert False
        except RepoError as re:
            assert str(re) == "Disciplina inexistenta!\n"

        try:
            self.__disciplina_service.sterge_disciplina_dupa_id_service(-1)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\n"
        """

    def test_adauga_disciplina_random_service(self):
        """
        Testeaza metoda de adaugare o disciplina random service
        :return: -
        """
        self.__disciplina_service.sterge_disciplina_dupa_id_service(self.__idDisciplina)
        self.assertTrue(self.__disciplina_service.size() == 0)
        self.__disciplina_service.adauga_disciplina_random_service(3)
        self.assertTrue(self.__disciplina_service.size() == 3)









