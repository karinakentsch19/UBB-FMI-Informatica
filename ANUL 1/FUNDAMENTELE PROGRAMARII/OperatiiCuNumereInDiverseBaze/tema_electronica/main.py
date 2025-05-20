#Kentsch Karina Ioana - grupa 214
def cifra_hexa_in_decimal(a, index):
    """
    Transforma in int cifra hexa de pe pozitia index
    :param a: string
    :param index: int
    :return: cifra int
    """
    cif1 = 0
    if a[index] == 'a':
        cif1 = 10
    else:
        if a[index] == 'b':
            cif1 = 11
        else:
            if a[index] == 'c':
                cif1 = 12
            else:
                if a[index] == 'd':
                    cif1 = 13
                else:
                    if a[index] == 'e':
                        cif1 = 14
                    else:
                        if a[index] == 'f':
                            cif1 = 15
                        else:
                            cif1 = int(a[index])
    return cif1

def cifra_decimal_in_hexa(cifra):
    """
    Transforma cifra decimala in hexa
    :param cifra: int
    :return: cifra hexa
    """
    if cifra == 10:
        cif_hexa = "a"
    else:
        if cifra == 11:
            cif_hexa = "b"
        else:
            if cifra == 12:
                cif_hexa = "c"
            else:
                if cifra == 13:
                    cif_hexa = "d"
                else:
                    if cifra == 14:
                        cif_hexa = "e"
                    else:
                        if cifra == 15:
                            cif_hexa = "f"
                        else:
                            cif_hexa = cifra
    return cif_hexa


def adunare(a, b, baza_rezultat):
    """
    Aduna doua numere a si b in baza_rezultat
    :param a: string
    :param b: string
    :param baza_rezultat: string
    :return: suma numerelor a si b in baza_rezultat (string)
    """
    rezultat = ""
    if len(a) > len(b):
        zero = "0" * (len(a)-len(b))
        b = zero + b
    else:
        zero = "0" * (len(b)-len(a))
        a = zero + a

    index = len(a) - 1
    cat_anterior = 0
    while index >= 0:
        cif1 = cifra_hexa_in_decimal(a, index)
        cif2 = cifra_hexa_in_decimal(b, index)
        rest = (cat_anterior + cif1 + cif2) % int(baza_rezultat)
        cat = (cat_anterior + cif1 + cif2) // int(baza_rezultat)
        cif_hexa = cifra_decimal_in_hexa(rest)
        rezultat = str(cif_hexa) + rezultat
        cat_anterior = cat
        index = index - 1
    return rezultat


def scadere(a, b, baza_rezultat):
    """
    Scade doua numere in baza_rezultat
    :param a: string
    :param b: string
    :param baza_rezultat: string
    :return: diferenta numerelor in baza_rezultat
    """
    rezultat = ""
    if len(a) > len(b):
        zero = "0" * (len(a)-len(b))
        b = zero + b
    else:
        zero = "0" * (len(b)-len(a))
        a = zero + a
    index = len(a) - 1
    imprumut = 0
    while index >= 0:
        cif1 = cifra_hexa_in_decimal(a, index)
        cif2 = cifra_hexa_in_decimal(b, index)
        if cif1 - cif2 - imprumut < 0:
            cifra = int(baza_rezultat) + cif1 - cif2 - imprumut
            imprumut = 1
        else:
            cifra = cif1 - cif2 - imprumut
            imprumut = 0
        cifra_hexa = cifra_decimal_in_hexa(cifra)
        rezultat = str(cifra_hexa) + rezultat
        index = index - 1

    return rezultat


def inmultire(a, b, baza_rezultat):
    """
    Inmulteste numarul a cu ultima cifra lui b in baza_rezultat
    :param a: string
    :param b: string
    :param baza_rezultat: string
    :return: inmultirea dintre a si ultima cifra a lui b
    """
    ultima_cifra = cifra_hexa_in_decimal(b, -1)
    rezultat = ""
    cat_anterior = 0
    index = len(a) - 1
    while index >= 0:
        cif1 = cifra_hexa_in_decimal(a, index)
        rest = (cif1 * ultima_cifra + cat_anterior) % int(baza_rezultat)
        cat = (cif1 * ultima_cifra + cat_anterior) // int(baza_rezultat)
        cat_anterior = cat
        cifra = cifra_decimal_in_hexa(rest)
        rezultat = str(cifra) + rezultat
        index = index - 1
    return rezultat

def impartire(a, b, baza_rezultat):
    """
    Imparte numarul a cu ultima cifra lui b in baza_rezultat
    :param a: string
    :param b: string
    :param baza_rezultat: string
    :return: impartirea dintre a si ultima cifra a lui b
    """
    rezultat = ""
    ultima_cifra = cifra_hexa_in_decimal(b, -1)
    rest_anterior = 0
    index = 0
    while index < len(a):
        cif1 = cifra_hexa_in_decimal(a, index)
        cat = (rest_anterior * int(baza_rezultat) + cif1) // ultima_cifra
        rest = (rest_anterior * int(baza_rezultat) + cif1) % ultima_cifra
        cifra = cifra_decimal_in_hexa(cat)
        rezultat = rezultat + str(cifra)
        rest_anterior = rest
        index = index + 1

    return rezultat

def conversie_substitutie(numar, baza1, baza2):
    """
    Converteste un numar din baza1 in baza2 prin metoda substitutiei
    :param numar: string
    :param baza1: string
    :param baza2: string
    :return: numar convertit - string
    """
    putere = 1
    rez = ""
    index = len(numar) - 1
    while index >= 0:
        cifra = cifra_hexa_in_decimal(numar, index)
        nr = cifra * putere
        nr = conversie_baza_intermediara_zece(str(nr), 10, baza2)
        putere = putere * int(baza1)
        rez = adunare(rez, nr, baza2)
        index = index - 1
    return rez


def conversie_baza_intermediara_zece(numar, baza1, baza2):
    """
    Converteste un numar din baza1 in baza2 folosind baza intermediara 10
    :param numar: string
    :param baza1: string
    :param baza2: string
    :return: string - nr convertit
    """
    putere = 1
    rez = 0
    rezultat = ""
    index = len(numar) - 1
    while index >= 0:
        cifra = cifra_hexa_in_decimal(numar, index)
        nr = cifra * putere
        putere = putere * int(baza1)
        rez += nr
        index = index - 1

    cat = rez // int(baza2)
    rest = rez - cat * int(baza2)
    rest = cifra_decimal_in_hexa(rest)
    rezultat = str(rest) + rezultat
    while cat != 0:
        rez = cat
        cat = rez // int(baza2)
        rest = rez - cat * int(baza2)
        rest = cifra_decimal_in_hexa(rest)
        rezultat = str(rest) + rezultat

    return rezultat


def conversie_impartiri_repetate(numar, baza1, baza2):
    """
    Converteste un numar dintr-o baza in alta
    :param numar: string
    :param baza1: string
    :param baza2: string
    :return: numarul convertit (string)
    """
    numar_convertit = ""
    cat = impartire(numar, baza2, baza1)
    cat_int = int(conversie_baza_intermediara_zece(cat, baza1, "10"))
    nr = inmultire(cat, baza2, baza1)
    rest = int(conversie_baza_intermediara_zece(numar, baza1, 10)) - int(conversie_baza_intermediara_zece(nr, baza1, 10))
    rest = cifra_decimal_in_hexa(rest)
    numar_convertit = str(rest) + numar_convertit

    while cat_int != 0:
        numar = cat
        cat = impartire(numar, baza2, baza1)
        cat_int = int(conversie_baza_intermediara_zece(cat, baza1, "10"))
        nr = inmultire(cat, baza2, baza1)
        rest = int(conversie_baza_intermediara_zece(numar, baza1, 10)) - int(conversie_baza_intermediara_zece(nr, baza1, 10))
        rest = cifra_decimal_in_hexa(rest)
        numar_convertit = str(rest) + numar_convertit
    return numar_convertit


def conversii(numar, baza1, baza2):
    """
    Converteste un numar din baza1 in baza2
    :param numar: string
    :param baza1: string
    :param baza2: string
    :return: numar convertit - string
    """
    if int(baza1) == int(baza2):
        return numar
    if int(baza2) == 2 and int(baza1) in [4, 8, 16]:
        nr = conversie_rapida_in_doi(numar, baza1)
    else:
        if int(baza1) == 2 and int(baza2) in [4,8,16]:
            nr = conversie_rapida_din_doi(numar, baza2)
        else:
            if int(baza1) < int(baza2):
                nr = conversie_substitutie(numar, baza1, baza2)
            else:
                nr = conversie_impartiri_repetate(numar, baza1, baza2)
    return nr

def conversie_rapida_din_doi(numar, baza):
    """
    Converteste un numar din baza 2 in baza folosind conversii rapide
    Din 2 in 4 sau din 2 in 8 sau din 2 in 16
    :param numar: string
    :param baza: string
    :return: numar convertit - string
    11 110 001
    """
    index = len(numar) - 1
    rezultat = ""
    if baza == "4":
        deplasament = 2
    else:
        if baza == "8":
            deplasament = 3
        else:
            deplasament = 4
    while index >= 0:
        if index - deplasament + 1 < 0:
            portiune = numar[0: index+1]
        else:
            portiune = numar[index-deplasament+1 : index+1]
        cifra = conversie_baza_intermediara_zece(portiune, "2", "16")
        rezultat = cifra + rezultat
        index = index - deplasament
    return rezultat

def conversie_rapida_in_doi(numar, baza):
    """
    Converteste un numar din baza 4, 8, sau 16 in baza 2
    :param numar: string
    :param baza: string
    :return: numar convertit - string
    """
    rezultat = ""
    if baza == "16":
        dictionar_baza_16 = {
            "0": "0000",
            "1": "0001",
            "2": "0010",
            "3": "0011",
            "4": "0100",
            "5": "0101",
            "6": "0110",
            "7": "0111",
            "8": "1000",
            "9": "1001",
            "a": "1010",
            "b": "1011",
            "c": "1100",
            "d": "1101",
            "e": "1110",
            "f": "1111"
        }
        index = len(numar) - 1
        while index >= 0:
            rezultat = dictionar_baza_16[numar[index]] + rezultat
            index = index - 1
    else:
        if baza == "8":
            dictionar_baza_8 = {
                "0": "000",
                "1": "001",
                "2": "010",
                "3": "011",
                "4": "100",
                "5": "101",
                "6": "110",
                "7": "111"
            }
            index = len(numar) - 1
            while index >= 0:
                rezultat = dictionar_baza_8[numar[index]] + rezultat
                index = index - 1
        else:
            dictionar_baza_4 = {
                "0": "00",
                "1": "01",
                "2": "10",
                "3": "11"
            }
            index = len(numar) - 1
            while index >= 0:
                rezultat = dictionar_baza_4[numar[index]] + rezultat
                index = index - 1
    return rezultat


def validare(numar, baza):
    """
    Verifica daca un numar este scris in baza data
    :param numar: string
    :param baza: string
    :return: True/False
    """
    for index in range(0, len(numar)):
        cifra = cifra_hexa_in_decimal(numar, index)
        if cifra >= int(baza):
            return False
    return True


def main():
    """
    Se dau doua numere in format string si bazele in care acestea sunt scrise si baza in care se va afisa rezultatul.
    Se alege din meniu una dintre operatiile date (adunare, inmultire, impartire, scadere)
    Afiseaza rezultatul pe ecran.
    Rezultatul va avea aceeasi dimensiune ca si cel mai mare dintre numere
    """

    global baza_rezultat, a, b, baza_a, baza_b
    print("Proiect realizat de : Kentsch Karina Ioana - grupa 214")
    while True:

        print("\nMENIU")
        print("citire    - citire numere")
        print("adunare   - aduna doua numere in baza_rezultat")
        print("scadere   - scade doua numere in baza_rezultat")
        print("inmultire - inmulteste numarul a cu ultima cifra a lui b in baza_rezultat")
        print("impartire - imparte numarul a la ultima cifra a lui b in baza_rezultat")
        print("exit      - iesire din program")
        comanda = input("Dati numele comenzii: ")
        comanda = comanda.strip()

        if comanda == "citire":
            valid = False
            while not valid:
                a = input("Dati numarul a: ")
                baza_a = input("Dati baza in care este scris a: ")
                valid = validare(a, baza_a)
                if valid == False:
                    print("Numar invalid!")

            valid = False
            while not valid:
                b = input("Dati numarul b: ")
                baza_b = input("Dati baza in care este scris b: ")
                valid = validare(b, baza_b)
                if valid == False:
                    print("Numar invalid!")
            baza_rezultat = input("Dati baza in care se afiseaza reultatul: ")
            a = conversii(a, baza_a, baza_rezultat)
            b = conversii(b, baza_b, baza_rezultat)
            continue
        if comanda == "":
            continue
        else:
            if comanda == "exit":
                break
            else:
                if comanda == "adunare":
                    rez = adunare(a, b, baza_rezultat)
                    print("\n", a, "+", b,  "=", rez)
                    continue
                else:
                    if comanda == "scadere":
                        rez = scadere(a, b, baza_rezultat)
                        print("\n", a, "-", b,  "=", rez)
                        continue
                    else:
                        if comanda == "inmultire":
                            rez = inmultire(a, b, baza_rezultat)
                            print("\n", a, "*", b[-1],  "=", rez)
                            continue
                        else:
                            if comanda == "impartire":
                                rez = impartire(a, b, baza_rezultat)
                                print("\n", a, "/", b[-1],  "=", rez)
                                continue
                            else:
                                print("Comanda invalida!")


main()