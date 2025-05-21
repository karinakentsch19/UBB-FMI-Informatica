//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_REPOERROR_H
#define LAB6_7_BIBLIOTECA_REPOERROR_H
#include "string"
using std::string;

class RepoError {
private:
    string mesaj;
public:

    /**
     * Constructor RepoError
     * @param mesaj string
     */
    RepoError(string mesaj);

    /**
     * Returneaza mesajul de eroare
     * @return mesaj de eroare
     */
    string get_mesaj();
};


#endif //LAB6_7_BIBLIOTECA_REPOERROR_H
