//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_REPOERROR_H
#define PRODUSE_REPOERROR_H

#include "string"
using std::string;

class RepoError{
private:
    string mesaj;
public:
    RepoError(string mesaj): mesaj{mesaj}{}

    const string get_mesaj() const{
        return mesaj;
    }
};

#endif //PRODUSE_REPOERROR_H
