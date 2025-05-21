#include <QApplication>
#include <QPushButton>
#include "tests/Test.h"
#include "presentation/UI.h"

int main(int argc, char *argv[]) {
    Test test;
    test.ruleaza_toate_testele();
    QApplication a(argc, argv);
    RepoProdus lista_repo{"file.txt"};
    Validator validator;
    ServiceProdus lista_service{lista_repo, validator};
    UI gui{lista_service};
    gui.ruleaza_ui();
    return QApplication::exec();
}
