//
// Created by Karina Kentsch on 5/31/2023.
//

#ifndef BIBLIOTECA_COSCRUDGUI_H
#define BIBLIOTECA_COSCRUDGUI_H
#include "QWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QTableWidget"
#include "../business/ServiceBiblioteca.h"
#include "../Observer/Observer.h"


class CosCrudGUI : public QWidget, public Observer{
private:
    ServiceBiblioteca& lista_carti;
    QWidget* main_cos_crud_gui = new QWidget;

    QPushButton* buton_adauga_cos = new QPushButton("Adauga in cos");
    QPushButton* buton_goleste_cos = new QPushButton("Goleste cos");
    QPushButton* buton_random_cos = new QPushButton("Adauga random in cos");
    QPushButton* buton_export_cos = new QPushButton("Export cos");

    QLineEdit* edit_titlu_cos = new QLineEdit;
    QLineEdit* edit_nr_random_cos = new QLineEdit;
    QLineEdit* edit_nume_fisier_cos = new QLineEdit;

    int nr_lines_cos = 2;
    int nr_columns_cos = 5;
    QTableWidget* tabel_carti_cos = new QTableWidget{nr_lines_cos, nr_columns_cos};

    void AssignTaskCos();

    void DesignCos();

    void clear_field_gui();

    void export_gui();

    void adauga_cos_gui();

    void goleste_cos_gui();

    void random_cos_gui();

    void afisare_cos_gui();

    void update() override{
        afisare_cos_gui();
    }

public:
    CosCrudGUI(ServiceBiblioteca& lista_service): lista_carti{lista_service}{
        AssignTaskCos();
        DesignCos();
        lista_carti.addObserver(this);
    }

    void ruleaza_cos_gui(){
        main_cos_crud_gui->show();
        afisare_cos_gui();
    }
};


#endif //BIBLIOTECA_COSCRUDGUI_H
