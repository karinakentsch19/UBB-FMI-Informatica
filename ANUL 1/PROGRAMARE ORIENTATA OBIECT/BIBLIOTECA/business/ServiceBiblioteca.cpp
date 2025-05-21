//
// Created by Karina Kentsch on 3/31/2023.
//

#include "ServiceBiblioteca.h"
#include "../errors/RepoError.h"
#include "iostream"
#include "fstream"
#include "algorithm"
using std::cout;
using std::swap;
using std::copy_if;
using std::sort;

const vector <Carte>& ServiceBiblioteca::get_all_service() const{
    return this->lista_carti_service.get_all_repo();
}

int ServiceBiblioteca::lungime_lista_service() const {
    return this->lista_carti_service.get_lungime_lista_repo();
}

void ServiceBiblioteca::adauga_carte_service(string titlu, string gen, string autor, int an_aparitie, int id) {
    Carte carte(titlu, gen, autor, an_aparitie, id);
    if (this->validator.valideaza_carte(carte)) {
        lista_carti_service.adauga_carte_repo(carte);
        ActiuneUndo* undo_adauga = new UndoAdauga{lista_carti_service, carte};
        this->lista_undo.push_back(undo_adauga);
        notify();
    }
}

void ServiceBiblioteca::sterge_carte_dupa_id_service(int id) {
    Carte carte = lista_carti_service.cauta_carte_dupa_id_repo(id);
    lista_carti_service.sterge_carte_dupa_id_repo(id);
    ActiuneUndo* undo_sterge = new UndoSterge{lista_carti_service, carte};
    this->lista_undo.push_back(undo_sterge);
    notify();
}

void ServiceBiblioteca::modificare_carte_service(string titlu_nou, string gen_nou, string autor_nou, int an_aparitie_nou,
                                            int id) {
    Carte carte_noua(titlu_nou, gen_nou, autor_nou, an_aparitie_nou, id);
    if (this->validator.valideaza_carte(carte_noua)) {
        Carte carte_veche = lista_carti_service.cauta_carte_dupa_id_repo(id);
        lista_carti_service.modificare_carte_repo(carte_noua);
        ActiuneUndo* undo_modifica = new UndoModifica{lista_carti_service, carte_veche};
        this->lista_undo.push_back(undo_modifica);
        notify();
    }
}

const Carte& ServiceBiblioteca::cauta_carte_dupa_id_service(int id) const {
    return lista_carti_service.cauta_carte_dupa_id_repo(id);
}

vector<Carte> ServiceBiblioteca::filtrare_carti_dupa_un_criteriu_service(string criteriu, string titlu_dat, int an_aparitie_dat) {
    vector <Carte> lista_filtrata;
//    if (criteriu == "titlu"){
//        for (const auto & carte : lista_carti_service.get_all_repo())
//            if (carte.get_titlu() == titlu_dat)
//                lista_filtrata.push_back(carte);
//    }
//    else
//        if (criteriu == "an"){
//            for (const auto & carte : lista_carti_service.get_all_repo())
//                if (carte.get_an_aparitie() == an_aparitie_dat)
//                     lista_filtrata.push_back(carte);
//        }

    const vector <Carte>& lista_carti = this->lista_carti_service.get_all_repo();
    if(criteriu == "titlu"){
        //copy_if returneaza iteratorul la inceputul listei
        copy_if(lista_carti.begin(), lista_carti.end(), std::back_inserter(lista_filtrata), [=](const Carte& c){
            return c.get_titlu() == titlu_dat;
        });
    }
    else
        if (criteriu == "an"){
            copy_if(lista_carti.begin(), lista_carti.end(), std::back_inserter(lista_filtrata), [=](const Carte& c){
                return c.get_an_aparitie() == an_aparitie_dat;
            });
        }
    return lista_filtrata;
}


void ServiceBiblioteca::sortare_lista_carti_dupa_un_criteriu(string criteriu) {
//    vector <Carte> lista_carti;
//    for (auto & carte: lista_carti_service.get_all_repo())
//        lista_carti.push_back(carte);
//
//    for (int i = 0; i < lista_carti.size() - 1; i++)
//        for (int j = i + 1; j < lista_carti.size(); j++)
//            if (!compara(lista_carti[i], lista_carti[j], criteriu))
//                swap(lista_carti[i], lista_carti[j]);
//
//    for (int i = 0; i < lista_carti.size(); i++)
//        this->lista_carti_service.sterge_carte_dupa_id_repo(lista_carti[i].get_id());
//
//    for (int i = 0; i < lista_carti.size(); i++)
//        this->lista_carti_service.adauga_carte_repo(lista_carti[i]);


    auto lista_carti = this->lista_carti_service.get_all_repo();
    if (criteriu == "titlu")
        sort(lista_carti.begin(), lista_carti.end(), [](const Carte& c1, const Carte& c2){
            return c1.get_titlu() < c2.get_titlu();
        });
    else {
        if (criteriu == "autor")
            sort(lista_carti.begin(), lista_carti.end(), [](const Carte &c1, const Carte &c2) {
                return c1.get_autor() < c2.get_autor();
            });
        else if (criteriu == "an")
            sort(lista_carti.begin(), lista_carti.end(), [](const Carte &c1, const Carte &c2) {
                return c1.get_an_aparitie() < c2.get_an_aparitie();
            });
        else if (criteriu == "gen")
            sort(lista_carti.begin(), lista_carti.end(), [](const Carte &c1, const Carte &c2) {
                return c1.get_gen() < c2.get_gen();
            });
        else if (criteriu == "an+gen")
            sort(lista_carti.begin(), lista_carti.end(), [](const Carte &c1, const Carte &c2) {
                return c1.get_an_aparitie() < c2.get_an_aparitie() || c1.get_an_aparitie() == c2.get_an_aparitie() && c1.get_gen() < c2.get_gen();
            });
    }
    for (auto &carte: lista_carti)
        this->lista_carti_service.sterge_carte_dupa_id_repo(carte.get_id());

    for (auto &carte: lista_carti)
        this->lista_carti_service.adauga_carte_repo(carte);
    notify();
}

bool ServiceBiblioteca::compara(const Carte& c1, const Carte& c2, string criteriu) {
    if (criteriu == "titlu")
        if (c1.get_titlu() < c2.get_titlu())
            return true;
        else
            return false;
    else
        if (criteriu == "autor")
            if (c1.get_autor() < c2.get_autor())
                return true;
            else
                return false;
        else
            if (criteriu == "an")
                if (c1.get_an_aparitie() < c2.get_an_aparitie())
                    return true;
                else
                    return false;
            else
                if (criteriu == "gen")
                    if (c1.get_gen() < c2.get_gen())
                        return true;
                    else
                        return false;
                else
                    if (criteriu == "an+gen")
                        if (c1.get_an_aparitie() < c2.get_an_aparitie())
                            return true;
                        else
                            if (c1.get_an_aparitie() == c2.get_an_aparitie())
                                if (c1.get_gen() < c2.get_gen())
                                    return true;
                                else
                                    return false;
                            else
                                return false;
    return false;
}

void ServiceBiblioteca::adauga_cos_service(string titlu) {
    vector<Carte> lista = filtrare_carti_dupa_un_criteriu_service("titlu", titlu, 0);
    for (auto &carte:lista)
        this->cos_inchirieri.adauga_cos(carte);
    notify();
}

void ServiceBiblioteca::goleste_cos_service() {
    this->cos_inchirieri.goleste_cos();
    notify();
}

void ServiceBiblioteca::genereaza_cos_service(int numar_carti) {
    this->cos_inchirieri.genereaza_cos(this->lista_carti_service.get_all_repo(), numar_carti);
    notify();
}

const vector<Carte> &ServiceBiblioteca::get_cos_service() const {
     return this->cos_inchirieri.get_cos();
}

const int ServiceBiblioteca::size_cos_service() {
    return this->cos_inchirieri.size_cos();
}

void ServiceBiblioteca:: export_in_fisier(string nume_fisier) const{
    std::ofstream fout(nume_fisier);
    const vector<Carte>& cos = get_cos_service();
    for (auto &carte:cos)
        fout << carte.get_id() << "," << carte.get_titlu() << "," << carte.get_autor() << "," << carte.get_gen() << "," << carte.get_an_aparitie() << "\n";
    fout.close();
}

const map <string, vector<Carte>> ServiceBiblioteca:: map_cheie_titlu() const{
    map <string, vector<Carte>> dictionar_carti;
    for (auto& carte: this->lista_carti_service.get_all_repo())
        dictionar_carti[carte.get_titlu()].push_back(carte);
    return dictionar_carti;
}

void ServiceBiblioteca::undo() {
    ActiuneUndo* actiune_undo = this->lista_undo.back();
    this->lista_undo.back()->doUndo();
    this->lista_undo.pop_back();
    delete actiune_undo;
    notify();
}

const int ServiceBiblioteca::lungime_lista_undo() const {
    return lista_undo.size();
}

ServiceBiblioteca::~ServiceBiblioteca() {
    for (auto& actiune_undo: this->lista_undo)
        delete actiune_undo;
}
