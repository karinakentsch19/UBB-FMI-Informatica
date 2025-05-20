from Domain.discipline import Disciplina
from Domain.note import Nota
from Domain.studenti import Student
from Persistance.file_repo_discipline import File_Repository_Discipline
from Persistance.file_repo_studenti import File_Repository_Studenti
from Persistance.repo_note import Repository_Note


class File_Repository_Note(Repository_Note):

    def __init__(self, __fisier):
        self.fisier = __fisier
        Repository_Note.__init__(self)
        self.__lista_studenti = File_Repository_Studenti("Studenti.txt")
        self.__lista_discipline = File_Repository_Discipline("Discipline.txt")
        self.load_from_file()

    def store_to_file(self):
        """
        Scrie in fisier lista de studenti
        :return: -
        """
        f = open(self.fisier, "w")
        lista_note = Repository_Note.get_all_repo(self)
        for nota in lista_note:
            student = nota.get_student()
            #string_student = f"{student.get_idStudent()},{student.get_nume()}"
            disciplina = nota.get_disciplina()
            #string_disciplina = f"{disciplina.get_idDisciplina()},{disciplina.get_nume()},{disciplina.get_profesor()}"
            string_nota = f"{nota.get_idNota()},{nota.get_valoare_nota()},{student.get_idStudent()},{disciplina.get_idDisciplina()},\n"
            f.write(string_nota)
        f.close()

    def load_from_file(self):
        """
        Incarci din fisier lista de studenti
        :return: -
        """
        f = open(self.fisier, "r")
        Repository_Note.sterge_toata_lista(self)
        line = f.readline().strip()
        while line != "":
            parametri = line.split(",")
            idNota = int(parametri[0])
            valoareNota = int(parametri[1])
            id_student = int(parametri[2])
            id_disciplina = int(parametri[3])
            nota = Nota(idNota, valoareNota, self.__lista_studenti.cautare_student_dupa_id_repo(id_student), self.__lista_discipline.cauta_disciplina_dupa_id_repo(id_disciplina))
            Repository_Note.adauga_nota_repo(self,nota)
            line = f.readline().strip()
        f.close()

    def adauga_nota_repo(self, nota):
        """
        Adauga o nota in repository note
        :param nota: nota
        :return: -
        """
        Repository_Note.adauga_nota_repo(self, nota)
        self.store_to_file()

    def sterge_nota_dupa_id_repo(self, idNota):
        """
        Sterge o nota dupa id din repository note
        :param idNota: int
        :return: -
        """
        Repository_Note.sterge_nota_dupa_id_repo(self, idNota)
        self.store_to_file()

    def modifica_nota_repo(self, nota_noua):
        """
        Modifica valoarea unei note
        :param nota_noua: nota
        :return: -
        """
        Repository_Note.modifica_nota_repo(self, nota_noua)
        self.store_to_file()

    def cauta_nota_dupa_id_repo(self, idNota):
        """
        Returneaza nota cu id-ul dat
        :param idNota: int
        :return: nota
        """
        return Repository_Note.cauta_nota_dupa_id_repo(self, idNota)


    def __len__(self):
        """
        Returneaza lungimea listei de note
        :return: lungime lista note
        """
        return Repository_Note.__len__(self)

    def get_all_repo(self):
        """
        Returneaza lista de note
        :return: lista
        """
        return Repository_Note.get_all_repo(self)