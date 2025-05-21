//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_UI_H
#define MELODII2_UI_H
#include "QWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QTableWidget"
#include "../business/ServiceMelodii.h"
#include "QPainter"
#include "TableModelView.h"
#include "QTableView"

class UI: public QWidget{
private:
    ServiceMelodii lista_melodii;

    QPushButton* buton_adauga = new QPushButton("Adauga melodie");
    QPushButton* buton_sterge = new QPushButton("Sterge melodie");

    QLineEdit* edit_id = new QLineEdit;
    QLineEdit* edit_titlu = new QLineEdit;
    QLineEdit* edit_artist = new QLineEdit;
    QLineEdit* edit_gen = new QLineEdit;

    QTableWidget* table = new QTableWidget(2,4);

    QTableView* table_view = new QTableView;
    TableModelView* model_table = new TableModelView{lista_melodii.get_all_service()};

    void Design();

    void AssignTask();

    void afisare();

    void adauga();

    void sterge();

    void clear_fields();

    void selectare_tabel();

    void paintEvent(QPaintEvent *event) override{
        QPainter painter{this};
        //pop colt stanga sus
        //rock colt dreapta sus
        //folk stanga jos
        //disco dreapta jos
        int x_pop = 100, y_pop = this->height()-200, x_rock = 300, y_rock = this->height() - 100, x_folk = 100, y_folk = this->height() - 100, x_disco = 300, y_disco = this->height()-200;
        painter.drawEllipse({x_pop, y_pop}, 20, 20);
        painter.drawEllipse({x_rock, y_rock}, 20, 20);
        painter.drawEllipse({x_folk, y_folk}, 20, 20);
        painter.drawEllipse({x_disco, y_disco}, 20, 20);

        int nr_melodii_pop = lista_melodii.numar_melodii_pt_gen_dat("pop");
        int r_pop = 25;
        while(nr_melodii_pop){
            painter.drawEllipse({x_pop, y_pop}, r_pop, r_pop);
            r_pop += 10;
            nr_melodii_pop--;
        }

        int nr_melodii_rock = lista_melodii.numar_melodii_pt_gen_dat("rock");
        int r_rock = 25;
        while(nr_melodii_rock){
//            painter.drawEllipse(x_rock, y_rock, r_rock, r_rock);
            painter.drawEllipse({x_rock, y_rock}, r_rock, r_rock);
            r_rock += 10;
            nr_melodii_rock--;
        }

        int nr_melodii_folk = lista_melodii.numar_melodii_pt_gen_dat("folk");
        int r_folk = 25;
        while(nr_melodii_folk){
            painter.drawEllipse({x_folk, y_folk}, r_folk, r_folk);
            r_folk += 10;
            nr_melodii_folk--;
        }

        int nr_melodii_disco = lista_melodii.numar_melodii_pt_gen_dat("disco");
        int r_disco = 25;
        while(nr_melodii_disco){
            painter.drawEllipse({x_disco, y_disco}, r_disco, r_disco);
            r_disco += 10;
            nr_melodii_disco--;
        }
    }

public:
    UI(ServiceMelodii lista_service): lista_melodii{lista_service}{
        Design();
        AssignTask();
    }

    void ruleaza_ui(){
        this->show();
        afisare();
    }
};


#endif //MELODII2_UI_H
