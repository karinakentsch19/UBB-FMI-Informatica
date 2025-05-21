//
// Created by Karina Kentsch on 3/31/2023.
//

#include "UI.h"
#include "iostream"
#include "../errors/RepoError.h"
#include "../errors/ValidationError.h"

using std::cin;
using std::cout;
using std::endl;

int UI::ruleaza_ui() {
    string comanda;
    while(1){
        afisare_optiuni();
        afisare_numar_carti_cos();
        cout << "Introduceti comanda:";
        cin >> comanda;
        switch (codificare(comanda)) {
            case 1:
                adauga_ui();
                break;
            case 2:
                sterge_ui();
                break;
            case 3:
                modifica_ui();
                break;
            case 4:
                cauta_ui();
                break;
            case 5:
                filtru_ui();
                break;
            case 6:
                sortare_ui();
                break;
            case 7:
                afisare_lista();
                break;
            case 8:
                adauga_cos_ui();
                break;
            case 9:
                goleste_cos();
                break;
            case 10:
                random_cos();
                break;
            case 11:
                export_ui();
                break;
            case 12:
                afisare_cos();
                break;
            case 13:
                afisare_map();
                break;
            case 14:
                undo_ui();
                break;
            case 15:
                return 0;
            default:
                cout << "Comanda invalida!" << " ";
                continue;
        }
    }
}

void UI::adauga_ui() {
    string titlu, nume, prenume, autor, gen;
    int id, an_aparitie;
    cout << "\nIntroduceti id-ul titlul, numele si prenumele autorului, genul si anul aparitiei" << endl;
    cin >> id >> titlu >> nume >> prenume >> gen >> an_aparitie;
    try{
        autor = nume + " " + prenume;
        this->lista_carti.adauga_carte_service(titlu, gen, autor, an_aparitie, id);
        cout << "Carte adaugata cu succes!" << endl;
    }catch (RepoError& eroare) {
        cout << eroare.get_mesaj() << endl;
    }catch (ValidationError& eroare){
        cout << eroare.get_mesaj() << endl;
    }

}

void UI::sterge_ui() {
    int id;
    cout << "\nIntroduceti id-ul cartii" << endl;
    cin >> id;
    try{
        this->lista_carti.sterge_carte_dupa_id_service(id);
        cout << "Carte stearsa cu succes!" << endl;
    }catch (RepoError& eroare){
        cout << eroare.get_mesaj() << endl;
    }catch (ValidationError& eroare){
        cout << eroare.get_mesaj() << endl;
    }
}

void UI::modifica_ui() {
    string titlu_nou, nume_nou, prenume_nou, gen_nou;
    int an_aparitie_nou, id;
    cout << "\nIntroduceti id-ul cartii de modificat si titlul, numele, prenumele autorului, genul si anul aparitiei noi" << endl;
    cin >> id >> titlu_nou >> nume_nou >> prenume_nou >> gen_nou >> an_aparitie_nou;
    try{
        string autor = nume_nou + " " + prenume_nou;
        this->lista_carti.modificare_carte_service(titlu_nou, gen_nou, autor, an_aparitie_nou, id);
        cout << "Carte modificata cu succes!" << endl;
    }catch (RepoError& eroare){
        cout << eroare.get_mesaj() << endl;
    }catch (ValidationError& eroare){
        cout << eroare.get_mesaj() << endl;
    }
}

void UI::cauta_ui() {
    int id;
    cout << "\nIntroduceti id-ul cartii pe care doriti sa o cautati" << endl;
    cin >> id;
    try{
        const Carte& carte = this->lista_carti.cauta_carte_dupa_id_service(id);
        cout << "Id: " << carte.get_id() << endl;
        cout << "Titlu: " << carte.get_titlu() << endl;
        cout << "Autor: " << carte.get_autor() << endl;
        cout << "Gen: " << carte.get_gen() << endl;
        cout << "An aparitie: " << carte.get_an_aparitie() << endl;
        cout << endl;
    }catch (RepoError& eroare){
        cout << eroare.get_mesaj() << endl;
    }catch (ValidationError& eroare){
        cout << eroare.get_mesaj() << endl;
    }
}

int UI::codificare(string comanda) const {
    if (comanda == "adauga")
        return 1;
    if (comanda == "sterge")
        return 2;
    if (comanda == "modifica")
        return 3;
    if (comanda == "cauta")
        return 4;
    if (comanda == "filtrare")
        return 5;
    if (comanda == "sortare")
        return 6;
    if (comanda == "afisare")
        return 7;
    if (comanda == "adauga_cos")
        return 8;
    if (comanda == "goleste_cos")
        return 9;
    if (comanda == "random_cos")
        return 10;
    if (comanda == "export")
        return 11;
    if (comanda == "afisare_cos")
        return 12;
    if (comanda == "afisare_map")
        return 13;
    if (comanda == "undo")
        return 14;
    if (comanda == "exit")
        return 15;
}

void UI::afisare_optiuni() {
    cout << endl;
    cout << "MENIU" << endl;
    cout << "adauga         - adauga o carte in lista" << endl;
    cout << "sterge         - sterge o carte din lista" << endl;
    cout << "modifica       - modifica o carte din lista" << endl;
    cout << "cauta          - cauta o carte in lista" << endl;
    cout << "filtrare       - filtreaza lista" << endl;
    cout << "sortare        - sortare lista" << endl;
    cout << "afisare        - afiseaza lista de carti" << endl;
    cout << "adauga_cos     - adauga  o carte in cos" << endl;
    cout << "goleste_cos    - goleste cosul" << endl;
    cout << "random_cos     - adauga un numar de carti random in cos" << endl;
    cout << "export         - exporta in fisier cosul" << endl;
    cout << "afisare_cos    - afiseaza cosul" << endl;
    cout << "afisare_map    - afiseaza dictionar" << endl;
    cout << "undo           - undo" << endl;
    cout << "exit     - iesire din program" << endl;
    cout << endl;
}

void UI::filtru_ui() {
    string criteriu;
    cout << "\nIntroduceti criteriul dupa care doriti sa filtrati: titlu/an" << endl;
    cin >> criteriu;
    if (criteriu == "titlu"){
        string titlu;
        cout << "\nIntroduceti titlul dupa care doriti sa filtrati" << endl;
        cin >> titlu;
        vector <Carte> lista_filtrata = this->lista_carti.filtrare_carti_dupa_un_criteriu_service(criteriu, titlu, 0);
        for (auto & carte:lista_filtrata){
            cout << "Id: " << carte.get_id() << endl;
            cout << "Titlu: " << carte.get_titlu() << endl;
            cout << "Autor: " << carte.get_autor() << endl;
            cout << "Gen: " << carte.get_gen() << endl;
            cout << "An aparitie: " << carte.get_an_aparitie() << endl;
            cout << endl;
        }
    }
    else
        if (criteriu == "an"){
            int an;
            cout << "\nIntroduceti anul aparitiei dupa care doriti sa filtrati" << endl;
            cin >> an;
            vector <Carte> lista_filtrata = this->lista_carti.filtrare_carti_dupa_un_criteriu_service(criteriu, "", an);
            for (auto & carte:lista_filtrata) {
                cout << "Id: " << carte.get_id() << endl;
                cout << "Titlu: " << carte.get_titlu() << endl;
                cout << "Autor: " << carte.get_autor() << endl;
                cout << "Gen: " << carte.get_gen() << endl;
                cout << "An aparitie: " << carte.get_an_aparitie() << endl;
                cout << endl;
            }
        }
        else
            cout << "Criteriu invalid!" << endl;
}

void UI::sortare_ui() {
    string criteriu;
    cout << "\nIntroduceti criteriul de sortare: titlu/autor/an/gen/an+gen" << endl;
    cin >> criteriu;
    this->lista_carti.sortare_lista_carti_dupa_un_criteriu(criteriu);
    cout << "Sortare efectuata cu succes!" << endl;
}

void UI::afisare_lista() {
    if (lista_carti.get_all_service().size() == 0)
        cout << "Nu exista carti in lista!" << endl;
    else
        for (auto & carte: lista_carti.get_all_service()){
            cout << "Id: " << carte.get_id() << endl;
            cout << "Titlu: " << carte.get_titlu() << endl;
            cout << "Autor: " << carte.get_autor() << endl;
            cout << "Gen: " << carte.get_gen() << endl;
            cout << "An aparitie: " << carte.get_an_aparitie() << endl;
            cout << endl;
        }
}

void UI::adauga_cos_ui() {
    string titlu;
    cout << "Introduceti titlul cartii pe care doriti sa o adaugati in cos:" << endl;
    cin >> titlu;
    lista_carti.adauga_cos_service(titlu);
    cout << "Adaugare cos efectuata cu succes!" << endl;
}

void UI::goleste_cos() {
    lista_carti.goleste_cos_service();
    cout << "Cos gol" << endl;
}

void UI::random_cos() {
    int numar_carti;
    cout << "Introduceti numarul de carti de introdus random:" << endl;
    cin >> numar_carti;
    lista_carti.genereaza_cos_service(numar_carti);
    cout << "Adaugare cos efectuata cu succes!" << endl;
}

void UI::afisare_cos() {
    const vector<Carte>& cos = lista_carti.get_cos_service();
    if (cos.empty())
        cout << "Cosul este gol!" << endl;
    else
        for(auto& carte:cos) {
            cout << "Id: " << carte.get_id() << endl;
            cout << "Titlu: " << carte.get_titlu() << endl;
            cout << "Autor: " << carte.get_autor() << endl;
            cout << "Gen: " << carte.get_gen() << endl;
            cout << "An aparitie: " << carte.get_an_aparitie() << endl;
            cout << endl;
        }
}

void UI::afisare_numar_carti_cos() {
    cout << "Numarul cartilor din cos: " << lista_carti.size_cos_service() << endl;
}

void UI::export_ui() {
    string fisier;
    cout << "Introduceti numele fisierului in care doriti sa exportati cosul:" << endl;
    cin >> fisier;
    lista_carti.export_in_fisier(fisier);
}

void UI::afisare_map() {
    auto map = this->lista_carti.map_cheie_titlu();
    auto iterator = map.begin();
    while(iterator != map.end()){
        cout << endl << "Titlu: " << iterator->first << endl;
        for (auto& carte: iterator->second)
            cout << "Id: " <<carte.get_id() << " Autor: " << carte.get_autor() << " Gen: " << carte.get_gen() << " An aparitie: " << carte.get_an_aparitie() << endl;
        iterator++;
    }
}

void UI::undo_ui() {
   if (lista_carti.lungime_lista_undo() == 0)
       cout << "Nu se mai poate face undo!" << endl;
   else
       lista_carti.undo();
}
