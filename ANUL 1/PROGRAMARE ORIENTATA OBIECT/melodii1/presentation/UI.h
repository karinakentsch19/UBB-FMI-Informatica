//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_UI_H
#define MELODII1_UI_H
#include "../business/ServiceMelodii.h"
#include "QWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QTableWidget"
#include "QSlider"
#include "QPainter"
#include "TableModelView.h"
#include "QTableView"

class UI: public QWidget{
private:
    ServiceMelodii lista_melodii;

    QPushButton* buton_adauga = new QPushButton("Adauga melodie");
    QPushButton* buton_sterge = new QPushButton("Sterge melodie");
    QPushButton* buton_modifica = new QPushButton("Modifica melodie");


    QLineEdit* edit_id = new QLineEdit;
    QLineEdit* edit_titlu = new QLineEdit;
    QLineEdit* edit_artist = new QLineEdit;

    QSlider* slider_rank = new QSlider{Qt::Orientation::Horizontal};

    QTableWidget* table = new QTableWidget(2,4);

    QTableView* table_view = new QTableView;
    TableModelView* table_model = new TableModelView{lista_melodii.get_all_service()};

    void afisare();

    void AssignTask();

    void Design();

    void clear_field();

    void adauga();

    void sterge();

    void modifica();

    void selectare_tabel();

    void paintEvent(QPaintEvent *event) override{
        QPainter painter{this};
        int x = 10,y = this->height(),h = 15;
        for (int i = 0; i <= 10; i++){
            int nr_melodii = lista_melodii.nr_melodii_cu_rank_dat(i);
            painter.drawRect(x,y,nr_melodii*10,h);
            y = y - 20;
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


#endif //MELODII1_UI_H
