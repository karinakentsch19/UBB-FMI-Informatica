//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_TABLEMODELVIEW_H
#define MELODII2_TABLEMODELVIEW_H
#include "QAbstractTableModel"
#include "vector"
#include "../domain/Melodie.h"
using std::vector;

class TableModelView: public QAbstractTableModel{
private:
    vector<Melodie> lista;

    int nr_melodii_acelasi_artist(Melodie melodie) const{
        int cont = 0;
        for (auto& mel: lista)
            if (mel.get_artist() == melodie.get_artist())
                cont++;
        return cont;
    }

    int nr_melodii_acelasi_gen(Melodie melodie) const{
        int cont = 0;
        for (auto& mel: lista)
            if (mel.get_gen() == melodie.get_gen())
                cont++;
        return cont;
    }


public:
    TableModelView(vector <Melodie> lista): lista{lista}{}

    void set_list(vector <Melodie> list){
        lista = list;
        emit layoutChanged();
    }

    int rowCount(const QModelIndex &parent = QModelIndex()) const{
        return lista.size();
    }
    int columnCount(const QModelIndex &parent = QModelIndex()) const {
        return 6;
    }
    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const{
        int row = index.row();
        int column = index.column();
        Melodie melodie = lista[row];

        if (role == Qt::DisplayRole){
            if (column == 0)
                return QString::number(melodie.get_id());
            if (column == 1)
                return QString::fromStdString(melodie.get_titlu());
            if (column == 2)
                return QString::fromStdString(melodie.get_artist());
            if (column == 3)
                return QString::fromStdString(melodie.get_gen());
            if (column == 4)
                return QString::number(nr_melodii_acelasi_artist(melodie));
            if (column == 5)
                return QString::number(nr_melodii_acelasi_gen(melodie));
        }
        if (role == Qt::BackgroundRole){
            if (melodie.get_gen() == "folk"){
                return QBrush(Qt::magenta);
            }
        }
        if (role == Qt::FontRole){
            QFont font;
            font.setBold(column == 0);
            return font;
        }
        return QVariant();
    }
};

#endif //MELODII2_TABLEMODELVIEW_H
