//
// Created by Karina Kentsch on 4/28/2023.
//

#ifndef LAB6_7_BIBLIOTECA_REPOSITORYBIBLIOTECAFILE_H
#define LAB6_7_BIBLIOTECA_REPOSITORYBIBLIOTECAFILE_H
#include "RepositoryBiblioteca.h"

class RepositoryBibliotecaFile:public RepositoryBiblioteca{
private:
    string file;

    /**
     * Incarca in fisier lista de carti repository
     */
    void store_to_file() const;

    /**
     * Pune in lista de carti repository continutul fisierului
     */
    void load_from_file();


public:

    /**
     * constructor repository biblioteca file
     * @param file_name string
     */
    RepositoryBibliotecaFile(string file_name): RepositoryBiblioteca(), file{file_name}{
        load_from_file();
    }

    /**
     * Destructor Repo Biblioteca file
     */
    ~RepositoryBibliotecaFile() = default;

    /**
     * Adauga o carte in lista si incarca lista in fisier
     * @param carte Carte
     * @throw RepoError "Carte existenta\n" daca exista deja carte in lista
     */
    void adauga_carte_repo(const Carte& carte) override;

    /**
     * Sterge o carte dupa id din lista si incarca lista in fisier
     * @param id int
     * @throw RepoError "Carte inexistenta\n" daca nu exista cartea in lista
     */
    void sterge_carte_dupa_id_repo(int id) override;

    /**
     * Modifica datele cartii care are acelasi id cu cartea noua s incarca lista in fisier
     * @param carte_noua Carte
     * @throw RepoError "Carte inexistenta\n" daca nu exista cartea in lista
     */
    void modificare_carte_repo(const Carte& carte_noua) override;
};


#endif //LAB6_7_BIBLIOTECA_REPOSITORYBIBLIOTECAFILE_H
