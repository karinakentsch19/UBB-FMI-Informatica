def minim(lista, key):
    """
    Returneaza minimul listei
    :param key: criteriu comparare
    :param lista: lista
    :return: minim
    """
    minim = lista[0]
    for i in range(1, len(lista)):
        if key(lista[i]) < key(minim):
            minim = lista[i]
    return minim

def maxim(lista, key):
    """
    Returneaza maximul listei
    :param key: criteriu comparare
    :param lista: lista
    :return: maxim
    """
    maxim = lista[0]
    for i in range(1, len(lista)):
        if key(lista[i]) > key(maxim):
            maxim = lista[i]
    return maxim

def BingoSort(lista, key, reverse=False):
    """
    -> gaseste cea mai mica valuare numita bingo
    -> deplaseaza elementele listei egale cu bingo in positia corespunzatoare
    -> gaseste nextBingo (cel mai mic element mai mare ca bingo)
    :param lista:lista
    :param key: criteriu comparare
    :param reverse:true/false
    :return: lista sortata
    """
    if not reverse:
        bingo = minim(lista, key)
        next_bingo = maxim(lista, key)
        element_maxim = next_bingo
        pozitie = 0

        while bingo < next_bingo:
            start_poz = pozitie
            for i in range(start_poz, len(lista)):
                if key(lista[i]) == key(bingo):
                    lista[i], lista[pozitie] = lista[pozitie], lista[i]
                    pozitie = pozitie + 1
                else:
                    if key(lista[i]) < key(next_bingo):
                        next_bingo = lista[i]
            bingo = next_bingo
            next_bingo = element_maxim
    else:
        bingo = maxim(lista, key)
        next_bingo = minim(lista, key)
        element_minim = next_bingo
        pozitie = 0

        while bingo > next_bingo:
            start_poz = pozitie
            for i in range(start_poz, len(lista)):
                if key(lista[i]) == key(bingo):
                    lista[i], lista[pozitie] = lista[pozitie], lista[i]
                    pozitie = pozitie + 1
                else:
                    if key(lista[i]) > key(next_bingo):
                        next_bingo = lista[i]
            bingo = next_bingo
            next_bingo = element_minim

    return lista
