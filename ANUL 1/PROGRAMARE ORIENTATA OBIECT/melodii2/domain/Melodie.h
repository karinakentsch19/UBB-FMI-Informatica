//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_MELODIE_H
#define MELODII2_MELODIE_H
#include "string"

using std::string;

class Melodie {
private:
    int id;
    string titlu, artist, gen;
public:
    Melodie(int id, string titlu, string artist, string gen): id{id}, titlu{titlu}, artist{artist}, gen{gen}{}

    /**
     * Return id
     * @return id
     */
    int get_id() const;

    /**
     * Returneaza titlu
     * @return titlu
     */
    string get_titlu() const;

    /**
     * Return artist
     * @return artist
     */
    string get_artist() const;

    /**
     * Return gen
     * @return gen
     */
    string get_gen() const;
};


#endif //MELODII2_MELODIE_H
