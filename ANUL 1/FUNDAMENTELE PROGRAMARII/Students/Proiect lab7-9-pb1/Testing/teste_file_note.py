from Domain.discipline import Disciplina
from Domain.note import Nota
from Domain.studenti import Student
from Persistance.file_repo_note import File_Repository_Note


class Teste_file_note():

    def __init__(self):
        self.__fisier = "teste_note.txt"
        self.__lista_note_file = File_Repository_Note(self.__fisier)

    def ruleaza_toate_testele(self):
        self.test_load_from_file()
        self.test_store_to_file()

    def test_load_from_file(self):
        self.__lista_note_file.load_from_file()
        assert self.__lista_note_file.__len__() == 2

    def test_store_to_file(self):
        self.__student = Student(5350000,"Mihai Ionescu")
        self.__disciplina = Disciplina(7313360, "Informatica", "Stefan Berinde")
        self.__nota = Nota(3, 5, self.__student, self.__disciplina)
        self.__lista_note_file.adauga_nota_repo(self.__nota)
        self.__lista_note_file.store_to_file()
        self.__lista_note_file.load_from_file()
        assert self.__lista_note_file.__len__() == 3
        self.__lista_note_file.sterge_nota_dupa_id_repo(self.__nota.get_idNota())
