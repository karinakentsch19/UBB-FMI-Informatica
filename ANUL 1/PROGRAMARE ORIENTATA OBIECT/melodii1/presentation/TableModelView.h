//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII1_TABLEMODELVIEW_H
#define MELODII1_TABLEMODELVIEW_H
#include "QAbstractTableModel"
#include "vector"
#include "QFont"
#include "../domain/Melodie.h"

using std::vector;

class TableModelView : public QAbstractTableModel{
private:
    vector <Melodie> lista_melodii;

    const int numar_melodii_care_au_acelasi_rank_cu_melodie_data(const Melodie melodie) const{
        int rank_melodie = melodie.get_rank();
        int cont = 0;
        for (const auto& mel: lista_melodii)
            if (mel.get_rank() == rank_melodie)
                cont++;
        return cont;
    }

public:

    TableModelView(vector <Melodie> lista_melodii): lista_melodii{lista_melodii}{}

    void set_lista(vector <Melodie> lista_noua_melodii){
        lista_melodii = lista_noua_melodii;
        emit layoutChanged();
    }

    int rowCount(const QModelIndex &parent = QModelIndex()) const{
        return lista_melodii.size();
    }

    int columnCount(const QModelIndex &parent = QModelIndex()) const{
        return 5;
    }

    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const{
        int row = index.row();
        int column = index.column();
        Melodie melodie = lista_melodii[row];

        if (role == Qt::DisplayRole){
            if (column == 0)
                return QString::number(melodie.get_id());
            if (column == 1)
                return QString::fromStdString(melodie.get_titlu());
            if (column == 2)
                return QString::fromStdString(melodie.get_artist());
            if (column == 3)
                return QString::number(melodie.get_rank());
            if (column == 4)
                return QString::number(numar_melodii_care_au_acelasi_rank_cu_melodie_data(melodie));
        }

        return QVariant();
    }
};


#endif //MELODII1_TABLEMODELVIEW_H
