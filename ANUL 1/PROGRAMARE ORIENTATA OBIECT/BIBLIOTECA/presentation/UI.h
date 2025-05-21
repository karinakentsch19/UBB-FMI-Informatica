//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_UI_H
#define LAB6_7_BIBLIOTECA_UI_H


#include "../business/ServiceBiblioteca.h"

class UI {
private:
    ServiceBiblioteca lista_carti;

    /**
     * Adauga o carte in lista
     */
    void adauga_ui();

    /**
     * Sterge o carte din lista
     */
    void sterge_ui();

    /**
     * Modifica o carte din lista
     */
    void modifica_ui();

    /**
     * Cauta o carte in lista
     */
    void cauta_ui();

    /**
     * Filtrare lista
     */
    void filtru_ui();

    /**
     * Sortare lista
     */
    void sortare_ui();

    /**
     * Codifica in numere comenzile
     * @return 1 - adauga
     *         2 - sterge
     *         3 - modifica
     *         4 - cauta
     *         5 - filtrare
     *         6 - sortare
     *         7 - afisare
     *         8 - exit
     */
    int codificare(string comanda) const;

    /**
     * Afiseaza optiunile din meniu
     */
    void afisare_optiuni();

    /**
     * Afiseaza lista de carti
     */
    void afisare_lista();

    /**
     * Export in fisier
     */
    void export_ui();

    /**
     * Adauga in cos
     */
    void adauga_cos_ui();

    /**
     * Goleste cos
     */
    void goleste_cos();

    /**
     * Adauga carti random in cos
     */
    void random_cos();

    /**
     * Afiseaza cos
     */
    void afisare_cos();

    /**
     * Afiseaza numarul de carti din cos
     */
    void afisare_numar_carti_cos();

    void afisare_map();

    void undo_ui();

public:
    /**
     * Constructor UI
     * @param lista
     */
    UI(ServiceBiblioteca lista): lista_carti{lista}{}

    /**
     * Ruleaza ui
     */
    int ruleaza_ui();
};


#endif //LAB6_7_BIBLIOTECA_UI_H
