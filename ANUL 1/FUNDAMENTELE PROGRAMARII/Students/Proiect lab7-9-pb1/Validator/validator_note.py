from Exceptions.erori import ValidationError


class Validator_Note:

    def __init__(self):
        pass

    def validator_nota(self, nota):
        """
        Valideaza o nota
        :param nota: nota
        :return: -
        :raise: ValidationError  - daca id <= 0 atunci concatenam stringul "Id invalid!\n" la stringul de erori
                                 - daca valoare_nota <= 0 sau valoare_nota >10 atunci concatenam stringul "Valoare nota invalida!\n" la stringul de erori
        """
        erori = ""
        if nota.get_idNota() <= 0:
            erori += "Id invalid!\n"
        if nota.get_valoare_nota() <= 0 or nota.get_valoare_nota() > 10:
            erori += "Valoare nota invalida!\n"
        if len(erori) > 0:
            raise ValidationError(erori)