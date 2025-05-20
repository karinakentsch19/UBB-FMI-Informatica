from Exceptions.erori import RepoError


class Repository_Discipline:

    def __init__(self):
        self.__lista_discipline = []

    def adauga_disciplina_repo(self, disciplina):
        """
        Adauga o disciplina in lista
        :param disciplina: disciplina
        :return: -
        """
        for disciplina1 in self.__lista_discipline:
            if disciplina1.get_idDisciplina() == disciplina.get_idDisciplina():
                raise RepoError("Disciplina existenta!\n")
        self.__lista_discipline.append(disciplina)

    def sterge_disciplina_dupa_id_repo(self, idDisciplina):
        """
        Sterge disciplina dupa id
        :param idDisciplina: int
        :return: -
        """
        for disciplina in self.__lista_discipline:
            if disciplina.get_idDisciplina() == idDisciplina:
                self.__lista_discipline.remove(disciplina)
                return
        raise RepoError("Disciplina inexistenta!\n")

    def sterge_disciplina_dupa_id_repo_recursiva(self, idDisciplina, index):
        """
        Sterge disciplina dupa id
        :param idDisciplina: int
        :param index: int
        :return: -
        """
        if index >= len(self.__lista_discipline):
            raise RepoError("Disciplina inexistenta!\n")
        if self.__lista_discipline[index].get_idDisciplina() == idDisciplina:
            self.__lista_discipline.remove(self.__lista_discipline[index])
            return
        self.sterge_disciplina_dupa_id_repo_recursiva(idDisciplina, index + 1)

    def modifica_disciplina_repo(self, disciplina_noua):
        """
        Modifica numele si profesorul disciplinei
        :param disciplina_noua: disciplina
        :return: -
        """
        for disciplina in self.__lista_discipline:
            if disciplina.get_idDisciplina() == disciplina_noua.get_idDisciplina():
                disciplina.set_nume(disciplina_noua.get_nume())
                disciplina.set_profesor(disciplina_noua.get_profesor())
                return
        raise RepoError("Disciplina inexistenta!\n")

    def modifica_disciplina_repo_recursiv(self, disciplina_noua, index):
        """
        Modifica numele si profesorul disciplinei
        :param disciplina_noua: disciplina
        :param index: int
        :return: -
        """
        if index >= len(self.__lista_discipline):
            raise RepoError("Disciplina inexistenta!\n")
        if self.__lista_discipline[index].get_idDisciplina() == disciplina_noua.get_idDisciplina():
            self.__lista_discipline[index].set_nume(disciplina_noua.get_nume())
            self.__lista_discipline[index].set_profesor(disciplina_noua.get_profesor())
            return
        self.modifica_disciplina_repo_recursiv(disciplina_noua, index + 1)


    def cauta_disciplina_dupa_id_repo(self, idDisciplina):
        """
        Cauta o disciplina dupa id
        :param idDisciplina: int
        :return: disciplina

        Best-case: cand id_disciplina e pe prima pozitie => O(1)
        Worst-case: cand id_disciplina e pe ultima pozitie => O(n)
        Average complexity: 1+2+...+n/n = n+1/2 => O(n)
        Overall complexity = O(n)
        """
        for disciplina in self.__lista_discipline:
            if disciplina.get_idDisciplina() == idDisciplina:
                return disciplina
        raise RepoError("Disciplina inexistenta!\n")

    def __len__(self):
        """
        Returneaza lungimea listei de discipline
        :return: lungime
        """
        return len(self.__lista_discipline)

    def get_all_repo(self):
        """
        Returneaza lista de discipline
        :return: lista
        """
        return self.__lista_discipline

    def sterge_toata_lista(self):
        self.__lista_discipline = []