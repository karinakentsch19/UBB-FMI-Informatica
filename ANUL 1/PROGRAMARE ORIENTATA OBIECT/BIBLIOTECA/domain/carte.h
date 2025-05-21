//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_CARTE_H
#define LAB6_7_BIBLIOTECA_CARTE_H
#include "string"
#include "iostream"
using std::string;
using std::cout;

class Carte {
private:
    string titlu, gen, autor;
    int an_aparitie, id;
public:
    /**
     * Constructor carte
     * @param titlu string
     * @param gen string
     * @param autor string
     * @param an_aparitie int
     */
    Carte(string titlu, string gen, string autor, int an_aparitie, int id);

    Carte(){}

//    Carte(const Carte& carte): titlu{carte.titlu}, gen{carte.gen}, autor{carte.autor}, an_aparitie{carte.an_aparitie}, id{carte.id}{
//        cout << "\nCarte copiata\n";
//    }

    /**
     * Returneaza id-ul cartii
     * @return id
     */
    int get_id() const;

    /**
     * Returneaza titlul cartii
     * @return titlu
     */
    string get_titlu() const;

    /**
     * Returneaza genul cartii
     * @return gen
     */
    string get_gen() const;

    /**
     * Returneaza autorul cartii
     * @return autor
     */
    string get_autor() const;

    /**
     * Returneaza anul de aparitie al cartii
     * @return an aparitie
     */
    int get_an_aparitie() const;

    /**
     * Seteaza un titlu nou
     * @param titlu_nou string
     */
    void set_titlu(string titlu_nou);

    /**
     * Seteaza un gen nou
     * @param gen_nou string
     */
    void set_gen(string gen_nou);

    /**
     * Seteaza un autor nou
     * @param autor_nou string
     */
    void set_autor(string autor_nou);

    /**
     * Seteaza un an de aparitie nou
     * @param an_aparitie_nou int
     */
    void set_an_aparitie(int an_aparitie_nou);

    /**
     * Transforma operatorul == in comparare dupa id
     * @param c2 carte
     * @return true - daca sunt egale dupa id
     *         false - daca nu sunt egale dupa id
     */
    bool operator== (const Carte &c2) const;

    /**
     * Operatorul = devine atribuire intre carti
     * @param c carte
     */
    void operator=(const Carte& c);
};


#endif //LAB6_7_BIBLIOTECA_CARTE_H
