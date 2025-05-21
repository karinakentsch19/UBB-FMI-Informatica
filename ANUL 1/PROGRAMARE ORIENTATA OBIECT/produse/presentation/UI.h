//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_UI_H
#define PRODUSE_UI_H
#include "QWidget"
#include "../business/ServiceProdus.h"
#include "QPushButton"
#include "QLineEdit"
#include "TableViewModel.h"
#include "QTableView"
#include "QSlider"

using std::vector;

class UI : public QWidget{
private:
    ServiceProdus lista_produse;

    QPushButton* buton_adauga = new QPushButton("Adauga produs");

    QLineEdit* edit_id = new QLineEdit;
    QLineEdit* edit_nume = new QLineEdit;
    QLineEdit* edit_tip = new QLineEdit;
    QLineEdit* edit_pret = new QLineEdit;

    QSlider* slider_pret = new QSlider{Qt::Horizontal};

    QTableView* table_view = new QTableView;
    TableModel* table_model = new TableModel{lista_produse.get_all_service(), 1};

    vector<QWidget*> ferestre_tip;

    void Design();

    void AssignTask();

    void afisare();

    void deschide_ferestre_tip();

    void adauga();

    void setare_pret_slider();

    void clear_fields();

    void closeEvent(QCloseEvent *event) override{
        for (auto& fereastra: ferestre_tip)
            delete fereastra;
        ferestre_tip.clear();
    }
public:
    UI(ServiceProdus lista_service): lista_produse{lista_service}{
        Design();
        AssignTask();
    }

    void ruleaza_ui(){
        this->show();
        afisare();
    }
};


#endif //PRODUSE_UI_H
