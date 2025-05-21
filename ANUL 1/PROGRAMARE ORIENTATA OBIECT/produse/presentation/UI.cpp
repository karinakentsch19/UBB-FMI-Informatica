//
// Created by Karina Kentsch on 6/16/2023.
//

#include "UI.h"
#include "../errors/RepoError.h"
#include "QMessageBox"
#include "QHBoxLayout"
#include "QVBoxLayout"
#include "QFormLayout"
#include "map"
#include "QLabel"
#include "iostream"

using std::map;
using std::cout;

void UI::Design() {
    QHBoxLayout* this_layout = new QHBoxLayout;
    this->setLayout(this_layout);

    QWidget* left = new QWidget;
    QVBoxLayout* left_layout = new QVBoxLayout;
    left->setLayout(left_layout);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);
    form_layout->addRow("ID", edit_id);
    form_layout->addRow("NUME", edit_nume);
    form_layout->addRow("TIP", edit_tip);
    form_layout->addRow("PRET", edit_pret);
    form_layout->addRow(slider_pret);
    form_layout->addRow(buton_adauga);

    left_layout->addWidget(form);
    slider_pret->setMinimum(1);
    slider_pret->setMaximum(100);

    QWidget* right = new QWidget;
    QVBoxLayout* right_layout = new QVBoxLayout;
    right->setLayout(right_layout);
    right_layout->addWidget(table_view);
    table_view->setModel(table_model);
    this_layout->addWidget(left);
    this_layout->addWidget(right);
    this->resize(left->width()+right->width(), left->height());
}

void UI::AssignTask() {
    QObject::connect(buton_adauga, &QPushButton::clicked, this, &UI::adauga);
    QObject::connect(slider_pret, &QSlider::valueChanged, this, &UI::setare_pret_slider);
}

void UI::afisare() {
    table_model->set_lista(lista_produse.get_all_service());
    deschide_ferestre_tip();
}

void UI::adauga() {
    int id = edit_id->text().toInt();
    string nume = edit_nume->text().toStdString();
    string tip = edit_tip->text().toStdString();
    double pret = edit_pret->text().toDouble();
    try{
        clear_fields();
        lista_produse.adauga_produs_service(id, nume, tip, pret);
        afisare();
    }catch (RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch (ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void UI::clear_fields() {
    edit_id->clear();
    edit_nume->clear();
    edit_tip->clear();
    edit_pret->clear();
}

void UI::setare_pret_slider() {
    double pret_filtrare = slider_pret->value();
    table_model->set_pret_filtrare(pret_filtrare);
}

void UI::deschide_ferestre_tip() {
    for (auto& fereastra: ferestre_tip)
        delete fereastra;
    ferestre_tip.clear();

    map<string,int> tipuri;
    for (const auto& produs: lista_produse.get_all_service())
        tipuri[produs.get_tip()]++;
    int y = 100;
    for (const auto& tip: tipuri){
        QWidget* fereastra  = new QWidget;
        QVBoxLayout* layout = new QVBoxLayout;
        fereastra->setLayout(layout);

        QLabel* label = new QLabel;
        label->setText(QString::number(tip.second));
        layout->addWidget(label);
        fereastra->setWindowTitle(QString::fromStdString(tip.first));
        fereastra->move(-1000, y);
        fereastra->resize(400,20);
        y+=100;
        ferestre_tip.push_back(fereastra);
    }
    for (const auto& fereastra: ferestre_tip)
        fereastra->show();
}
