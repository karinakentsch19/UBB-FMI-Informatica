class DTO:

    def __init__(self, __student):
        self.__student = __student
        self.__lista_note = []

    def get_student_dto(self):
        """
        Returneaza student DTO
        :return: student
        """
        return self.__student

    def get_lista_note_dto(self):
        """
        Returneaza lista de note a unui student DTO
        :return: lista note
        """
        return self.__lista_note

    def set_lista_note_dto(self, nota):
        """
        Adauga o nota in lista de note DTO
        :param nota: nota
        :return: -
        """
        self.__lista_note.append(nota)

    def lungime_lista_note_dto(self):
        """
        Returneaza lungimea listei de note DTO
        :return: lungime lista
        """
        return len(self.__lista_note)

    def __str__(self):
        """
        Returneaza studentul si notele lui
        :return: student si note
        """
        return f"Studentul: {self.__student}\nNote: {self.__lista_note}\n"

    def __eq__(self, other):
        """
        Verifica daca doua obiecte dto au id studenti egali
        :param other: dto
        :return: True - id-uri egale
                 False - id-uri diferite
        """
        return self.__student == other.__student

    def medie_note_dto(self):
        """
        Returneaza media notelor float a unui student DTO
        :return: media
        """
        suma = sum(self.__lista_note)
        return suma/self.lungime_lista_note_dto()
