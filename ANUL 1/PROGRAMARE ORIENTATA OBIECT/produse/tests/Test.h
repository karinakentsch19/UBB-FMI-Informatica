//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_TEST_H
#define PRODUSE_TEST_H
#include "../business/ServiceProdus.h"

class Test {
private:
    int id = 10;
    string nume = "mar";
    string tip = "fruct";
    double pret = 1.4;

    string nume_fisier = "test.txt";

    void ruleaza_teste_domain();

    void test_creare_get_domain();

    void ruleaza_teste_repo();

    void test_adauga_repo();

    void test_sterge_repo();

    void ruleaza_test_validator();

    void ruleaza_teste_service();

    void test_adauga_service();

    void test_filtru_service();

public:
    Test(){}

    void ruleaza_toate_testele();
};


#endif //PRODUSE_TEST_H
