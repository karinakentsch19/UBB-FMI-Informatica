//
// Created by Karina Kentsch on 6/15/2023.
//

#include "UI.h"
#include "QHBoxLayout"
#include "QVBoxLayout"
#include "QStringList"
#include "QHeaderView"
#include "QFormLayout"
#include "../errors/RepoError.h"
#include "QMessageBox"

void UI::Design() {

    QHBoxLayout* this_layout = new QHBoxLayout;
    this->setLayout(this_layout);

    QWidget* left = new QWidget;
    QVBoxLayout* left_layout = new QVBoxLayout;
    left->setLayout(left_layout);

    QWidget* form = new QWidget;
    QFormLayout* form_layout = new QFormLayout;
    form->setLayout(form_layout);
    form_layout->addRow("TITLU", edit_titlu);
    form_layout->addRow("ARTIST", edit_artist);
    form_layout->addRow("GEN", edit_gen);
    form_layout->addRow(buton_adauga);
    form_layout->addRow(buton_sterge);

    left_layout->addWidget(form);

    QWidget* right = new QWidget;
    QVBoxLayout* right_layout = new QVBoxLayout;
    right->setLayout(right_layout);
    right_layout->addWidget(table);
    right_layout->addWidget(table_view);
    table_view->setModel(model_table);

    QStringList header_table;
    header_table << "ID" << "TITLU" << "ARTIST" << "GEN";
    table->setHorizontalHeaderLabels(header_table);
    table->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    this_layout->addWidget(left);
    this_layout->addWidget(right);
    this->resize(left->width()+right->width(), left->height());
}

void UI::AssignTask() {
    QObject::connect(buton_adauga, &QPushButton::clicked, this, &UI::adauga);
    QObject::connect(buton_sterge, &QPushButton::clicked, this, &UI::sterge);
    QObject::connect(table, &QTableWidget::itemSelectionChanged, this, &UI::selectare_tabel);
}

void UI::afisare() {
    table->clearContents();
    table->setRowCount(lista_melodii.get_lungime_service());
    int row = 0;
    for (auto& melodie: lista_melodii.get_all_service()){
        table->setItem(row, 0, new QTableWidgetItem(QString::number(melodie.get_id())));
        table->setItem(row, 1, new QTableWidgetItem(QString::fromStdString(melodie.get_titlu())));
        table->setItem(row, 2, new QTableWidgetItem(QString::fromStdString(melodie.get_artist())));
        table->setItem(row, 3, new QTableWidgetItem(QString::fromStdString(melodie.get_gen())));
        row++;
    }
    model_table->set_list(lista_melodii.get_all_service());
}

void UI::selectare_tabel() {
    auto selected_item = table->selectedItems();
    if (selected_item.size() == 4){
        int id = selected_item.at(0)->text().toInt();
        edit_id->setText(QString::number(id));
    }
}

void UI::adauga() {
    string titlu = edit_titlu->text().toStdString();
    string artist = edit_artist->text().toStdString();
    string gen = edit_gen->text().toStdString();
    try{
        clear_fields();
        lista_melodii.adauga_melodie_service(titlu, artist, gen);
        repaint();
        afisare();
    }catch (RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void UI::sterge() {
    int id = edit_id->text().toInt();
    try{
        lista_melodii.sterge_dupa_id_service(id);
        afisare();
        repaint();
    }catch (RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch(ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void UI::clear_fields() {
    edit_titlu->clear();
    edit_artist->clear();
    edit_gen->clear();
}
