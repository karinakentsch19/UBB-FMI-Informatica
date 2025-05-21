//
// Created by Karina Kentsch on 5/31/2023.
//

#ifndef BIBLIOTECA_OBSERVABLE_H
#define BIBLIOTECA_OBSERVABLE_H
#include "vector"
#include "Observer.h"

using std::vector;

class Observable{
private:
    vector<Observer*> observers;

public:
    void addObserver(Observer* obs){
        observers.push_back(obs);
    }

    void removeObserver(Observer* obs){
        auto iterator = observers.begin();
        while(iterator != observers.end() && *iterator != obs)
            iterator++;
        observers.erase(iterator);
    }

protected:
    void notify(){
        for (auto obs: observers)
            obs->update();
    }
};

#endif //BIBLIOTECA_OBSERVABLE_H
