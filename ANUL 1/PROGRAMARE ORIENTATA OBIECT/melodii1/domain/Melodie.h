//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_MELODIE_H
#define MELODII1_MELODIE_H

#include "string"

using std::string;

class Melodie {
private:
    int id;
    string titlu, artist;
    int rank;

public:

    Melodie(int id, string titlu, string artist, int rank):id{id}, titlu{titlu}, artist{artist}, rank{rank}{}

    /**
     * Returneaza id
     * @return id
     */
    const int get_id() const;

    /**
     * Returneaza titlu
     * @return titlu
     */
    const string get_titlu() const;

    /**
     * Returneaza autor
     * @return autor
     */
    const string get_artist() const;

    /**
     * Returneaza rank
     * @return rank
     */
    const int get_rank() const;

    /**
     * Seteaza titlu
     * @param titlu_nou string
     */
    void set_titlu(string titlu_nou);

    /**
     * Seteaza rank
     * @param rank int
     */
    void set_rank(int rank_nou);
};


#endif //MELODII1_MELODIE_H
