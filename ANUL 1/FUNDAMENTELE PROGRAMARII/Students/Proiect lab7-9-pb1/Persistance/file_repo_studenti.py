from Domain.studenti import Student
from Persistance.repo_studenti import Repository_Studenti


class File_Repository_Studenti(Repository_Studenti):

    def __init__(self, __fisier):
        #self.__lista_studenti = []
        self.fisier = __fisier
        Repository_Studenti.__init__(self)
        self.load_from_file()

    def adauga_student_repo(self, student):
        """
        Adauga un student in repository studenti ("lista de studenti")
        """
        Repository_Studenti.adauga_student_repo(self,student)
        self.store_to_file()


    def sterge_student_dupa_id_repo(self, idStudent):
        """
        Sterge un student dupa id din repository studenti
        """
        Repository_Studenti.sterge_student_dupa_id_repo(self,idStudent)
        self.store_to_file()


    def modifica_nume_student_repo(self, student_nou):
        """
        Modifica numele unui student din repository studenti
        """
        Repository_Studenti.modifica_nume_student_repo(self, student_nou)
        self.store_to_file()

    def cautare_student_dupa_id_repo(self, idStudent):
        """
        Cauta student dupa id in repository studenti
        :return: student
        """
        return Repository_Studenti.cautare_student_dupa_id_repo(self, idStudent)


    def __len__(self):
        """
        Returneaza lungimea listei de studenti
        :return: lungime - int
        """
        return Repository_Studenti.__len__(self)

    def get_all(self):
        """
        Returneaza lista de studenti
        :return: lista
        """
        return Repository_Studenti.get_all(self)

    def store_to_file(self):
        """
        Scrie in fisier lista de studenti
        :return: -
        """
        f = open(self.fisier, "w")
        lista_studenti = Repository_Studenti.get_all(self)
        for student in lista_studenti:
            string_student = f"{student.get_idStudent()},{student.get_nume()}\n"
            f.write(string_student)
        f.close()


    def load_from_file(self):
        """
        Incarci din fisier lista de studenti
        :return:
        """
        f = open(self.fisier, "r")
        Repository_Studenti.sterge_toata_lista(self)
        line = f.readline().strip()
        while line != "":
            parametri = line.split(",")
            idStudent = int(parametri[0])
            numeStudent = parametri[1]
            student = Student(idStudent,numeStudent)
            Repository_Studenti.adauga_student_repo(self,student)
            line = f.readline().strip()
        f.close()