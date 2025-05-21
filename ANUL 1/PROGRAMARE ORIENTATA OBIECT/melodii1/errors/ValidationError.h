//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_VALIDATIONERROR_H
#define MELODII1_VALIDATIONERROR_H
#include "string"

using std::string;

class ValidationError{
private:
    string mesaj;
public:
    ValidationError(string mesaj):mesaj{mesaj}{}

    const string get_mesaj() const{
        return mesaj;
    }
};

#endif //MELODII1_VALIDATIONERROR_H
