//
// Created by Karina Kentsch on 6/14/2023.
//

#include <cassert>
#include "Test.h"
#include "../errors/RepoError.h"
#include "../errors/ValidationError.h"
#include "../errors/ServiceError.h"

void Test::ruleaza_toate_testele() {
    ruleaza_teste_domain();
    ruleaza_teste_repo();
    ruleaza_test_validator();
    ruleaza_teste_service();
}

void Test::ruleaza_teste_domain() {
    test_creaza_get_set_domain();
}

void Test::test_creaza_get_set_domain() {
    Melodie melodie{id, titlu, artist, rank};
    assert(melodie.get_id() == id);
    assert(melodie.get_titlu() == titlu);
    assert(melodie.get_artist() == artist);
    assert(melodie.get_rank() == rank);

    Melodie melodie_noua{id, titlu, artist, rank};
    melodie_noua.set_titlu(titlu_nou);
    melodie_noua.set_rank(rank_nou);
    assert(melodie_noua.get_id() == id);
    assert(melodie_noua.get_titlu() == titlu_nou);
    assert(melodie_noua.get_artist() == artist);
    assert(melodie_noua.get_rank() == rank_nou);
}

void Test::ruleaza_teste_repo() {
    test_adauga_repo();
    test_sterge_repo();
    test_modifica_repo();
}

void Test::test_adauga_repo() {
    RepoMelodii lista_repo{nume_fisier};
    Melodie melodie{id, titlu, artist, rank};
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
    lista_repo.adauga_melodie_repo({3,"morisca","aurel mali",4});
}

void Test::test_modifica_repo() {
    RepoMelodii lista_repo{nume_fisier};
    Melodie melodie{id, titlu, artist, rank};
    Melodie melodie_noua{id, titlu_nou, artist, rank_nou};
    lista_repo.adauga_melodie_repo(melodie);
    assert(lista_repo.get_lungime_repo() == 4);
    lista_repo.modifica_melodie_repo(melodie_noua);
    assert(lista_repo.get_lungime_repo() == 4);
    lista_repo.sterge_melodie_dupa_id_repo(id);
    try{
        lista_repo.modifica_melodie_repo(melodie_noua);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Melodie inexistenta\n");
    }
}

void Test::ruleaza_test_validator() {
    Validator validator;
    Melodie melodie{id, titlu, artist, rank};
    assert(validator.valideaza_melodie(melodie) == true);
    Melodie melodie_invalida{-1,"","",11};
    try{
        validator.valideaza_melodie(melodie_invalida);
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Id invalid\nTitlu invalid\nArtist invalid\nRank invalid\n");
    }
}

void Test::ruleaza_teste_service() {
    test_adauga_service();
    test_sterge_service();
    test_modifica_service();
    test_nr_melodii_service();
}

void Test::test_adauga_service() {
    RepoMelodii lista_repo{nume_fisier};
    Validator validator;
    ServiceMelodii lista_service{lista_repo, validator};
    assert(lista_service.get_lungime_service() == 3);
    lista_service.adauga_melodie_service(id, titlu, "aurel mali", rank);
    assert(lista_service.get_lungime_service() == 4);
    lista_service.sterge_dupa_id_service(id);
    try{
        validator.valideaza_melodie({-1,"","",11});
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Id invalid\nTitlu invalid\nArtist invalid\nRank invalid\n");
    }
}

void Test::test_sterge_service() {
    RepoMelodii lista_repo{nume_fisier};
    Validator validator;
    ServiceMelodii lista_service{lista_repo, validator};
    assert(lista_service.get_lungime_service() == 3);
    lista_service.adauga_melodie_service(id, titlu, "aurel mali", rank);
    assert(lista_service.get_lungime_service() == 4);
    lista_service.sterge_dupa_id_service(id);
    assert(lista_service.get_lungime_service() == 3);
    try{
        lista_service.sterge_dupa_id_service(1);
    }catch (ServiceError& eroare){
        assert(eroare.get_mesaj() == "Ultima melodie\n");
    }
}

void Test::test_modifica_service() {
    RepoMelodii lista_repo{nume_fisier};
    Validator validator;
    ServiceMelodii lista_service{lista_repo, validator};
    assert(lista_service.get_lungime_service() == 3);
    lista_service.adauga_melodie_service(id, titlu, "aurel mali", rank);
    assert(lista_service.get_lungime_service() == 4);
    lista_service.sterge_dupa_id_service(id);
    assert(lista_service.get_lungime_service() == 3);
    try{
        validator.valideaza_melodie({-1,"","",11});
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Id invalid\nTitlu invalid\nArtist invalid\nRank invalid\n");
    }
}

void Test::test_nr_melodii_service() {
    RepoMelodii lista_repo{nume_fisier};
    Validator validator;
    ServiceMelodii lista_service{lista_repo, validator};
    assert(lista_service.nr_melodii_cu_rank_dat(4) == 1);
}
