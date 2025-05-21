//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_VALIDATIONERROR_H
#define LAB6_7_BIBLIOTECA_VALIDATIONERROR_H
#include "string"
using std::string;

class ValidationError {
private:
    string mesaj;
public:
    /**
     * Constructor ValidationError
     */
    ValidationError(string mesaj);

    /**
     * Returneaza mesajul de eroare
     * @return mesaj de eroare
     */
    string get_mesaj();

};


#endif //LAB6_7_BIBLIOTECA_VALIDATIONERROR_H
