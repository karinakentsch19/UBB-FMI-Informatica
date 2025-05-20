from Business.service_note import Service_Note
from Domain.DTO import DTO
from Domain.discipline import Disciplina
from Domain.note import Nota
from Domain.studenti import Student
from Exceptions.erori import RepoError, ValidationError
from Persistance.repo_discipline import Repository_Discipline
from Persistance.repo_note import Repository_Note
from Persistance.repo_studenti import Repository_Studenti
from Validator.validator_note import Validator_Note

import unittest

class Teste_Note(unittest.TestCase):

    def setUp(self):
        self.__idNota = 1
        self.__valoare_nota = 10
        self.__student = Student(12, "Pop Ion")
        self.__disciplina = Disciplina(1, "Algebra", "Ciprian Modoi")
        self.__idNotaNoua = 50
        self.__valoare_nota_noua = 6
        self.__studentNou = Student(12, "Marcel Ionut")
        self.__disciplinaNoua = Disciplina(1, "Analiza", "Stefan Berinde")
        self.__idNotaInvalid = -1
        self.__valoare_nota_invalida = -12
        self.__disciplinaInvalida = Disciplina(-1, "", "")
        self.__studentInvalid = Student(-2, "")
        self.__notaInvalida = Nota(self.__idNotaInvalid, self.__valoare_nota_invalida, self.__studentInvalid, self.__disciplinaInvalida)
        self.__nota = Nota(self.__idNota, self.__valoare_nota, self.__student, self.__disciplina)
        self.__nota_noua = Nota(self.__idNota, self.__valoare_nota, self.__student, self.__disciplina)
        self.__lista_note = Repository_Note()
        self.__lista_note.adauga_nota_repo(self.__nota)
        self.__valideaza_nota = Validator_Note()
        self.__idStudentService = 12
        self.__idDisciplinaService = 1
        self.__lista_studenti = Repository_Studenti()
        self.__lista_studenti.adauga_student_repo(self.__student)
        self.__lista_discipline = Repository_Discipline()
        self.__lista_discipline.adauga_disciplina_repo(self.__disciplina)
        self.__lista_note_service = Service_Note(self.__lista_note, self.__valideaza_nota, self.__lista_studenti, self.__lista_discipline)
        #self.__lista_note_service.asignare_nota_service(self.__valoare_nota, self.__idStudentService,self.__idDisciplinaService)
        self.__student2 = Student(50, "Maria Marian")
        self.__lista_studenti.adauga_student_repo(self.__student2)
        self.__dto = DTO(self.__student)
        self.__dto1 = DTO(self.__student)
        self.__dto2 = DTO(self.__student2)
        self.__dto.set_lista_note_dto(self.__valoare_nota)
        self.__dto1.set_lista_note_dto(self.__valoare_nota)
        self.__dto2.set_lista_note_dto(2)

    '''
    def ruleaza_toate_testele(self):
        """
        Ruleaza toate testele note
        :return: -
        """
        self.__ruleaza_teste_note_domain()
        self.__ruleaza_teste_note_repository()
        self.__ruleaza_teste_note_validator()
        self.__ruleaza_teste_note_service()

    def __ruleaza_teste_note_domain(self):
        """
        Ruleaza testele note domain
        :return: -
        """
        self.__test_creare_nota()
        self.__test_set_nota()
        self.__test_eq()
    '''
    def test_creare_nota(self):
        """
        Testeaza metoda de creare nota
        :return: -
        """

        self.assertTrue(self.__nota.get_idNota() == self.__idNota)
        self.assertTrue(self.__nota.get_valoare_nota() == self.__valoare_nota)
        self.assertTrue(self.__nota.get_student() == self.__student)
        self.assertTrue(self.__nota.get_disciplina() == self.__disciplina)

    def test_set_nota(self):
        """
        Testeaza metoda de setare a valorii unei note
        :return: -
        """
        self.__nota_noua.set_nota(6)
        self.assertTrue(self.__nota_noua.get_valoare_nota() == 6)

    def test_eq(self):
        """
        Testeaza metoda de verificare a doua id-uri pt note
        :return: -
        """
        self.assertTrue(self.__nota == self.__nota_noua)

    '''
    def __ruleaza_teste_note_repository(self):
        """
        Ruleaza toate testele pentru note repository
        :return: -
        """
        
        self.__test_adauga_nota()
        self.__test_modifica_nota()
        self.__test_cauta_nota()
        self.__test_get_all()
        self.__test_sterge_nota()

    '''

    def test_adauga_nota(self):
        """
        Testeaza metoda de adaugare nota repo
        :return: -
        """
        self.assertTrue(self.__lista_note.__len__() == 1)
        self.assertRaises(RepoError,  self.__lista_note.adauga_nota_repo, self.__nota)
        """
        try:
            self.__lista_note.adauga_nota_repo(self.__nota)
            assert False
        except RepoError as re:
            assert str(re) == "Nota existenta!\n"
        """


    def test_modifica_nota(self):
        """
        Testeaza metoda de modificare valoare nota repo
        :return: -
        """
        self.__lista_note.modifica_nota_repo(self.__nota_noua)
        self.assertTrue(self.__lista_note.cauta_nota_dupa_id_repo(1).get_valoare_nota() == self.__nota_noua.get_valoare_nota())
        self.assertRaises(RepoError, self.__lista_note.modifica_nota_repo, self.__notaInvalida)
        """
        try:
            self.__lista_note.modifica_nota_repo(self.__notaInvalida)
            assert False
        except RepoError as re:
            assert str(re) == "Nota inexistenta!\n"
        """

    def test_cauta_nota(self):
        """
        Testeaza metoda de cautare dupa id a unei note repo
        :return: -
        """
        self.assertTrue(self.__lista_note.cauta_nota_dupa_id_repo(1).get_valoare_nota() == self.__nota_noua.get_valoare_nota())
        self.assertRaises(RepoError, self.__lista_note.cauta_nota_dupa_id_repo, -1)
        """
        try:
            self.__lista_note.cauta_nota_dupa_id_repo(-1)
            assert False
        except RepoError as re:
            assert str(re) == "Nota inexistenta!\n"
        """

    def test_get_all(self):
        """
        Testeaza metoda get_all
        :return: -
        """
        self.assertTrue(self.__lista_note.get_all_repo() == [self.__nota_noua])

    def test_sterge_nota(self):
        """
        Testeaza metoda de stergere nota dupa id repo
        :return: -
        """
        self.__lista_note.sterge_nota_dupa_id_repo(1)
        self.assertTrue(self.__lista_note.__len__() == 0)
        self.assertRaises(RepoError, self.__lista_note.sterge_nota_dupa_id_repo, -1)
        """
        try:
            self.__lista_note.sterge_nota_dupa_id_repo((-1))
            assert False
        except RepoError as re:
            assert str(re) == "Nota inexistenta!\n"
        """
    '''
    def __ruleaza_teste_note_validator(self):
        """
        Ruleaza toate testele de validare nota
        :return: -
        """
        
        self.__test_validare_nota()
    '''

    def test_validare_nota(self):
        """
        Testeaza metoda de validare nota
        :return: -
        """
        self.__valideaza_nota.validator_nota(self.__nota)
        self.assertRaises(ValidationError, self.__valideaza_nota.validator_nota, self.__notaInvalida)
        """
        try:
            self.__valideaza_nota.validator_nota(self.__notaInvalida)
            assert False
        except ValidationError as ve:
            assert str(ve) == "Id invalid!\nValoare nota invalida!\n"
        """
    '''
    def __ruleaza_teste_note_service(self):
        """
        Ruleaza toate testele pentru service note
        :return: -
        """
        self.__test_asignare_nota_service()
        self.__test_get_all_service()
        self.__test_creare_lista_studenti_note_disciplina_data_service()
        self.__test_creare_lista_studenti_note()
        self.__test_lista_studenti_note_disciplina_data_ordonata()
        self.__test_top_20_la_suta_studenti_dupa_medie()
        self.__test_studenti_medie_mai_mare_medie_data()
    '''

    def test_asignare_nota_service(self):
        """
        Testeaza metoda de asignare nota service
        :return: -
        """
        #assert self.__lista_note_service.size() == 0

        self.assertTrue(self.__lista_note_service.size() == 1)
        self.assertRaises(RepoError, self.__lista_note_service.asignare_nota_service, 10, -1, -2)
        """
        try:
            self.__lista_note_service.asignare_nota_service(10, -1, -2)
            assert False
        except RepoError as re:
            assert str(re) == "Student inexistent!\n"
        """

    def test_get_all_service(self):
        """
        Testeaza metoda get_all_service
        :return: -
        """
        self.assertTrue(self.__lista_note_service.get_all_service() == [self.__nota])

    def test_creare_lista_studenti_note_disciplina_data_service(self):
        """
        Testeaza metoda creare_lista_studenti_note_disciplina_data
        :return: -
        """
        #self.__dto.set_lista_note_dto(10)
        self.assertTrue(self.__lista_note_service.creare_lista_studenti_note_disciplina_data(1) == [self.__dto])

    def test_lista_studenti_note_disciplina_data_ordonata(self):
        """
        Testeaza metoda lista_studenti_note_disciplina_data_ordonata
        :return: -
        """
        self.__lista_note_service.asignare_nota_service(2, self.__student2.get_idStudent(), 1)

        #dto1.set_lista_note_dto(10)

        #dto2.set_lista_note_dto(2)
        self.assertTrue(self.__lista_note_service.lista_studenti_note_disciplina_data_ordonata(1) == [self.__dto2, self.__dto1])

    def __test_creare_lista_studenti_note(self):
        """
        Testeaza metoda creare_lista_studenti_note
        :return: -
        """
        #dto = DTO(self.__student)
        #self.__dto.set_lista_note_dto(10)
        self.assertTrue(self.__lista_note_service.creare_lista_studenti_note() == [self.__dto])

    def test_top_20_la_suta_studenti_dupa_medie(self):
        """
        Testeaza metoda top_20_la_suta_studenti_dupa_medie
        :return: -
        """
        #dto1 = DTO(self.__student)
        #dto1.set_lista_note_dto(10)
        #dto2 = DTO(self.__student2)
        #dto2.set_lista_note_dto(2)
        self.assertTrue(self.__lista_note_service.top_20_la_suta_studenti_dupa_medie() == [self.__dto1])

    def test_studenti_medie_mai_mare_medie_data(self):
        """
        Testeaza metoda studenti_bursieri
        :return: -
        """
        self.assertTrue(self.__lista_note_service.studenti_medie_mai_mare_medie_data(9) == [self.__student])












