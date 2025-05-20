import math
import random
from random import sample
from math import factorial
from itertools import permutations
from math import perm, comb
from itertools import permutations, combinations

def permutari(word,nr_total=False, aleator=False):
    if nr_total == True:
        nr_permutari = factorial(len(word))
        print(nr_permutari)
    elif aleator == True:
        permutari = []
        for perm in permutations(word):
            permutari.append(perm)
        perm_random = random.sample(permutari, 1)
        print(perm_random)
    else:
        for perm in permutations(word):
            print(perm)


# 3
def aranjamente(word, nr, nr_total=False, aleator=False):
    if nr_total == True:
        n = len(word)
        nr_aranj = factorial(n)//factorial(n-nr)
        print(nr_aranj)
    elif aleator == True:
        aranjamente = []
        for aranj in permutations(word,nr):
            aranjamente.append(aranj)
        random_aranj = random.sample(aranjamente, 1)
        print(random_aranj)
    else:
        for aranj in permutations(word, nr):
            print(aranj)


def combinari(word, nr, nr_total=False, aleator=False):
    if nr_total == True:
        n = len(word)
        #nr_aranj = factorial(n)//(factorial(n-nr)*factorial(nr))
        nr_aranj = math.comb(len(word), nr)
        print(nr_aranj)
    elif aleator == True:
        combinari = []
        for comb in combinations(word,nr):
            combinari.append(comb)
        random_comb = random.sample(combinari, 1)
        print(random_comb)
    else:
        for comb in combinations(word, nr):
            print(comb)

def main():
    print("PERMUTARI:\n")
    #lista cu toate permutarile
    permutari("word")
    #numarul total de permutari
    permutari("word", nr_total=True)
    #permutare aleatoare
    permutari("word",aleator=True)

    print("\nARANJAMENTE:\n")
    #toate aranjamentele
    aranjamente("word", 2)
    #numar de aranjamente
    aranjamente("word",2, nr_total=True)
    #un aranjament aleator
    aranjamente("word", 2, aleator=True)

    print("\nCOMBINARI:\n")
    #toate combinarile
    combinari("word", 2)
    #numar de combinari
    combinari("word", 2, nr_total=True)
    #o combinare aleatoare
    combinari("word", 2,  aleator=True)


main()