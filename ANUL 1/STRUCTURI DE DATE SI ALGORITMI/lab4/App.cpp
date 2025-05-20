#include <iostream>
#include <cassert>

#include "TestScurt.h"
#include "TestExtins.h"
#include "MDO.h"

bool relatie(TCheie cheie1, TCheie cheie2) {
    if (cheie1 <= cheie2) {
        return true;
    }
    else {
        return false;
    }
}

void test_cea_mai_frecventa(){
    MDO multi_dict(relatie);
    multi_dict.adauga(1,2);
    multi_dict.adauga(1,3);
    multi_dict.adauga(2,3);
    multi_dict.adauga(3,3);
    assert(multi_dict.ceaMaiFrecventaValoare() == 3);
}


int main(){
    testAll();
    testAllExtins();
    std::cout<<"Finished Tests!"<<std::endl;

    test_cea_mai_frecventa();
    return 0;
}
