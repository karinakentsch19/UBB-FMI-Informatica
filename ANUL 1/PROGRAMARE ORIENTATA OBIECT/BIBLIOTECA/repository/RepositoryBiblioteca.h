//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_REPOSITORYBIBLIOTECA_H
#define LAB6_7_BIBLIOTECA_REPOSITORYBIBLIOTECA_H
#include "vector"
//#include "../Vector/MyVector.h"
#include "../repository/BaseRepo.h"
#include "../domain/carte.h"
using std::vector;

class RepositoryBiblioteca: public BaseRepo{
private:
    vector <Carte> lista_carti;
//    MyVector<Carte> lista_carti;

public:
    /**
     * Constructor lista
     */
    RepositoryBiblioteca() = default;

    /**
     * Returneaza lungimea listei de carti
     * @return lungime lista carti
     */
    int get_lungime_lista_repo() const override;

    /**
     * Returneaza lista de carti
     * @return lista de carti
     */
    const vector <Carte>& get_all_repo() const override;

    /**
     * Adauga o carte in lista
     * @param carte Carte
     * @throw RepoError "Carte existenta\n" daca exista deja carte in lista
     */
    virtual void adauga_carte_repo(const Carte& carte) override;

    /**
     * Sterge o carte dupa id din lista
     * @param id int
     * @throw RepoError "Carte inexistenta\n" daca nu exista cartea in lista
     */
    virtual void sterge_carte_dupa_id_repo(int id) override;

    /**
     * Modifica datele cartii care are acelasi id cu cartea noua
     * @param carte_noua Carte
     * @throw RepoError "Carte inexistenta\n" daca nu exista cartea in lista
     */
    virtual void modificare_carte_repo(const Carte& carte_noua) override;

    /**
     * Returneaza cartea care are id-ul dat
     * @param id int
     * @throw RepoError "Carte inexistenta\n" daca nu exista cartea in lista
     * @return carte
     */
    const Carte& cauta_carte_dupa_id_repo(int id) const override;
};


#endif //LAB6_7_BIBLIOTECA_REPOSITORYBIBLIOTECA_H
