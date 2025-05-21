//
// Created by Karina Kentsch on 4/12/2023.
//

#ifndef LAB6_7_BIBLIOTECA_MYVECTOR_H
#define LAB6_7_BIBLIOTECA_MYVECTOR_H

#endif //LAB6_7_BIBLIOTECA_MYVECTOR_H

template < typename TElem >

class Iterator;

template < typename TElem >

class MyVector{
private:
    friend class Iterator<TElem>;
    TElem* lista;
    int dimensiune, capacitate;

    /**
     * Redimensionarea vectorului
     * Se aloca un spatiu dublu de memorie
     * Se copiaza elementele
     * Se dealoca vectorul initial
     * Se dubleaza capacitatea
     */
    void redimensionare(){
        TElem* lista_noua = new TElem[2 * capacitate];
        for (int i = 0; i < dimensiune; i++)
            lista_noua[i] = lista[i];
        delete[] lista;
        capacitate = 2 * capacitate;
        lista = lista_noua;
    }

public:
    /**
     * Constructor (vectorul alocat dinamic e vid dupa apelul constructorului)
     */
    MyVector(){
        capacitate = 2;
        dimensiune = 0;
        lista = new TElem[capacitate];
    }

    //copy constructor
    MyVector(const MyVector<TElem>& ot) {
        lista = new TElem[ot.capacitate];
        //copiez elementele
        for (int i = 0; i < ot.dimensiune; i++) {
            lista[i] = ot.lista[i];
        }
        dimensiune = ot.dimensiune;
        capacitate = ot.capacitate;
    }

    MyVector(MyVector&& other){
        lista = other.lista;
        dimensiune = other.dimensiune;
        capacitate = other.capacitate;

        other.lista = nullptr;
        other.dimensiune = 0;
        other.capacitate = 0;
    }

    MyVector& operator=(MyVector& other){
        if (this == &other)
            return *this;
        delete[] lista;
        lista = other.lista;
        dimensiune = other.dimensiune;
        capacitate = other.capacitate;
        other.lista = nullptr;
        other.dimensiune = 0;
        other.capacitate = 0;
        return *this;
    }

    MyVector<TElem>& operator=(const MyVector<TElem>& ot) {
        if (this == &ot) {
            return *this;
        }
        delete[] lista;
        lista = new TElem[ot.capacitate];
        //copiez elementele
        for (int i = 0; i < ot.dimensiune; i++) {
            lista[i] = ot.lista[i];
        }
        dimensiune = ot.dimensiune;
        capacitate = ot.capacitate;
        return *this;
    }

    /**
     * Destructor (se dealoca vectorul)
     */
    ~MyVector(){
        delete[] lista;
    };

    /**
     * Returneaza dimensiunea vectorului
     * @return dimensiune
     */
    const int size() const{
        return dimensiune;
    }

    /**
     * Adauga un element la finalul listei
     * @param element TElem
     */
    void push_back(const TElem& element){
        if (dimensiune == capacitate)
            redimensionare();
        lista[dimensiune] = element;
        dimensiune++;
    }

    /**
     * Sterge elementul de pe ultima pozitie din lista
     */
    void pop_back(){
        dimensiune--;
    }

    /**
     * Returneaza un iterator pe lista care refera primul element al listei
     * @return iterator
     */
    Iterator<TElem> begin() const{
        return Iterator(*this);
    }

    /**
     * Returneaza un iterator pe lista care refera locatia de dupa ultimul element al listei
     * @return  iterator
     */
    Iterator<TElem> end() const{
        return Iterator(*this, dimensiune);
    }

    /**
     * Returneaza elementul din lista de pe o pozitie data
     * @param pozitie int
     * @return elementul de pe pozitia data
     */
    TElem& operator[](const int pozitie)const{
        return lista[pozitie];
    }
};

template < typename TElem >
class Iterator{
private:
    int curent;
    const MyVector<TElem> &vector;
public:
    /**
     * Constructor iterator
     * @param lista MyVector <TElem>
     */
    Iterator(const MyVector<TElem> &lista):curent{0}, vector{lista}{}

    /**
     * Constructor iterator care seteaza curent pe o anumita pozitie
     * @param lista MyVector <TElem>
     * @param pozitie int
     */
    Iterator(const MyVector<TElem> &lista, const int pozitie):curent{pozitie}, vector{lista}{}

    /**
     * Suprascrie operatorul == astfel incat acesta sa compare iteratorul curent cu un alt iterator
     * Verifica daca iteratorul refera acelasi curent
     * @param iterator Iterator <TElem>
     * @return true/false
     */
    const bool operator==(const Iterator <TElem> &iterator) const{
        if (this->curent == iterator.curent)
            return true;
        return false;
    }
    /**
     * Suprascrie operatorul != astfel incat acesta sa compare iteratorul curent cu un alt iterator
     * Verifica daca iteratorul nu refera acelasi curent
     * @param iterator Iterator <TElem>
     * @return true/false
     */
    const bool operator!=(const Iterator <TElem> &iterator)const{
        return this->curent != iterator.curent;
    }

    /**
     * Iteratorul refera urmatorul element
     */
    void operator++(){
        this->curent++;
    }

    /**
     * Returneaza elementul curent pe care il refera iteratorul
     * @return element curent
     */
    TElem& operator*(){
        return vector.lista[this->curent];
    }

};