//
// Created by Karina Kentsch on 3/31/2023.
//

#include "RepoError.h"

#include <utility>

RepoError::RepoError(string mesaj) {
    this->mesaj = std::move(mesaj);
}

string RepoError::get_mesaj() {
    return this->mesaj;
}
