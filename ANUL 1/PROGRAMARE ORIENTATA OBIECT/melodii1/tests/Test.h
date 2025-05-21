//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_TEST_H
#define MELODII1_TEST_H
#include "../business/ServiceMelodii.h"

class Test {
private:
    int id = 10;
    string titlu = "OK";
    string artist = "Virgilius Menutz";
    int rank = 10;

    string titlu_nou = "ok";
    string artist_nou = "Aurel Menutz";
    int rank_nou = 2;

    string nume_fisier = "test.txt";

    void ruleaza_teste_domain();

    void test_creaza_get_set_domain();

    void ruleaza_teste_repo();

    void test_adauga_repo();

    void test_sterge_repo();

    void test_modifica_repo();

    void ruleaza_test_validator();

    void ruleaza_teste_service();

    void test_adauga_service();

    void test_sterge_service();

    void test_modifica_service();

    void test_nr_melodii_service();

public:
    Test(){}

    void ruleaza_toate_testele();
};


#endif //MELODII1_TEST_H
