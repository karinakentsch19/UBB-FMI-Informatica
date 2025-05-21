//
// Created by Karina Kentsch on 5/31/2023.
//

#ifndef BIBLIOTECA_MYTABLEMODEL_H
#define BIBLIOTECA_MYTABLEMODEL_H
#include "QAbstractTableModel"
#include "QFont"
#include "QBrush"
#include "../business/ServiceBiblioteca.h"

using std::vector;

class MyTableModel : public QAbstractTableModel{
private:
    ServiceBiblioteca& lista_carti_service;
public:
    MyTableModel(QObject *parent, ServiceBiblioteca& lista_service): QAbstractTableModel(parent), lista_carti_service{lista_service}{}

    int rowCount(const QModelIndex &parent = QModelIndex()) const override{
        return lista_carti_service.lungime_lista_service();
    }

    int columnCount(const QModelIndex &parent = QModelIndex()) const override{
        return 5;
    }

    //value at a given position
    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override{
        int row = index.row();
        int column = index.column();

        vector <Carte> lista_carti = lista_carti_service.get_all_service();
        Carte carte = lista_carti[row];

        if (role == Qt::DisplayRole){
            if (column == 0)
                return QString::number(carte.get_id());
            else
                if (column == 1)
                    return QString::fromStdString(carte.get_titlu());
                else
                    if (column == 2)
                        return QString::fromStdString(carte.get_autor());
                    else
                        if (column == 3)
                            return QString::fromStdString(carte.get_gen());
                        else
                            return QString::number(carte.get_an_aparitie());
        }

        if (role == Qt::FontRole){
            QFont f;
            f.setBold(column == 0);
            f.setItalic(row % 2 == 0);
            return f;
        }

        if (role == Qt::BackgroundRole){
            if (carte.get_id() % 2 == 0){
                QBrush color(Qt::cyan);
                return color;
            }
        }

        return QVariant();
    }
};

#endif //BIBLIOTECA_MYTABLEMODEL_H
