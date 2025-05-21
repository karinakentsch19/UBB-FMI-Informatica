//
// Created by Karina Kentsch on 3/31/2023.
//

#include "ValidationError.h"

string ValidationError::get_mesaj() {
    return this->mesaj;
}

ValidationError::ValidationError(string mesaj) {
    this->mesaj = mesaj;
}
