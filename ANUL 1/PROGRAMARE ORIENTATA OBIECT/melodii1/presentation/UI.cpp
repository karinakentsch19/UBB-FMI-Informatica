//
// Created by Karina Kentsch on 6/14/2023.
//

#include "UI.h"
#include "QHBoxLayout"
#include "QVBoxLayout"
#include "QFormLayout"
#include "QStringList"
#include "QHeaderView"
#include "../errors/RepoError.h"
#include "../errors/ValidationError.h"
#include "../errors/ServiceError.h"
#include "QMessageBox"

void UI::AssignTask() {
    QObject::connect(buton_adauga, &QPushButton::clicked, this, &UI::adauga);
    QObject::connect(buton_sterge, &QPushButton::clicked, this, &UI::sterge);
    QObject::connect(buton_modifica, &QPushButton::clicked, this, &UI::modifica);
    QObject::connect(table, &QTableWidget::itemSelectionChanged, this, &UI::selectare_tabel);
}

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
    form_layout->addRow("TITLU", edit_titlu);
    form_layout->addRow("ARTIST", edit_artist);
    form_layout->addRow("RANK",slider_rank);
    form_layout->addRow(buton_adauga);
    form_layout->addRow(buton_sterge);
    form_layout->addRow(buton_modifica);

    left_layout->addWidget(form);
    slider_rank->setMinimum(0);
    slider_rank->setMaximum(10);

    QWidget* right = new QWidget;
    QVBoxLayout* right_layout = new QVBoxLayout;
    right->setLayout(right_layout);

    QStringList table_header;
    table_header << "ID" << "TITLU" << "ARTIST" << "RANK";
    table->setHorizontalHeaderLabels(table_header);
    table->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    right_layout->addWidget(table);
    right_layout->addWidget(table_view);
    table_view->setModel(table_model);

    this_layout->addWidget(left);
    this_layout->addWidget(right);
    this->resize(left->width()+right->width(), left->height());
}

void UI::afisare() {
    table->clearContents();
    table->setRowCount(lista_melodii.get_lungime_service());
    int row = 0;
    for (const auto& melodie: lista_melodii.get_all_service()){
        table->setItem(row, 0, new QTableWidgetItem(QString::number(melodie.get_id())));
        table->setItem(row, 1, new QTableWidgetItem(QString::fromStdString(melodie.get_titlu())));
        table->setItem(row, 2, new QTableWidgetItem(QString::fromStdString(melodie.get_artist())));
        table->setItem(row, 3, new QTableWidgetItem(QString::number(melodie.get_rank())));
        row++;
    }

    table_model->set_lista(lista_melodii.get_all_service());
}

void UI::adauga() {
    int id = edit_id->text().toInt();
    string titlu = edit_titlu->text().toStdString();
    string artist = edit_artist->text().toStdString();
    int rank = slider_rank->value();

    try{
        clear_field();
        lista_melodii.adauga_melodie_service(id, titlu, artist, rank);
        afisare();
        repaint();
    }catch (RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ServiceError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void UI::sterge() {
    int id = edit_id->text().toInt();

    try{
        clear_field();
        lista_melodii.sterge_dupa_id_service(id);
        afisare();
        repaint();
    }catch (RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ServiceError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void UI::modifica() {
    int id = edit_id->text().toInt();
    string titlu = edit_titlu->text().toStdString();
    int rank = slider_rank->value();

    try{
        clear_field();
        lista_melodii.modifica_melodie_service(id, titlu, rank);
        afisare();
        repaint();
    }catch (RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ServiceError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void UI::clear_field() {
    edit_id->clear();
    edit_titlu->clear();
    edit_artist->clear();
}

void UI::selectare_tabel() {
    auto item = table->selectedItems();
    if (item.size() == 4){
        int id = item.at(0)->text().toInt();
        string titlu = item.at(1)->text().toStdString();
        string artist = item.at(2)->text().toStdString();
        int rank = item.at(3)->text().toInt();

        edit_id->setText(QString::number(id));
        edit_titlu->setText(QString::fromStdString(titlu));
        edit_artist->setText(QString::fromStdString(artist));
        slider_rank->setValue(rank);
    }
}
