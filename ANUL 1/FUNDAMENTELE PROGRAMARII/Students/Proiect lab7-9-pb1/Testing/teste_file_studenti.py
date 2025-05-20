from Domain.studenti import Student
from Persistance.file_repo_studenti import File_Repository_Studenti


class Teste_file_studenti():

    def __init__(self):
        self.__fisier = "teste_studenti.txt"
        self.__lista_studenti_file = File_Repository_Studenti(self.__fisier)

    def ruleaza_toate_testele(self):
        self.test_load_from_file()
        self.test_store_to_file()

    def test_load_from_file(self):
        """
        Testeaza metoda load_from_file
        :return: -
        """
        self.__lista_studenti_file.load_from_file()
        assert self.__lista_studenti_file.__len__() == 5


    def test_store_to_file(self):
        """
        Testeaza metoda store_to_file
        :return: -
        """
        self.__student = Student(1, "Mester Paul")
        self.__lista_studenti_file.adauga_student_repo(self.__student)
        self.__lista_studenti_file.store_to_file()
        self.__lista_studenti_file.load_from_file()
        assert self.__lista_studenti_file.__len__() == 6
        self.__lista_studenti_file.sterge_student_dupa_id_repo(self.__student.get_idStudent())


