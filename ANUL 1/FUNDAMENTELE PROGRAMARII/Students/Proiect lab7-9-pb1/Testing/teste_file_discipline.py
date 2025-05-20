from Domain.discipline import Disciplina
from Persistance.file_repo_discipline import File_Repository_Discipline


class Teste_file_discipline():

    def __init__(self):
        self.__fisier = "teste_discipline.txt"
        self.__lista_discipline_file = File_Repository_Discipline(self.__fisier)

    def ruleaza_toate_testele(self):
        self.test_load_from_file()
        self.test_store_to_file()

    def test_load_from_file(self):
        self.__lista_discipline_file.load_from_file()
        assert self.__lista_discipline_file.__len__() == 3

    def test_store_to_file(self):
        self.__disciplina = Disciplina(4, "Filozofie", "Eugen Cosmin")
        self.__lista_discipline_file.adauga_disciplina_repo(self.__disciplina)
        self.__lista_discipline_file.store_to_file()
        self.__lista_discipline_file.load_from_file()
        assert self.__lista_discipline_file.__len__() == 4
        self.__lista_discipline_file.sterge_disciplina_dupa_id_repo(self.__disciplina.get_idDisciplina())