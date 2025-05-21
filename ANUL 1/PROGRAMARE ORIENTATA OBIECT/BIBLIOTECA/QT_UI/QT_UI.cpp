//
// Created by Karina Kentsch on 5/6/2023.
//

#include "QT_UI.h"
#include "QHBoxLayout"
#include "QFormLayout"
#include "../errors/RepoError.h"
#include "QMessageBox"
#include "../errors/ValidationError.h"
#include "map"
#include "CosCrudGUI.h"
#include "CosReadOnlyGUI.h"
#include "MyTableModel.h"

using std::map;
using std::to_string;

void QT_UI::Assign_task() {
    QObject::connect(buton_adauga, &QPushButton::clicked, this, &QT_UI::adauga_ui);
    QObject::connect(buton_sterge, &QPushButton::clicked, this, &QT_UI::sterge_ui);
    QObject::connect(buton_modifica, &QPushButton::clicked, this, &QT_UI::modifica_ui);
    QObject::connect(buton_cauta, &QPushButton::clicked, this, &QT_UI::cauta_ui);
    QObject::connect(buton_filtru_titlu, &QPushButton::clicked, this, &QT_UI::filtru_titlu_ui);
    QObject::connect(buton_filtru_an, &QPushButton::clicked, this, &QT_UI::filtru_an_ui);
    QObject::connect(buton_sort, &QPushButton::clicked, this, &QT_UI::sortare_ui);
    QObject::connect(buton_undo, &QPushButton::clicked, this, &QT_UI::undo_ui);
    QObject::connect(buton_cos_crud, &QPushButton::clicked, this, &QT_UI::show_cos_crud);
    QObject::connect(buton_cos_read_only, &QPushButton::clicked, this, &QT_UI::show_cos_read_only);

//    ///butoane pt cos
    QObject::connect(buton_adauga_cos, &QPushButton::clicked, this, &QT_UI::adauga_cos_ui);
    QObject::connect(buton_goleste_cos, &QPushButton::clicked, this, &QT_UI::goleste_cos_ui);
    QObject::connect(buton_random_cos, &QPushButton::clicked, this, &QT_UI::random_cos_ui);
//    QObject::connect(buton_export_cos, &QPushButton::clicked, this, &QT_UI::export_ui);

//    QObject::connect(buton_reload, &QPushButton::clicked, [&](){
//        this->afisare_lista(lista_carti.get_all_service());
//    });
}

void QT_UI::Button_design() {

    QHBoxLayout* main_layout = new QHBoxLayout;
    QWidget* left = new QWidget;

    main->setLayout(main_layout);

    QVBoxLayout* layout_left = new QVBoxLayout;
    left->setLayout(layout_left);

    QWidget* form  = new QWidget;
    QFormLayout * layout_form = new QFormLayout;
    form->setLayout(layout_form);

    layout_form->addRow(text_box_id, edit_id);
    layout_form->addRow(text_box_titlu, edit_titlu);
    layout_form->addRow(text_box_autor, edit_autor);
    layout_form->addRow(text_box_gen, edit_gen);
    layout_form->addRow(text_box_an_aparitie, edit_an_aparitie);
    layout_form->addRow(text_box_titlu_cos, edit_titlu_cos);
    layout_form->addRow(text_box_nr_random_cos, edit_nr_random_cos);
    layout_form->addRow(text_box_nume_fisier_cos, edit_nume_fisier_cos);

    layout_left->addWidget(form);

    layout_left->addWidget(buton_adauga);
    layout_left->addWidget(buton_sterge);
    layout_left->addWidget(buton_modifica);
    layout_left->addWidget(buton_cauta);
    layout_left->addWidget(buton_filtru_titlu);
    layout_left->addWidget(buton_filtru_an);
    layout_left->addWidget(buton_sort);
    layout_left->addWidget(buton_undo);
    layout_left->addWidget(buton_cos_crud);
    layout_left->addWidget(buton_cos_read_only);
    layout_left->addWidget(buton_adauga_cos);
    layout_left->addWidget(buton_goleste_cos);
    layout_left->addWidget(buton_random_cos);

    right->setLayout(layout_right);

//    QStringList tableHeaderList;
//    tableHeaderList << "ID" << "TITLU" << "AUTOR" << "GEN" << "AN APARITIE";
//    this->tabel_carti->setHorizontalHeaderLabels(tableHeaderList);
//
//    this->tabel_carti->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    //layout_right->addWidget(tabel_carti);
    layout_right->addWidget(tabel_nou_carti);
    MyTableModel* model = new MyTableModel{this, lista_carti};
    tabel_nou_carti->setModel(model);
    layout_right->addWidget(lista);

    main_layout->addWidget(left);
    main_layout->addWidget(right);

    map->setLayout(map_layout);
    main_layout->addWidget(map);

//    //meniu pt cos
//    QHBoxLayout* main_cos_layout = new QHBoxLayout;
//    main_cos->setLayout(main_cos_layout);
//
//    QWidget* left_cos = new QWidget;
//    QWidget* right_cos = new QWidget;
//
//    QVBoxLayout* left_cos_layout = new QVBoxLayout;
//    QVBoxLayout* right_cos_layout = new QVBoxLayout;
//
//    left_cos->setLayout(left_cos_layout);
//    right_cos->setLayout(right_cos_layout);
//
//    main_cos_layout->addWidget(left_cos);
//    main_cos_layout->addWidget(right_cos);
//
//    QWidget* form_cos = new QWidget;
//    QFormLayout* form_cos_layout = new QFormLayout;
//    form_cos->setLayout(form_cos_layout);
//
//    form_cos_layout->addRow(text_box_titlu_cos, edit_titlu_cos);
//    form_cos_layout->addRow(text_box_nr_random_cos, edit_nr_random_cos);
//    form_cos_layout->addRow(text_box_nume_fisier_cos, edit_nume_fisier_cos);
//
//    left_cos_layout->addWidget(form_cos);
//
//    left_cos_layout->addWidget(buton_adauga_cos);
//    left_cos_layout->addWidget(buton_goleste_cos);
//    left_cos_layout->addWidget(buton_random_cos);
//    left_cos_layout->addWidget(buton_export_cos);
//
//    right_cos_layout->addWidget(tabel_carti_cos);
//    QStringList tableHeaderListCos;
//    tableHeaderListCos << "ID" << "TITLU" << "AUTOR" << "GEN" << "AN APARITIE";
//    tabel_carti_cos->setHorizontalHeaderLabels(tableHeaderList);
//    tabel_carti_cos->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    //size-ul ferestrelor
    main->resize(left->width() + right->width(), left->height());
//    main_cos->resize(left_cos->width()+ right_cos->width(), left_cos->height());
}

void QT_UI::adauga_ui() {
    int id = edit_id->text().toInt();
    string titlu = edit_titlu->text().toStdString();
    string autor = edit_autor->text().toStdString();
    string gen = edit_gen->text().toStdString();
    int an_aparitie = edit_an_aparitie->text().toInt();

    try{
        clear_field();
        this->lista_carti.adauga_carte_service(titlu, gen, autor, an_aparitie, id);
        afisare_lista(lista_carti.get_all_service());
        show_map();
    }catch(RepoError& eroare){
       QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch (ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }

}

void QT_UI::clear_field() {
    edit_id->clear();
    edit_titlu->clear();
    edit_autor->clear();
    edit_gen->clear();
    edit_an_aparitie->clear();
    edit_titlu_cos->clear();
    edit_nr_random_cos->clear();
    edit_nume_fisier_cos->clear();
}

void QT_UI::afisare_lista(const vector<Carte> &lista_c) {
//    this->tabel_carti->clearContents();
//    this->tabel_carti->setRowCount(lista_c.size());
//
//    int nrLinie = 0;
//    for (auto& carte: lista_c){
//        this->tabel_carti->setItem(nrLinie, 0, new QTableWidgetItem(QString::number(carte.get_id())));
//        this->tabel_carti->setItem(nrLinie, 1, new QTableWidgetItem(QString::fromStdString(carte.get_titlu())));
//        this->tabel_carti->setItem(nrLinie, 2, new QTableWidgetItem(QString::fromStdString(carte.get_autor())));
//        this->tabel_carti->setItem(nrLinie, 3, new QTableWidgetItem(QString::fromStdString(carte.get_gen())));
//        this->tabel_carti->setItem(nrLinie, 4, new QTableWidgetItem(QString::number(carte.get_an_aparitie())));
//        nrLinie++;
//    }
    MyTableModel* model = new MyTableModel{this, lista_carti};
    tabel_nou_carti->setModel(model);

    this->lista->clear();
    for (auto& carte: lista_c){
        string string_carte = to_string(carte.get_id()) + "," + carte.get_titlu() + "," + carte.get_autor() + "," + carte.get_gen() + "," +
                to_string(carte.get_an_aparitie());
        this->lista->addItem(QString::fromStdString(string_carte));
    }

}

void QT_UI::sterge_ui() {
    int id = edit_id->text().toInt();
    try{
        clear_field();
        this->lista_carti.sterge_carte_dupa_id_service(id);
        afisare_lista(lista_carti.get_all_service());
        show_map();
    }catch(RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch (ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void QT_UI::modifica_ui() {
    int id = edit_id->text().toInt();
    string titlu_nou = edit_titlu->text().toStdString();
    string autor_nou = edit_autor->text().toStdString();
    string gen_nou = edit_gen->text().toStdString();
    int an_aparitie_nou = edit_an_aparitie->text().toInt();

    try{
        clear_field();
        this->lista_carti.modificare_carte_service(titlu_nou, gen_nou, autor_nou, an_aparitie_nou, id);
        afisare_lista(lista_carti.get_all_service());
        show_map();
    }catch(RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch (ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void QT_UI::cauta_ui() {
    int id = edit_id->text().toInt();
    try{
        clear_field();
        Carte carte = this->lista_carti.cauta_carte_dupa_id_service(id);
        string informatie = "Id: " + to_string(carte.get_id()) + "\nTitlu: " + carte.get_titlu() + "\nAutor: " + carte.get_autor() + "\nGen: " + carte.get_gen() + "\nAn aparitie: " + to_string(carte.get_an_aparitie());
        QMessageBox::information(this, "Cartea cautata", QString::fromStdString(informatie));
    }catch(RepoError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }catch (ValidationError& eroare){
        QMessageBox::warning(this, "Warning", QString::fromStdString(eroare.get_mesaj()));
    }
}

void QT_UI::filtru_titlu_ui(){
    string titlu = edit_titlu->text().toStdString();
    clear_field();
    vector<Carte> lista_carti_filtrate = lista_carti.filtrare_carti_dupa_un_criteriu_service("titlu", titlu, 0);
    pop_up_list(lista_carti_filtrate);
}

void QT_UI::filtru_an_ui(){
    int an_aparitie = edit_an_aparitie->text().toInt();
    clear_field();
    vector<Carte> lista_carti_filtrate = lista_carti.filtrare_carti_dupa_un_criteriu_service("an", "", an_aparitie);
    pop_up_list(lista_carti_filtrate);
}

void QT_UI::sortare_ui() {
    QWidget* pop_up_sort = new QWidget;
    QHBoxLayout* pop_up_sort_layout = new QHBoxLayout;

    QRadioButton* titlu = new QRadioButton("TITLU");
    QRadioButton* autor = new QRadioButton("AUTOR");
    QRadioButton* an = new QRadioButton("AN");
    QRadioButton* gen = new QRadioButton("GEN");
    QRadioButton* an_si_gen = new QRadioButton("AN SI GEN");

    QPushButton* ready_to_sort = new QPushButton("SORT");
    QObject::connect(ready_to_sort, &QPushButton::clicked, this, [=](){
        if (titlu->isChecked())
            lista_carti.sortare_lista_carti_dupa_un_criteriu("titlu");
        else
            if (autor->isChecked())
                lista_carti.sortare_lista_carti_dupa_un_criteriu("autor");
            else
                if (an->isChecked())
                    lista_carti.sortare_lista_carti_dupa_un_criteriu("an");
                else
                    if (gen->isChecked())
                        lista_carti.sortare_lista_carti_dupa_un_criteriu("gen");
                    else
                        if (an_si_gen->isChecked())
                            lista_carti.sortare_lista_carti_dupa_un_criteriu("an+gen");
        afisare_lista(lista_carti.get_all_service());
    });
    pop_up_sort_layout->addWidget(ready_to_sort);
    pop_up_sort_layout->addWidget(titlu);
    pop_up_sort_layout->addWidget(autor);
    pop_up_sort_layout->addWidget(an);
    pop_up_sort_layout->addWidget(gen);
    pop_up_sort_layout->addWidget(an_si_gen);

    pop_up_sort->setLayout(pop_up_sort_layout);
    pop_up_sort->show();
}

void QT_UI::pop_up_list(const vector<Carte> &lista) {
    QWidget* pop_up = new QWidget;
    QVBoxLayout* pop_up_layout = new QVBoxLayout;

    int nr_lin = lista.size();
    int nr_col = 5;
    QTableWidget* tabel_pop_up = new QTableWidget{nr_lin, nr_col};

    QStringList tableHeaderList;
    tableHeaderList << "ID" << "TITLU" << "AUTOR" << "GEN" << "AN APARITIE";
    tabel_pop_up->setHorizontalHeaderLabels(tableHeaderList);
    tabel_pop_up->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
    tabel_pop_up->clearContents();

    int nrLinie = 0;
    for (auto& carte: lista){
        tabel_pop_up->setItem(nrLinie, 0, new QTableWidgetItem(QString::number(carte.get_id())));
        tabel_pop_up->setItem(nrLinie, 1, new QTableWidgetItem(QString::fromStdString(carte.get_titlu())));
        tabel_pop_up->setItem(nrLinie, 2, new QTableWidgetItem(QString::fromStdString(carte.get_autor())));
        tabel_pop_up->setItem(nrLinie, 3, new QTableWidgetItem(QString::fromStdString(carte.get_gen())));
        tabel_pop_up->setItem(nrLinie, 4, new QTableWidgetItem(QString::number(carte.get_an_aparitie())));
        nrLinie++;
    }
    pop_up_layout->addWidget(tabel_pop_up);
    pop_up->setLayout(pop_up_layout);
    pop_up->show();
}

void QT_UI::undo_ui() {
    if (lista_carti.lungime_lista_undo() == 0)
        QMessageBox::warning(this, "Warning", QString::fromStdString("Nu se mai poate face undo!"));
    else {
        lista_carti.undo();
        afisare_lista(lista_carti.get_all_service());
    }
}

void QT_UI::export_ui() {
    string nume_fisier = edit_nume_fisier_cos->text().toStdString();
    clear_field();
    lista_carti.export_in_fisier(nume_fisier);
}

void QT_UI::goleste_cos_ui() {
    lista_carti.goleste_cos_service();
    afisare_cos();
}

void QT_UI::adauga_cos_ui() {
    string titlu = edit_titlu_cos->text().toStdString();
    clear_field();
    lista_carti.adauga_cos_service(titlu);
    afisare_cos();
}

void QT_UI::random_cos_ui() {
    int nr_random = edit_nr_random_cos->text().toInt();
    clear_field();
    lista_carti.genereaza_cos_service(nr_random);
    afisare_cos();
}

void QT_UI::show_cos_crud() {
    CosCrudGUI* cos_crud_gui = new CosCrudGUI{lista_carti};
    cos_crud_gui->ruleaza_cos_gui();

}

void QT_UI::show_cos_read_only() {
    CosReadOnlyGUI* cos_read_only_gui = new CosReadOnlyGUI{lista_carti};
    cos_read_only_gui->show();
}

void QT_UI::afisare_cos() {
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

void QT_UI::show_map() {
    for(auto& buton: butoane)
        delete buton;
    butoane.clear();

    auto map_carti = lista_carti.map_cheie_titlu();
    for (auto& element: map_carti){
        auto titlu = element.first;
        auto carte = element.second;
        QPushButton* buton = new QPushButton(QString::fromStdString(titlu));
        butoane.push_back(buton);
        QObject::connect(buton, &QPushButton::clicked, [this, carte](){
            QMessageBox::information(this, "NUMAR", QString::number(carte.size()));
        });
        map_layout->addWidget(buton);
    }
}


