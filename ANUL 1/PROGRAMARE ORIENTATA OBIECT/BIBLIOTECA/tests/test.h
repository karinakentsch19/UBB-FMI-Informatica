//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_TEST_H
#define LAB6_7_BIBLIOTECA_TEST_H
#include "string"
#include "../domain/carte.h"
#include "../repository/RepositoryBiblioteca.h"

using std::string;


class Test {
private:
    int id = 1;
    int an_aparitie = 1999;
    string titlu = "Ana";
    string gen = "SF";
    string autor = "Kentsch Karina";

    int an_aparitie_nou = 2000;
    string titlu_nou = "Maria";
    string gen_nou = "Romance";
    string autor_nou = "Irimies Vasile";

    string fisier = "fisier_test_repo_file.txt";

    /**
     * Ruleaza testele care tin de domain
     */
    void ruleaza_teste_domain();

    /**
     * Testeaza metoda de creare carte
     */
    void test_creaza_carte();

    /**
     * Testeaza metodele de set carte
     */
    void test_set_carte();


    void test_operator_domain();

    /**
     * Ruleaza testele care tin de repo
     */
    void ruleaza_teste_repository();

    /**
     * Testeaza metoda de adaugare repo
     */
    void test_adauga_repo();

    /**
     * Testeaza metoda de get_all repo
     */
    void test_get_all_repo();

    /**
     * Testeaza metoda de sterge repo
     */
    void test_sterge_repo();

    /**
     * Testeaza metoda de modificare repo
     */
    void test_modifica_repo();

    /**
     * Testeaza metoda de cautare repo
     */
    void test_cauta_repo();

    /**
     * Testeaza metoda de validare a unei carti
     */
    void ruleaza_test_validator();

    /**
     * Ruleaza toate testelele care tin de service
     */
    void ruleaza_teste_service();

    /**
     * Testeaza metoda de adaugare service
     */
    void test_adauga_service();

    /**
     * Testeaza metoda de stergere service
     */
    void test_sterge_service();

    /**
     * Testeaza metoda de modificare service
     */
    void test_modifica_service();

    /**
     * Testeaza metoda de cautare service
     */
    void test_cauta_service();

    /**
     * Testeaza metoda de filtrare service
     */
    void test_filtru_service();

    /**
     * Testeaza metoda get_all service
     */
    void test_get_all_service();

    /**
     * Testeaza metoda de compara service
     */
    void test_compara_service();

    /**
     * Testeaza metoda de sortare service
     */
    void test_sort_service();

    void test_map();

    void test_undo_service();

    /**
     * ruleaza toate testele care tin de cos
     */
    void ruleaza_teste_cos();

    /**
     * Testeaza metoda de adaugare cos
     */
    void test_adauga_cos();

    /**
     * Testeaza metoda de golire cos
     */
    void test_goleste_cos();

    /**
    * Testeaza metoda de generare cos
    */
    void test_genereaza_cos();

    /**
     * Testeaza metoda de get cos
     */
    void test_get_cos();

    /**
     * Ruleaza teste service pt cos
     */
    void ruleaza_teste_cos_service();

    /**
     * Testeaza metoda de adaugare cos service
     */
    void test_adauga_cos_service();

    /**
     * Testeaza metoda de golire cos service
     */
    void test_goleste_cos_service();

    /**
    * Testeaza metoda de generare cos service
    */
    void test_genereaza_cos_service();

    /**
     * Testeaza metoda de get cos service
     */
    void test_get_cos_service();

    /**
     * Testeaza metoda de export service
     */
    void test_export_service();

    void ruleaza_teste_file_repo();

    void test_adauga_repo_file();

    void test_sterge_repo_file();

    void test_modifica_repo_file();

    void ruleaza_teste_undo();

    void test_undo_adauga();

    void test_undo_sterge();

    void test_undo_modifica();

public:
    /**
     * Ruleaza toate testele
     */
    void ruleaza_toate_testele();
};


#endif //LAB6_7_BIBLIOTECA_TEST_H
