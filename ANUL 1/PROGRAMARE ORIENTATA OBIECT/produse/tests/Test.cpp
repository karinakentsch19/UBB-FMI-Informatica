//
// Created by Karina Kentsch on 6/16/2023.
//

#include <cassert>
#include "Test.h"
#include "../errors/RepoError.h"

void Test::ruleaza_toate_testele() {
    ruleaza_teste_domain();
    ruleaza_teste_repo();
    ruleaza_test_validator();
    ruleaza_teste_service();
}

void Test::ruleaza_teste_domain() {
    test_creare_get_domain();
}

void Test::test_creare_get_domain() {
    Produs produs{id, nume, tip, pret};
    assert(produs.get_id() == id);
    assert(produs.get_nume() == nume);
    assert(produs.get_tip() == tip);
    assert(produs.get_pret() == pret);
}

void Test::ruleaza_teste_repo() {
    test_adauga_repo();
    test_sterge_repo();
}

void Test::test_adauga_repo() {
    RepoProdus lista_repo{nume_fisier};
    assert(lista_repo.get_lungime_repo() == 3);
    Produs produs{id, nume, tip, pret};
    lista_repo.adauga_produs_repo(produs);
    assert(lista_repo.get_lungime_repo() == 4);
    try{
        lista_repo.adauga_produs_repo(produs);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Produs existent\n");
    }
    lista_repo.sterge_produs_dupa_id_repo(id);
}

void Test::test_sterge_repo() {
    RepoProdus lista_repo{nume_fisier};
    assert(lista_repo.get_lungime_repo() == 3);
    lista_repo.sterge_produs_dupa_id_repo(3);
    assert(lista_repo.get_lungime_repo() == 2);
    try{
        lista_repo.sterge_produs_dupa_id_repo(3);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Produs inexistent\n");
    }
    lista_repo.adauga_produs_repo({3,"miere","altele",20.3});
}

void Test::ruleaza_test_validator() {
    Validator validator;
    Produs produs{id, nume, tip, pret};
    Produs produs_rau{-1, "", "", 111.1};
    assert(validator.valideaza_produs(produs) == true);
    try{
        validator.valideaza_produs(produs_rau);
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Id invalid\nNume invalid\nTip invalid\nPret invalid\n");
    }
}

void Test::ruleaza_teste_service() {
    test_adauga_service();
    test_filtru_service();
}

void Test::test_adauga_service() {
    RepoProdus lista_repo{nume_fisier};
    Validator validator;
    ServiceProdus lista_service{lista_repo, validator};
    assert(lista_service.get_lungime_service() == 3);
    lista_service.adauga_produs_service(id, nume, tip, pret);
    assert(lista_service.get_lungime_service() == 4);
    lista_service.sterge_dupa_id_service(id);
}

void Test::test_filtru_service() {
    RepoProdus lista_repo{nume_fisier};
    Validator validator;
    ServiceProdus lista_service{lista_repo, validator};
    vector<Produs> lista_filtrata = lista_service.filtru_produse_pret_mai_mic_egal_cu_pret_dat(12);
    assert(lista_filtrata.size() == 1);
}
