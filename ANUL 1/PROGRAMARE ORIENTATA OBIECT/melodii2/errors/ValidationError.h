//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_VALIDATIONERROR_H
#define MELODII2_VALIDATIONERROR_H
#include "string";
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
#endif //MELODII2_VALIDATIONERROR_H
