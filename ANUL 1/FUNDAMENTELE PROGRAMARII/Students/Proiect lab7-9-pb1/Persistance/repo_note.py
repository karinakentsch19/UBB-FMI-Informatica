from Exceptions.erori import RepoError


class Repository_Note():

    def __init__(self):
        self.__lista_note = []

    def adauga_nota_repo(self, nota):
        """
        Adauga o nota in repository note
        :param nota: nota
        :return: -
        """
        for nota1 in self.__lista_note:
            if nota1.get_idNota() == nota.get_idNota():
                raise RepoError("Nota existenta!\n")
        self.__lista_note.append(nota)

    def sterge_nota_dupa_id_repo(self, idNota):
        """
        Sterge o nota dupa id din repository note
        :param idNota: int
        :return: -
        """
        for nota in self.__lista_note:
            if nota.get_idNota() == idNota:
                self.__lista_note.remove(nota)
                return
        raise RepoError("Nota inexistenta!\n")

    def modifica_nota_repo(self, nota_noua):
        """
        Modifica valoarea unei note
        :param nota_noua: nota
        :return: -
        """
        for nota in self.__lista_note:
            if nota.get_idNota() == nota_noua.get_idNota():
                nota.set_nota(nota_noua.get_valoare_nota())
                return
        raise RepoError("Nota inexistenta!\n")

    def cauta_nota_dupa_id_repo(self, idNota):
        """
        Returneaza nota cu id-ul dat
        :param idNota: int
        :return: nota
        """
        for nota in self.__lista_note:
            if nota.get_idNota() == idNota:
                return nota
        raise RepoError("Nota inexistenta!\n")


    def __len__(self):
        """
        Returneaza lungimea listei de note
        :return: lungime lista note
        """
        return len(self.__lista_note)

    def get_all_repo(self):
        """
        Returneaza lista de note
        :return: lista
        """
        return self.__lista_note

    def sterge_toata_lista(self):
        self.__lista_note = []