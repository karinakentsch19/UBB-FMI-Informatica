//
// Created by Karina Kentsch on 5/31/2023.
//

#ifndef BIBLIOTECA_COSREADONLYGUI_H
#define BIBLIOTECA_COSREADONLYGUI_H
#include "QPainter"
#include "QWidget"
#include "../business/ServiceBiblioteca.h"
#include "../Observer/Observer.h"

class CosReadOnlyGUI : public QWidget, public Observer{
private:
    ServiceBiblioteca& lista_carti;

    QWidget* main_cos_read_only_gui = new QWidget;

    void paintEvent(QPaintEvent* event) override{
        QPainter painter{this};
        int x = 100;
        for (auto& obiect_cos: lista_carti.get_cos_service()) {
            painter.drawLine(x, 10, x, 100);
            x+=10;
        }
    }

    void update() override{
        repaint();
    }

public:
    CosReadOnlyGUI(ServiceBiblioteca& lista_service): lista_carti{lista_service}{
        lista_carti.addObserver(this);
    }

    void ruleaza_main_cos_read_only_gui(){
        main_cos_read_only_gui->show();
    }
};


#endif //BIBLIOTECA_COSREADONLYGUI_H
