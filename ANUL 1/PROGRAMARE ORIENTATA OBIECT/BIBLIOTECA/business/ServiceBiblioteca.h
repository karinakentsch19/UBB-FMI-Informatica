//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_SERVICEBIBLIOTECA_H
#define LAB6_7_BIBLIOTECA_SERVICEBIBLIOTECA_H


#include "../repository/RepositoryBiblioteca.h"
#include "../validator/Validator.h"
#include "../Cos_de_inchirieri/CosCarti.h"
#include "../undo/ActiuneUndo.h"
#include "map"
#include "../Observer/Observable.h"

using std::map;

class ServiceBiblioteca : public Observable{
private:
    BaseRepo& lista_carti_service;
    Validator validator;
    CosCarti cos_inchirieri;
    vector<ActiuneUndo*> lista_undo;
public:
    /**
     * Constructor lista carti service
     * @param lista_repo RepositoryBiblioteca
     * @param validator Validator
     */
    ServiceBiblioteca(RepositoryBiblioteca& lista_repo, Validator validator): lista_carti_service{lista_repo}, validator{validator}{}

    /**
     * Destructor
     */
    ~ServiceBiblioteca();

    /**
     * Returneaza lista de carti service
     * @return lista carti service
     */
    const vector <Carte>& get_all_service() const;

    /**
     * Returneaza lungimea listei de carti service
     * @return lungime lista carti service
     */
    int lungime_lista_service() const;

    /**
     * Adauga o carte in lista de carti service
     * @param titlu string
     * @param gen string
     * @param autor string
     * @param an_aparitie int
     * @param id int
     * @throw RepoError_ValidationError
     */
    void adauga_carte_service(string titlu, string gen, string autor, int an_aparitie, int id);

    /**
     * Sterge o carte dupa id din lista de carti service
     * @param id
     * @throw RepoError_ValidationError
     */
    void sterge_carte_dupa_id_service(int id);

    /**
     * Modifica argumentele unei carti care are id-ul dat
     * @param titlu_nou string
     * @param gen_nou string
     * @param autor_nou string
     * @param an_aparitie_nou int
     * @param id int
     * @throw RepoError_ValidationError
     */
    void modificare_carte_service(string titlu_nou, string gen_nou, string autor_nou, int an_aparitie_nou, int id);

    /**
     * Cauta o carte dupa id-ul dat in lista de carti service
     * @param id int
     * @return carte
     * @throw RepoError_ValidationError
     */
    const Carte& cauta_carte_dupa_id_service(int id) const;

    /**
     * Filtreqza lista de carti service dupa un criteriu (titlu / an)
     * @param criteriu string
     * @param titlu_dat string
     * @param an_aparitie_dat int
     * @return lista filtrata de carti
     */
    vector <Carte> filtrare_carti_dupa_un_criteriu_service(string criteriu, string titlu_dat, int an_aparitie_dat);

    /**
     * Sorteaza lista in functie de un criteriu de comparatie
     * Criteriu de comparatie poate fi: titlu, autor, an sau gen
     * @param criteriu string
     */
    void sortare_lista_carti_dupa_un_criteriu(string criteriu);

    /**
     * Compara o carte c1 cu o carte c2 in functie de un criteriu
     * @param c1 Carte
     * @param c2 Carte
     * @param criteriu string
     * @return true daca c1 < c2
     *         false daca c1 >= c2
     */
    bool compara(const Carte& c1, const Carte& c2, string criteriu);

    /**
     * Adauga in cosul de inchirieri o carte care are titlul dat
     * @param titlu string
     */
    void adauga_cos_service(string titlu);

    /**
     * Goleste cosul de inchirieri
     */
    void goleste_cos_service();

    /**
     * Genereaza random un numar de carti dat si le adauga in cosul de inchirieri
     * @param numar_carti int
     */
    void genereaza_cos_service(int numar_carti);

    /**
     * Returneaza cosul de inchirieri
     */
    const vector<Carte>& get_cos_service() const;

    /**
     * Returneaza cate carti sunt in cosul de inchirieri
     * @return lungime cos
     */
    const int size_cos_service();

    /**
     * Se salveaza intr-un fisier CVS cu numele dat cosul de inchirieri
     * @param nume_fisier string
     */
    void export_in_fisier(string nume_fisier) const;

    /**
     * Returneaza un map in care cheile reprezinta titlurile cartilor, iar valoarea este un vector cu cartile care au titlul respectiv
     * @return map
     */
    const map<string, vector<Carte>> map_cheie_titlu() const;

    /**
     * Undo pentru ultima actiune efectuata a listei
     */
    void undo();

    /**
     * Returneaza lungimea listei de undo
     * @return
     */
    const int lungime_lista_undo() const;

};


#endif //LAB6_7_BIBLIOTECA_SERVICEBIBLIOTECA_H
