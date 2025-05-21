//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_SERVICEERROR_H
#define MELODII1_SERVICEERROR_H
#include "string"

using std::string;

class ServiceError{
private:
    string mesaj;
public:
    ServiceError(string mesaj):mesaj{mesaj}{}

    const string get_mesaj() const{
        return mesaj;
    }
};
#endif //MELODII1_SERVICEERROR_H
