//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_TEST_H
#define MELODII2_TEST_H
#include "../business/ServiceMelodii.h"
#include "../validation/Validator.h"


class Test {
private:
    int id = 10;
    string titlu = "Fereastra";
    string artist = "maia";
    string gen = "folk";
    string nume_fisier = "test.txt";

    void ruleaza_teste_domain();

    void test_creare_get_domain();

    void ruleaza_teste_repo();

    void test_adauga_repo();

    void test_sterge_repo();

    void ruleaza_test_validator();

    void ruleaza_teste_service();

    void test_adauga_service();

    void test_sterge_service();

public:
    Test(){}

    void ruleaza_toate_testele();
};


#endif //MELODII2_TEST_H
