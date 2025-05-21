//
// Created by Karina Kentsch on 3/31/2023.
//

#include "RepositoryBiblioteca.h"
#include "../errors/RepoError.h"
#include "algorithm"

using std::find_if;

int RepositoryBiblioteca::get_lungime_lista_repo() const{
    return this->lista_carti.size();
}

const vector <Carte>& RepositoryBiblioteca::get_all_repo() const{
    return this->lista_carti;
}

void RepositoryBiblioteca::adauga_carte_repo(const Carte& carte) {
//    for (const auto& c:this->lista_carti)
//        if (c == carte)

    auto found = find_if(this->lista_carti.begin(), this->lista_carti.end(), [carte](const Carte& c){
        return c.get_id() == carte.get_id();
    });
    if (found != this->lista_carti.end())
            throw RepoError("Carte existenta\n");
    lista_carti.push_back(carte);
}

void RepositoryBiblioteca::sterge_carte_dupa_id_repo(int id) {
//    bool found = false;
//    for (int i = 0; i < this->get_lungime_lista_repo(); i++)
//        if (lista_carti[i].get_id() == id){
//            for (int j = i; j < this->get_lungime_lista_repo()-1; j++)
//                lista_carti[j] = lista_carti[j+1];
//            lista_carti.pop_back();
//            found = true;
//        }
//    if (!found)
//        throw RepoError("Carte inexistenta\n");

    auto found = find_if(this->lista_carti.begin(), this->lista_carti.end(), [id](const Carte& c){
        return c.get_id() == id;
    });
    if (found == this->lista_carti.end())
        throw RepoError("Carte inexistenta\n");
    else
        this->lista_carti.erase(found);
}

void RepositoryBiblioteca::modificare_carte_repo(const Carte& carte_noua) {
//    bool found = false;
//    for (auto& c:this->lista_carti)
//        if (c == carte_noua){
//            c = carte_noua;
//            found = true;
//        }
//    if (!found)
        auto found = find_if(this->lista_carti.begin(), this->lista_carti.end(), [carte_noua](const Carte& c){
            return c.get_id() == carte_noua.get_id();
        });
        if (found == this->lista_carti.end())
            throw RepoError("Carte inexistenta\n");
        else{
            found->set_an_aparitie(carte_noua.get_an_aparitie());
            found->set_autor(carte_noua.get_autor());
            found->set_gen(carte_noua.get_gen());
            found->set_titlu(carte_noua.get_titlu());
        }
}

const Carte& RepositoryBiblioteca::cauta_carte_dupa_id_repo(int id) const{
//    for (auto& c:this->lista_carti)
//        if (c.get_id() == id)
//            return c;
    auto found = find_if(this->lista_carti.begin(), this->lista_carti.end(), [id](const Carte& c){
        return c.get_id() == id;
    });
    if (found == this->lista_carti.end())
        throw RepoError("Carte inexistenta\n");
    else
        return *found;
}
