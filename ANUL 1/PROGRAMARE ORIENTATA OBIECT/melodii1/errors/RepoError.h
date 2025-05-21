//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_REPOERROR_H
#define MELODII1_REPOERROR_H
#include "string"

using std::string;

class RepoError{
private:
    string mesaj;
public:
    RepoError(string mesaj):mesaj{mesaj}{}

    const string get_mesaj() const{
        return mesaj;
    }
};

#endif //MELODII1_REPOERROR_H
