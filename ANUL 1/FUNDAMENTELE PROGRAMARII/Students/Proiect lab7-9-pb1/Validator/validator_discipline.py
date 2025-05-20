from Exceptions.erori import ValidationError


class Validator_Discipline:

    def __init__(self):
        pass

    def validator_disciplina(self, disciplina):
        """
        Verifica daca atributele unei discipline sunt corecte
        :param disciplina: disciplina
        :return: -
        :raise: ValidationError - daca idDisciplina <= 0 atunci concatenam stringul "Id invalid!\n" la stringul de erori
                                - daca nume == "" sau contine cifre atunci concatenam stringul "Nume invalid!\n" la stringul de erori
                                - daca profesor == "" sau contine cifre atunci concatenam stringul "Profesor invalid!\n" la stringul de erori
        """
        erori = ""
        if disciplina.get_idDisciplina() <= 0:
            erori += "Id invalid!\n"

        if disciplina.get_nume() == "":
            erori += "Nume invalid!\n"
        else:
            nume = disciplina.get_nume()
            if nume.isalpha() == False:
                erori += "Nume invalid!\n"

        if disciplina.get_profesor() == "":
            erori += "Profesor invalid!\n"
        else:
            profesor = disciplina.get_profesor()
            profesor = profesor.split()
            if profesor[0].isalpha() == False or profesor[1].isalpha() == False:
                erori += "Profesor invalid!\n"
        if len(erori) > 0:
            raise ValidationError(erori)
