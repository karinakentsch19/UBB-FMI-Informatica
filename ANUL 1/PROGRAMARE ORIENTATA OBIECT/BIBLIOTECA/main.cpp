#include <QApplication>
#include <QPushButton>
#include "QT_UI/QT_UI.h"
#include "repository/RepositoryBibliotecaFile.h"

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
//    RepositoryBiblioteca lista_repo;
    RepositoryBibliotecaFile lista_repo{"file.txt"};
    Validator validator;
    ServiceBiblioteca lista_service{lista_repo, validator};
    QT_UI consola{lista_service};
    consola.ruleaza_ui();
    return QApplication::exec();
}
