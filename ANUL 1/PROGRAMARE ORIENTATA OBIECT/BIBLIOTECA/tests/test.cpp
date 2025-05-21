//
// Created by Karina Kentsch on 3/31/2023.
//

#include <cassert>
#include "test.h"
#include "../errors/RepoError.h"
#include "../validator/Validator.h"
#include "../errors/ValidationError.h"
#include "../business/ServiceBiblioteca.h"
#include "../repository/RepositoryBibliotecaFile.h"
#include "fstream"
#include "../undo/ActiuneUndo.h"

using std::to_string;

void Test::ruleaza_teste_domain() {
    test_creaza_carte();
    test_set_carte();
    test_operator_domain();
}

void Test::ruleaza_toate_testele() {
    ruleaza_teste_domain();
    ruleaza_teste_repository();
    ruleaza_test_validator();
    ruleaza_teste_service();
    ruleaza_teste_cos();
    ruleaza_teste_cos_service();
    ruleaza_teste_file_repo();
    ruleaza_teste_undo();
}

void Test::test_creaza_carte() {
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    assert(c.get_titlu() == this->titlu);
    assert(c.get_gen() == this->gen);
    assert(c.get_autor() == this->autor);
    assert(c.get_an_aparitie() == this->an_aparitie);
    assert(c.get_id() == this->id);
}

void Test::test_set_carte() {
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    c_noua.set_titlu(this->titlu_nou);
    c_noua.set_gen(this->gen_nou);
    c_noua.set_autor(this->autor_nou);
    c_noua.set_an_aparitie(this->an_aparitie_nou);
    assert(c_noua.get_titlu() == this->titlu_nou);
    assert(c_noua.get_gen() == this->gen_nou);
    assert(c_noua.get_autor() == this->autor_nou);
    assert(c_noua.get_an_aparitie() == this->an_aparitie_nou);
    assert(c_noua == c);
}

void Test::test_operator_domain() {
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu, this->gen, this->autor, this->an_aparitie, 2);
    assert(!(c == c_noua));
}

void Test::ruleaza_teste_repository() {
    test_adauga_repo();
    test_get_all_repo();
    test_sterge_repo();
    test_modifica_repo();
    test_cauta_repo();
}

void Test::test_adauga_repo() {
    RepositoryBiblioteca lista_carti_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu, this->gen, this->autor, this->an_aparitie, 2);
    lista_carti_repo.adauga_carte_repo(c);
    lista_carti_repo.adauga_carte_repo(c_noua);
    assert(lista_carti_repo.get_lungime_lista_repo() == 2);
    try{
        lista_carti_repo.adauga_carte_repo(c);
    }catch(RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte existenta\n");
    }
}

void Test::test_get_all_repo() {
    RepositoryBiblioteca lista_carti_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu, this->gen, this->autor, this->an_aparitie, 2);
    lista_carti_repo.adauga_carte_repo(c);
    lista_carti_repo.adauga_carte_repo(c_noua);
    const vector <Carte> &lista = lista_carti_repo.get_all_repo();
    assert(lista[0] == c);
    assert(lista[1] == c_noua);
}

void Test::test_sterge_repo() {
    RepositoryBiblioteca lista_carti_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu, this->gen, this->autor, this->an_aparitie, 2);
    lista_carti_repo.adauga_carte_repo(c);
    lista_carti_repo.adauga_carte_repo(c_noua);
    assert(lista_carti_repo.get_lungime_lista_repo() == 2);
    lista_carti_repo.sterge_carte_dupa_id_repo(this->id);
    assert(lista_carti_repo.get_lungime_lista_repo() == 1);
    try{
        lista_carti_repo.sterge_carte_dupa_id_repo(this->id);
    }catch(RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte inexistenta\n");
    }
}

void Test::test_modifica_repo() {
    RepositoryBiblioteca lista_carti_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, this->id);
    lista_carti_repo.adauga_carte_repo(c);
    lista_carti_repo.modificare_carte_repo(c_noua);
    Carte carte = lista_carti_repo.cauta_carte_dupa_id_repo(this->id);
    assert(carte.get_id() == this->id);
    assert(carte.get_an_aparitie() == this->an_aparitie_nou);
    assert(carte.get_autor() == this->autor_nou);
    assert(carte.get_gen() == this->gen_nou);
    assert(carte.get_titlu() == this->titlu_nou);
    lista_carti_repo.sterge_carte_dupa_id_repo(this->id);
    try{
        lista_carti_repo.modificare_carte_repo(c);
    }catch(RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte inexistenta\n");
    }
}

void Test::test_cauta_repo() {
    RepositoryBiblioteca lista_carti_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c_noua(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    lista_carti_repo.adauga_carte_repo(c);
    assert(lista_carti_repo.cauta_carte_dupa_id_repo(this->id) == c);
    try{
        lista_carti_repo.cauta_carte_dupa_id_repo(2);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte inexistenta\n");
    }
}

void Test::ruleaza_test_validator() {
    Carte carte_invalida("", "", "", -12, -123);
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Validator validator;
    assert(validator.valideaza_carte(c) == true);
    try{
        validator.valideaza_carte(carte_invalida);
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Titlu invalid\nGen invalid\nAutor invalid\nAn aparitie invalid\nId invalid\n");
    }
}

void Test::ruleaza_teste_service() {
    test_adauga_service();
    test_sterge_service();
    test_modifica_service();
    test_cauta_service();
    test_get_all_service();
    test_filtru_service();
    test_compara_service();
    test_sort_service();
    test_export_service();
    test_map();
    test_undo_service();
}

void Test::test_adauga_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    assert(lista_service.lungime_lista_service() == 0);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    assert(lista_service.lungime_lista_service() == 1);
    try{
        lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte existenta\n");
    }

    try{
        lista_service.adauga_carte_service("","","",-1,-1);
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Titlu invalid\nGen invalid\nAutor invalid\nAn aparitie invalid\nId invalid\n");
    }
}

void Test::test_sterge_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    assert(lista_service.lungime_lista_service() == 1);
    lista_service.sterge_carte_dupa_id_service(this->id);
    assert(lista_service.lungime_lista_service() == 0);
    try{
        lista_service.sterge_carte_dupa_id_service(this->id);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte inexistenta\n");
    }
}

void Test::test_modifica_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.modificare_carte_service(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, this->id);
    Carte carte = lista_service.cauta_carte_dupa_id_service(this->id);
    assert(carte.get_id() == this->id);
    assert(carte.get_an_aparitie() == this->an_aparitie_nou);
    assert(carte.get_autor() == this->autor_nou);
    assert(carte.get_gen() == this->gen_nou);
    assert(carte.get_titlu() == this->titlu_nou);
    lista_service.sterge_carte_dupa_id_service(this->id);

    try{
        lista_service.modificare_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte inexistenta\n");
    }

    try{
        lista_service.modificare_carte_service("", "", "", -1, -1);
    }catch (ValidationError& eroare){
        assert(eroare.get_mesaj() == "Titlu invalid\nGen invalid\nAutor invalid\nAn aparitie invalid\nId invalid\n");
    }
}

void Test::test_cauta_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    assert(lista_service.cauta_carte_dupa_id_service(this->id) == c);
    try{
        lista_service.cauta_carte_dupa_id_service(2);
    }catch (RepoError& eroare){
        assert(eroare.get_mesaj() == "Carte inexistenta\n");
    }
}

void Test::test_get_all_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    const vector <Carte> &lista = lista_service.get_all_service();
    assert(lista[0] == c);
}

void Test::test_filtru_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);

    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c2(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_carte_service(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);

    vector <Carte> lista_carti = lista_service.filtrare_carti_dupa_un_criteriu_service("titlu", this->titlu, this->an_aparitie);
    assert(lista_carti[0] == c);

    vector <Carte> lista_carti2 = lista_service.filtrare_carti_dupa_un_criteriu_service("an", this->titlu, this->an_aparitie_nou);
    assert(lista_carti2[0] == c2);
}

void Test::test_compara_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);

    Carte c1(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c2(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    Carte c3(this->titlu, this->gen_nou, this->autor, this->an_aparitie, 3);
    assert(lista_service.compara(c1,c2,"titlu") == true);
    assert(lista_service.compara(c2,c1,"titlu") == false);
    assert(lista_service.compara(c1,c2,"autor") == false);
    assert(lista_service.compara(c2,c1,"autor") == true);
    assert(lista_service.compara(c1,c2,"an") == true);
    assert(lista_service.compara(c2,c1,"an") == false);
    assert(lista_service.compara(c1,c2,"gen") == false);
    assert(lista_service.compara(c2,c1,"gen") == true);
    assert(lista_service.compara(c2,c1,"") == false);
    assert(lista_service.compara(c1,c2, "an+gen") == true);
    assert(lista_service.compara(c2,c1, "an+gen") == false);
    assert(lista_service.compara(c1,c3, "an+gen") == false);
    assert(lista_service.compara(c3,c1, "an+gen") == true);

}

void Test::test_sort_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);

    Carte c1(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c2(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_carte_service(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);

    lista_service.sortare_lista_carti_dupa_un_criteriu("autor");
    const vector <Carte> &lista_sortata = lista_service.get_all_service();
    assert(lista_sortata[0] == c2);
    assert(lista_sortata[1] == c1);

    lista_service.sortare_lista_carti_dupa_un_criteriu("titlu");
    const vector <Carte> &lista_sortata2 = lista_service.get_all_service();
    assert(lista_sortata2[0] == c1);
    assert(lista_sortata2[1] == c2);

    lista_service.sortare_lista_carti_dupa_un_criteriu("an");
    const vector <Carte> &lista_sortata3 = lista_service.get_all_service();
    assert(lista_sortata3[0] == c1);
    assert(lista_sortata3[1] == c2);

    lista_service.sortare_lista_carti_dupa_un_criteriu("gen");
    const vector <Carte> &lista_sortata4 = lista_service.get_all_service();
    assert(lista_sortata4[0] == c2);
    assert(lista_sortata4[1] == c1);

    lista_service.sterge_carte_dupa_id_service(this->id);
    lista_service.sterge_carte_dupa_id_service(2);

    Carte c3(this->titlu, "b", this->autor, 1, this->id);
    Carte c4(this->titlu_nou, "a", this->autor_nou, 1, 2);
    lista_service.adauga_carte_service(this->titlu, "b", this->autor, 1, this->id);
    lista_service.adauga_carte_service(this->titlu_nou, "a", this->autor_nou, 1, 2);

    lista_service.sortare_lista_carti_dupa_un_criteriu("an+gen");
    const vector <Carte> &lista_sortata5 = lista_service.get_all_service();
    assert(lista_sortata5[0] == c4);
    assert(lista_sortata5[1] == c3);


}

void Test::ruleaza_teste_cos() {
    test_adauga_cos();
    test_goleste_cos();
    test_genereaza_cos();
    test_get_cos();
}

void Test::test_adauga_cos() {
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    CosCarti cos;
    assert(cos.size_cos() == 0);
    cos.adauga_cos(c);
    assert(cos.size_cos() == 1);
}

void Test::test_goleste_cos(){
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    CosCarti cos;
    cos.adauga_cos(c);
    assert(cos.size_cos() == 1);
    cos.goleste_cos();
    assert(cos.size_cos() == 0);
}

void Test::test_genereaza_cos() {
    vector <Carte> lista;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista.push_back(c);
    lista.push_back(c);
    lista.push_back(c);
    CosCarti cos;
    cos.genereaza_cos(lista, 3);
    vector <Carte> lista_carti = cos.get_cos();
    assert(lista_carti[0] == c);
    assert(lista_carti[1] == c);
    assert(lista_carti[2] == c);
}

void Test::test_get_cos() {
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    CosCarti cos;
    cos.adauga_cos(c);
    vector <Carte> lista = cos.get_cos();
    assert(lista[0] == c);
}

void Test::ruleaza_teste_cos_service() {
    test_adauga_cos_service();
    test_goleste_cos_service();
    test_genereaza_cos_service();
    test_get_cos_service();
}

void Test::test_adauga_cos_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    assert(lista_service.size_cos_service() == 0);
    lista_service.adauga_cos_service(this->titlu);
    assert(lista_service.size_cos_service() == 1);
}

void Test::test_goleste_cos_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_cos_service(this->titlu);
    assert(lista_service.size_cos_service() == 1);
    lista_service.goleste_cos_service();
    assert(lista_service.size_cos_service() == 0);
}

void Test::test_genereaza_cos_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.genereaza_cos_service(2);
    assert(lista_service.size_cos_service() == 2);
}

void Test::test_get_cos_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    Carte c1(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte c2(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_carte_service(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    lista_service.adauga_cos_service(this->titlu);
    lista_service.adauga_cos_service(this->titlu_nou);
    const vector<Carte> &lista = lista_service.get_cos_service();
    assert(lista[0] == c1);
    assert(lista[1] == c2);
}

void Test::test_export_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);

    Carte c1(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_cos_service(this->titlu);

    lista_service.export_in_fisier("fisier_test.txt");
    std::ifstream fin("fisier_test.txt");
    string carte, linie;
    char linie_char[1001];
    fin.getline(linie_char, 1000);
    linie = linie_char;
    carte = to_string(this->id) + "," + this->titlu + "," + this->autor + "," + this->gen + "," + to_string(this->an_aparitie);
    assert(linie == carte);
}

void Test::ruleaza_teste_file_repo() {
    test_adauga_repo_file();
    test_sterge_repo_file();
    test_modifica_repo_file();
}

void Test::test_adauga_repo_file() {
    RepositoryBibliotecaFile lista_carti_file{this->fisier};
    assert(lista_carti_file.get_lungime_lista_repo() == 2);
    Carte c{"a", "b" , "kentsch karina", 20, 3};
    lista_carti_file.adauga_carte_repo(c);
    assert(lista_carti_file.get_lungime_lista_repo() == 3);
    lista_carti_file.sterge_carte_dupa_id_repo(3);
}

void Test::test_sterge_repo_file() {
    RepositoryBibliotecaFile lista_carti_file{this->fisier};
    Carte c{"a", "b" , "kentsch karina", 20, 3};
    lista_carti_file.adauga_carte_repo(c);
    assert(lista_carti_file.get_lungime_lista_repo() == 3);
    lista_carti_file.sterge_carte_dupa_id_repo(3);
    assert(lista_carti_file.get_lungime_lista_repo() == 2);
}

void Test::test_modifica_repo_file() {
    RepositoryBibliotecaFile lista_carti_file{this->fisier};
    Carte c{"a", "b" , "kentsch karina", 20, 3};
    lista_carti_file.adauga_carte_repo(c);
    Carte c2{"b", "c" , "kentsch eu", 21, 3};
    lista_carti_file.modificare_carte_repo(c2);
    Carte gasita = lista_carti_file.cauta_carte_dupa_id_repo(3);
    assert(gasita.get_titlu() == "b");
    assert(gasita.get_gen() == "c");
    assert(gasita.get_autor() == "kentsch eu");
    assert(gasita.get_an_aparitie() == 21);
    assert(gasita.get_id() == 3);
    lista_carti_file.sterge_carte_dupa_id_repo(3);
}

void Test::ruleaza_teste_undo() {
    test_undo_adauga();
    test_undo_sterge();
    test_undo_modifica();
}

void Test::test_undo_adauga() {
    RepositoryBiblioteca lista_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_repo.adauga_carte_repo(c);
    assert(lista_repo.get_lungime_lista_repo() == 1);
    UndoAdauga undo{lista_repo, c};
    undo.doUndo();
    assert(lista_repo.get_lungime_lista_repo() == 0);
}

void Test::test_undo_sterge() {
    RepositoryBiblioteca lista_repo;
    Carte c(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    assert(lista_repo.get_lungime_lista_repo() == 0);
    UndoSterge undo{lista_repo, c};
    undo.doUndo();
    assert(lista_repo.get_lungime_lista_repo() == 1);
}

void Test::test_undo_modifica() {
    RepositoryBiblioteca lista_repo;
    Carte carte_veche(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    Carte carte_noua(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, this->id);
    lista_repo.adauga_carte_repo(carte_noua);
    UndoModifica undo{lista_repo, carte_veche};
    undo.doUndo();
    Carte carte_gasita = lista_repo.cauta_carte_dupa_id_repo(this->id);
    assert(carte_gasita.get_titlu() == this->titlu);
    assert(carte_gasita.get_gen() == this->gen);
    assert(carte_gasita.get_autor() == this->autor);
    assert(carte_gasita.get_an_aparitie() == this->an_aparitie);
}

void Test::test_map() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_carte_service(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    lista_service.adauga_carte_service(this->titlu_nou, "a", "b", 3131, 3);
    map<string,vector<Carte>> dictionar = lista_service.map_cheie_titlu();
    assert(dictionar[this->titlu].size() == 1);
    assert(dictionar[this->titlu_nou].size() == 2);
}

void Test::test_undo_service() {
    RepositoryBiblioteca lista_repo;
    Validator validator;
    ServiceBiblioteca lista_service(lista_repo, validator);
    lista_service.adauga_carte_service(this->titlu, this->gen, this->autor, this->an_aparitie, this->id);
    lista_service.adauga_carte_service(this->titlu_nou, this->gen_nou, this->autor_nou, this->an_aparitie_nou, 2);
    assert(lista_service.lungime_lista_service() == 2);
    lista_service.undo();
    assert(lista_service.lungime_lista_service() == 1);
    assert(lista_service.lungime_lista_undo() == 1);
}




