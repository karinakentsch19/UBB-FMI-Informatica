//
// Created by Karina Kentsch on 5/6/2023.
//

#ifndef BIBLIOTECA_QT_UI_H
#define BIBLIOTECA_QT_UI_H
#include "../business/ServiceBiblioteca.h"
#include <QPushButton>
#include <QLabel>
#include <QLineEdit>
#include <QTableWidget>
#include <QHeaderView>
#include <QRadioButton>
#include <QVBoxLayout>
#include <QListWidget>
#include "QTableView"
#include "../Observer/Observer.h"

class QT_UI:public QWidget, public Observer{
private:
    ServiceBiblioteca& lista_carti;
    QPushButton* buton_adauga =  new QPushButton("Adauga o carte");
    QPushButton* buton_sterge =  new QPushButton("Sterge o carte");
    QPushButton* buton_modifica =  new QPushButton("Modifica o carte");
    QPushButton* buton_cauta =  new QPushButton("Cauta o carte");
    QPushButton* buton_filtru_titlu =  new QPushButton("Filtrare dupa titlu");
    QPushButton* buton_filtru_an =  new QPushButton("Filtrare dupa an");
    QPushButton* buton_sort =  new QPushButton("Sortare");
    QPushButton* buton_undo = new QPushButton("Undo");
    QPushButton* buton_cos_crud = new QPushButton("Cos crud");
    QPushButton* buton_cos_read_only = new QPushButton("Cos read only");

    QLabel* text_box_titlu = new QLabel("Titlu");
    QLabel* text_box_gen = new QLabel("Gen");
    QLabel* text_box_autor = new QLabel("Autor");
    QLabel* text_box_an_aparitie = new QLabel("An aparitie");
    QLabel* text_box_id = new QLabel("Id");

    QLineEdit* edit_titlu = new QLineEdit;
    QLineEdit* edit_gen = new QLineEdit;
    QLineEdit* edit_autor = new QLineEdit;
    QLineEdit* edit_an_aparitie = new QLineEdit;
    QLineEdit* edit_id = new QLineEdit;

    QListWidget* lista = new QListWidget;

    //butoane pt cos
    QPushButton* buton_adauga_cos = new QPushButton("Adauga in cos");
    QPushButton* buton_goleste_cos = new QPushButton("Goleste cos");
    QPushButton* buton_random_cos = new QPushButton("Adauga random in cos");
    QPushButton* buton_export_cos = new QPushButton("Export cos");

    QLabel* text_box_titlu_cos = new QLabel("Titlu cos");
    QLabel* text_box_nr_random_cos = new QLabel("Numar carti random");
    QLabel* text_box_nume_fisier_cos = new QLabel("Nume fisier");

    QLineEdit* edit_titlu_cos = new QLineEdit;
    QLineEdit* edit_nr_random_cos = new QLineEdit;
    QLineEdit* edit_nume_fisier_cos = new QLineEdit;

    QWidget* main_cos = new QWidget;

    QWidget* main = new QWidget;

    QWidget* main_map = new QWidget;

    int nr_lines = 2;
    int nr_columns = 5;
   // QTableWidget* tabel_carti = new QTableWidget{nr_lines, nr_columns};
    QTableView* tabel_nou_carti = new QTableView;


    void Assign_task();

    void Button_design();

    //functionalitati lista carti
    void adauga_ui();

    void sterge_ui();

    void modifica_ui();

    void cauta_ui();

    void filtru_titlu_ui();

    void filtru_an_ui();

    void sortare_ui();

    void undo_ui();

    void show_cos_crud();

    void show_cos_read_only();


    QWidget* right = new QWidget;
    QVBoxLayout* layout_right = new QVBoxLayout;

    QWidget* map = new QWidget;
    QVBoxLayout* map_layout = new QVBoxLayout;

    vector <QPushButton*> butoane;
    void show_map();

    //functionalitati cos
    void export_ui();

    void adauga_cos_ui();

    void goleste_cos_ui();

    void random_cos_ui();

    void afisare_cos();

    int nr_lines_cos = 2;
    int nr_columns_cos = 5;
    QTableWidget* tabel_carti_cos = new QTableWidget{nr_lines_cos, nr_columns_cos};

    void pop_up_list(const vector<Carte>& lista);

    void afisare_lista(const vector<Carte>& lista);

    void clear_field();


    void update() override{
        afisare_lista(lista_carti.get_all_service());
    }

public:

    QT_UI(ServiceBiblioteca& lista): lista_carti{lista}{
        Assign_task();
        Button_design();
        lista_carti.addObserver(this);
    }

    void ruleaza_ui(){
        main->show();
        show_map();
        afisare_lista(lista_carti.get_all_service());
    }

};


#endif //BIBLIOTECA_QT_UI_H
