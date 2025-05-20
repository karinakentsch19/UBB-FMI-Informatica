from Domain.discipline import Disciplina
from Persistance.repo_discipline import Repository_Discipline


class File_Repository_Discipline(Repository_Discipline):

    def __init__(self, __fisier):
        self.fisier = __fisier
        Repository_Discipline.__init__(self)
        self.load_from_file()

    def adauga_disciplina_repo(self, disciplina):
        """
        Adauga o disciplina in lista
        :param disciplina: disciplina
        :return: -
        """
        Repository_Discipline.adauga_disciplina_repo(self, disciplina)
        self.store_to_file()

    def sterge_disciplina_dupa_id_repo(self, idDisciplina):
        """
        Sterge disciplina dupa id
        :param idDisciplina: int
        :return: -
        """
        #Repository_Discipline.sterge_disciplina_dupa_id_repo(self, idDisciplina)
        Repository_Discipline.sterge_disciplina_dupa_id_repo_recursiva(self, idDisciplina, 0)
        self.store_to_file()


    def modifica_disciplina_repo(self, disciplina_noua):
        """
        Modifica numele si profesorul disciplinei
        :param disciplina_noua: disciplina
        :return: -
        """
        #Repository_Discipline.modifica_disciplina_repo(self, disciplina_noua)
        Repository_Discipline.modifica_disciplina_repo_recursiv(self, disciplina_noua, 0)
        self.store_to_file()

    def cauta_disciplina_dupa_id_repo(self, idDisciplina):
        """
        Cauta o disciplina dupa id
        :param idDisciplina: int
        :return: disciplina
        """
        return Repository_Discipline.cauta_disciplina_dupa_id_repo(self, idDisciplina)

    def __len__(self):
        """
        Returneaza lungimea listei de discipline
        :return: lungime
        """
        return Repository_Discipline.__len__(self)

    def get_all_repo(self):
        """
        Returneaza lista de discipline
        :return: lista
        """
        return Repository_Discipline.get_all_repo(self)

    def store_to_file(self):
        """
        Scrie in fisier lista de studenti
        :return: -
        """
        f = open(self.fisier, "w")
        lista_discipline = Repository_Discipline.get_all_repo(self)
        for disciplina in lista_discipline:
            string_disciplina = f"{disciplina.get_idDisciplina()},{disciplina.get_nume()},{disciplina.get_profesor()},\n"
            f.write(string_disciplina)
        f.close()

    def load_from_file(self):
        """
        Incarci din fisier lista de studenti
        :return: -
        """
        f = open(self.fisier, "r")
        Repository_Discipline.sterge_toata_lista(self)
        line = f.readline().strip()
        while line != "":
            parametri = line.split(",")
            idDisciplina = int(parametri[0])
            nume = parametri[1]
            profesor = parametri[2]
            disciplina = Disciplina(idDisciplina, nume, profesor)
            Repository_Discipline.adauga_disciplina_repo(self,disciplina)
            line = f.readline().strip(",")
        f.close()