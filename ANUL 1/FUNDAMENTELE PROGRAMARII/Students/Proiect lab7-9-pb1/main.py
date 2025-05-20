from unittest import TestLoader, TestResult

from Business.service_discipline import Service_Discipline
from Business.service_note import Service_Note
from Business.service_studenti import Service_Studenti
from Persistance.file_repo_discipline import File_Repository_Discipline
from Persistance.file_repo_note import File_Repository_Note
from Persistance.file_repo_studenti import File_Repository_Studenti
from Persistance.repo_discipline import Repository_Discipline
from Persistance.repo_note import Repository_Note
from Persistance.repo_studenti import Repository_Studenti
from Presentation.ui import UI
from Testing.teste_discipline import Teste_Discipline
from Testing.teste_dto import Teste_DTO
from Testing.teste_generale import Teste
from Testing.teste_note import Teste_Note
from Testing.teste_studenti import Teste_Studenti
from Utilities.BingoSort import BingoSort
from Utilities.MergeSort import  MergeSort
from Validator import validator_discipline
from Validator.validator_discipline import Validator_Discipline
from Validator.validator_note import Validator_Note
from Validator.validator_studenti import Validator_Studenti
import unittest


def main():

    teste = Teste()
    teste.ruleaza_toate_testele()
    loader = TestLoader()
    teste = (Teste_Studenti, Teste_Discipline, Teste_Note, Teste_DTO)
    for test in teste:
        suite = loader.loadTestsFromTestCase(test)
        result = TestResult()
        suite.run(result)
        print(result)
    print()
    print("Alegeti optiunea de stocare: memorie/fisier")
    optiune = input(">>>")
    if optiune == "memorie":
        repo_studenti = Repository_Studenti()
        repo_discipline = Repository_Discipline()
        repo_note = Repository_Note()
    elif optiune == "fisier":
        repo_studenti = File_Repository_Studenti("Studenti.txt")
        repo_discipline = File_Repository_Discipline("Discipline.txt")
        repo_note = File_Repository_Note("Note.txt")
    else:
        print("Comanda invalida!")
        return

    validator_studenti = Validator_Studenti()
    student_service = Service_Studenti(repo_studenti, validator_studenti)
    validator_discipline = Validator_Discipline()
    disciplina_service = Service_Discipline(repo_discipline, validator_discipline)
    validator_note = Validator_Note()
    nota_service = Service_Note(repo_note, validator_note, repo_studenti, repo_discipline)
    consola = UI(student_service, disciplina_service, nota_service)
    consola.ruleaza_ui()


main()