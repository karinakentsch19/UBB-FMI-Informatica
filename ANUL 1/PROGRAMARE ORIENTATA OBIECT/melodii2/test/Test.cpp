//
// Created by Karina Kentsch on 6/15/2023.
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
    Melodie melodie{id, titlu, artist, gen};
    assert(melodie.get_id() == id);
    assert(melodie.get_titlu() == titlu);
    assert(melodie.get_artist() == artist);
    assert(melodie.get_gen() == gen);
}

void Test::ruleaza_teste_repo() {
    test_adauga_repo();
    test_sterge_repo();
}

void Test::test_adauga_repo() {
    RepoMelodii lista_repo{nume_fisier};
    Melodie melodie{id, titlu, artist, gen};
    assert(lista_repo.get_lungime_repo() == 3);
    lista_repo.adauga_melodie_repo(melodie);
    assert(lista_repo.get_lungime_repo() == 4);
    try{
        lista_repo.adauga_melodie_repo(melodie);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Melodie existenta\n");
    }
    lista_repo.sterge_melodie_dupa_id_repo(id);
}

void Test::test_sterge_repo() {
    RepoMelodii lista_repo{nume_fisier};
    assert(lista_repo.get_lungime_repo() == 3);
    lista_repo.sterge_melodie_dupa_id_repo(3);
    assert(lista_repo.get_lungime_repo() == 2);
    try{
        lista_repo.sterge_melodie_dupa_id_repo(3);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Melodie inexistenta\n");
    }
    Melodie melodie{3,"oua","eu","pop"};
    lista_repo.adauga_melodie_repo(melodie);
}

void Test::ruleaza_test_validator() {
    Validator validator;
    Melodie melodie{id, titlu, artist, gen};
    assert(validator.valideaza_melodie(melodie) == true);
    try{
        validator.valideaza_melodie({-1,"","","as"});
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Id invalid\nTitlu invalid\nArtist invalid\nGen invalid\n");
    }
}

void Test::ruleaza_teste_service() {
    test_adauga_service();
    test_sterge_service();
}

void Test::test_adauga_service() {
    RepoMelodii lista_repo{nume_fisier};
    Validator validator;
    ServiceMelodii lista_service{lista_repo, validator};
    assert(lista_service.get_lungime_service() == 3);
    lista_service.adauga_melodie_service(titlu, artist, gen);
    assert(lista_service.get_lungime_service() == 4);
    lista_service.sterge_dupa_id_service(4);
}

void Test::test_sterge_service() {
    RepoMelodii lista_repo{nume_fisier};
    Validator validator;
    ServiceMelodii lista_service{lista_repo, validator};
    assert(lista_service.get_lungime_service() == 3);
    lista_service.sterge_dupa_id_service(3);
    assert(lista_service.get_lungime_service() == 2);
    lista_service.adauga_melodie_service("oua","eu","pop");
}
