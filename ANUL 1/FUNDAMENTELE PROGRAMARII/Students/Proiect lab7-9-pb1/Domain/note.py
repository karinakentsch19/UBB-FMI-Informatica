class Nota:

    def __init__(self, idNota, valoare_nota, student, disciplina):
        self.__idNota = idNota
        self.__valoare_nota = valoare_nota
        self.__student = student
        self.__disciplina = disciplina

    def set_nota(self, nota_noua):
        """
        Seteaza o nota
        :param nota_noua: float
        :return: -
        """
        self.__valoare_nota = nota_noua

    def get_idNota(self):
        """
        Returneaza id-ul unei note
        :return: id
        """
        return self.__idNota

    def get_valoare_nota(self):
        """
        Returneaza valoarea unei note
        :return: nota
        """
        return self.__valoare_nota

    def get_student(self):
        """
        Returneaza studentul care are nota
        :return: id student
        """
        return self.__student

    def get_disciplina(self):
        """
        Returneaza  disciplia care are nota
        :return: id disciplina
        """
        return self.__disciplina

    def __str__(self):
        """
        Returneaza nota sub forma de string
        :return: nota
        """
        nume_student = self.__student.get_nume()
        nume_disciplina = self.__disciplina.get_nume()
        return f"Student: {nume_student}\nDisciplina: {nume_disciplina}\nNota: {self.__valoare_nota}\n"

    def __eq__(self, other):
        """
        Verifica daca doua note au id egale
        :param other: nota
        :return: True  - daca notele au id egal
                 False - daca notele nu au id egal
        """
        return self.__idNota == other.__idNota

