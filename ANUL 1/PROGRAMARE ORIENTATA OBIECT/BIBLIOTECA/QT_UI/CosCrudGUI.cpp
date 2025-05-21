//
// Created by Karina Kentsch on 5/31/2023.
//

#include "CosCrudGUI.h"
#include "QHBoxLayout"
#include "QFormLayout"
#include "QStringList"
#include "QHeaderView"

void CosCrudGUI::AssignTaskCos() {
    QObject::connect(buton_adauga_cos, &QPushButton::clicked, this, &CosCrudGUI::adauga_cos_gui);
    QObject::connect(buton_goleste_cos, &QPushButton::clicked, this, &CosCrudGUI::goleste_cos_gui);
    QObject::connect(buton_random_cos, &QPushButton::clicked, this, &CosCrudGUI::random_cos_gui);
    QObject::connect(buton_export_cos, &QPushButton::clicked, this, &CosCrudGUI::export_gui);
}

void CosCrudGUI::DesignCos() {
    //meniu pt cos
    QHBoxLayout* main_cos_crud_gui_layout = new QHBoxLayout;
    main_cos_crud_gui->setLayout(main_cos_crud_gui_layout);

    QWidget* left_cos = new QWidget;
    QWidget* right_cos = new QWidget;

    QVBoxLayout* left_cos_layout = new QVBoxLayout;
    QVBoxLayout* right_cos_layout = new QVBoxLayout;

    left_cos->setLayout(left_cos_layout);
    right_cos->setLayout(right_cos_layout);

    main_cos_crud_gui_layout->addWidget(left_cos);
    main_cos_crud_gui_layout->addWidget(right_cos);

    QWidget* form_cos = new QWidget;
    QFormLayout* form_cos_layout = new QFormLayout;
    form_cos->setLayout(form_cos_layout);

    form_cos_layout->addRow("Titlu", edit_titlu_cos);
    form_cos_layout->addRow("Nr. random", edit_nr_random_cos);
    form_cos_layout->addRow("Nume fisier", edit_nume_fisier_cos);

    left_cos_layout->addWidget(form_cos);

    left_cos_layout->addWidget(buton_adauga_cos);
    left_cos_layout->addWidget(buton_goleste_cos);
    left_cos_layout->addWidget(buton_random_cos);
    left_cos_layout->addWidget(buton_export_cos);
    right_cos_layout->addWidget(tabel_carti_cos);

    QStringList tableHeaderListCos;
    tableHeaderListCos << "ID" << "TITLU" << "AUTOR" << "GEN" << "AN APARITIE";
    tabel_carti_cos->setHorizontalHeaderLabels(tableHeaderListCos);
    tabel_carti_cos->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    main_cos_crud_gui->resize(left_cos->width()+ right_cos->width(), left_cos->height());
}


void CosCrudGUI::export_gui() {
    string nume_fisier = edit_nume_fisier_cos->text().toStdString();
    clear_field_gui();
    lista_carti.export_in_fisier(nume_fisier);
}

void CosCrudGUI::goleste_cos_gui() {
    lista_carti.goleste_cos_service();
    afisare_cos_gui();
}

void CosCrudGUI::adauga_cos_gui() {
    string titlu = edit_titlu_cos->text().toStdString();
    clear_field_gui();
    lista_carti.adauga_cos_service(titlu);
    afisare_cos_gui();
}

void CosCrudGUI::random_cos_gui() {
    int nr_random = edit_nr_random_cos->text().toInt();
    clear_field_gui();
    lista_carti.genereaza_cos_service(nr_random);
    afisare_cos_gui();
}

void CosCrudGUI::afisare_cos_gui() {
    this->tabel_carti_cos->clearContents();
    this->tabel_carti_cos->setRowCount(lista_carti.get_cos_service().size());

    int nrLinie = 0;
    for (auto& carte:lista_carti.get_cos_service()){
        this->tabel_carti_cos->setItem(nrLinie, 0, new QTableWidgetItem(QString::number(carte.get_id())));
        this->tabel_carti_cos->setItem(nrLinie, 1, new QTableWidgetItem(QString::fromStdString(carte.get_titlu())));
        this->tabel_carti_cos->setItem(nrLinie, 2, new QTableWidgetItem(QString::fromStdString(carte.get_autor())));
        this->tabel_carti_cos->setItem(nrLinie, 3, new QTableWidgetItem(QString::fromStdString(carte.get_gen())));
        this->tabel_carti_cos->setItem(nrLinie, 4, new QTableWidgetItem(QString::number(carte.get_an_aparitie())));
        nrLinie++;
    }
}

void CosCrudGUI::clear_field_gui() {
    edit_titlu_cos->clear();
    edit_nume_fisier_cos->clear();
    edit_nr_random_cos->clear();
}
