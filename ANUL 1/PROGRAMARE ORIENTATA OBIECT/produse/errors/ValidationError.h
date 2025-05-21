//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_VALIDATIONERROR_H
#define PRODUSE_VALIDATIONERROR_H
#include "string"
using std::string;

class ValidationError{
private:
    string mesaj;
public:
    ValidationError(string mesaj): mesaj{mesaj}{}

    const string get_mesaj() const{
        return mesaj;
    }
};

#endif //PRODUSE_VALIDATIONERROR_H
