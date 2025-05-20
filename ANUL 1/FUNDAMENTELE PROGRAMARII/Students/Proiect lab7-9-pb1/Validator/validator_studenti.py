from Exceptions.erori import ValidationError


class Validator_Studenti:

    def __init__(self):
        pass

    def validare_student(self, student):
        """
        Verifica daca studentul are atributele corect definite
        :return: -
        :raise: ValidationError - daca idStudent <= 0 concatenam stringul "Id invalid!\n" la stringul de erori
                                - daca nume = "" sau numele contine cifre concatenam stringul "Nume invalid!\n" la stringul de erori
        """
        erori = ""
        if student.get_idStudent() <= 0:
            erori += "Id invalid!\n"
        if student.get_nume() == "":
            erori += "Nume invalid!\n"
        else:
            nume = student.get_nume()
            nume = nume.split()
            if nume[0].isalpha() == False or nume[1].isalpha() == False:
                erori += "Nume invalid!\n"
        if len(erori) > 0:
            raise ValidationError(erori)