//
// Created by Karina Kentsch on 4/28/2023.
//

#ifndef LAB6_7_BIBLIOTECA_ACTIUNEUNDO_H
#define LAB6_7_BIBLIOTECA_ACTIUNEUNDO_H


#include "../domain/carte.h"
#include "../repository/RepositoryBiblioteca.h"

class ActiuneUndo {
public:

    virtual void doUndo() = 0;
    virtual ~ActiuneUndo() = default;
};

class UndoAdauga:public ActiuneUndo{
private:
    Carte carte;
    BaseRepo& lista_carti_repo;

public:
    /**
     * Constructor undo adauga
     * @param lista RepositoryBiblioteca&
     * @param c Carte&
     */
    UndoAdauga(BaseRepo& lista, Carte& c): carte{c}, lista_carti_repo{lista}{}

    /**
     * Actiune undo pt adauga
     */
    void doUndo() override{
        lista_carti_repo.sterge_carte_dupa_id_repo(carte.get_id());
    }

};

class UndoSterge:public ActiuneUndo{
private:
    Carte carte;
    BaseRepo& lista_carti_repo;

public:
    /**
     * Constructor undo sterge
     * @param lista RepositoryBiblioteca&
     * @param c Carte&
     */
    UndoSterge(BaseRepo& lista, Carte& c): carte{c}, lista_carti_repo{lista}{}

    /**
     * Actiune undo pt sterge
     */
    void doUndo() override{
        lista_carti_repo.adauga_carte_repo(carte);
    }
};

class UndoModifica:public ActiuneUndo{
private:
    Carte carte_veche;
    BaseRepo& lista_carti_repo;

public:
    /**
     * Constructor undo sterge
     * @param lista RepositoryBiblioteca&
     * @param c Carte&
     */
    UndoModifica(BaseRepo& lista, Carte& c): carte_veche{c}, lista_carti_repo{lista}{}

    void doUndo() override{
        lista_carti_repo.modificare_carte_repo(carte_veche);
    }
};

#endif //LAB6_7_BIBLIOTECA_ACTIUNEUNDO_H
