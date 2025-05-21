//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_TABLEVIEWMODEL_H
#define PRODUSE_TABLEVIEWMODEL_H
#include "QAbstractTableModel"
#include "../domain/Produs.h"
#include "vector"
#include "cstring"

using std::vector;

class TableModel: public QAbstractTableModel{
private:
    vector<Produs> lista_produse;
    double pret_dat;

    int numar_vocale_din_nume(Produs produs) const{
        int cont = 0;
        string nume = produs.get_nume();
        for(int i = 0; i < nume.size(); i++)
            if (strchr("aeiouAEIOU", nume[i]))
                cont++;
        return cont;
    }

public:

    TableModel(vector<Produs> lista_produse, double pret_filtrare): lista_produse{lista_produse}, pret_dat{pret_filtrare}{}

    ~TableModel(){}

    void set_pret_filtrare(double pret_filtrare){
        pret_dat = pret_filtrare;
        emit layoutChanged();
    }

    void set_lista(vector<Produs> lista){
        lista_produse = lista;
        emit layoutChanged();
    }

    int rowCount(const QModelIndex &parent = QModelIndex()) const{
        return lista_produse.size();
    }

    int columnCount(const QModelIndex &parent = QModelIndex()) const{
        return 5;
    }

    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const{
        int row = index.row();
        int column = index.column();
        Produs produs = lista_produse[row];

        if (role == Qt::DisplayRole){
            if (column == 0)
                return QString::number(produs.get_id());
            if (column == 1)
                return QString::fromStdString(produs.get_nume());
            if (column == 2)
                return QString::fromStdString(produs.get_tip());
            if (column == 3)
                return QString::number(produs.get_pret());
            if (column == 4)
                return QString::number(numar_vocale_din_nume(produs));
        }


        if (role == Qt::ForegroundRole){
            if (produs.get_pret() <= pret_dat){
                return QBrush(Qt::red);
            }
        }
        return QVariant();
    }
};

#endif //PRODUSE_TABLEVIEWMODEL_H
