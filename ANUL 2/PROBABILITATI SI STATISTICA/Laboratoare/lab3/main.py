import random
from random import choices, sample
from math import comb, perm
from random import randrange
from matplotlib.pyplot import bar, hist, grid, show, legend, xlabel, ylabel, title
from numpy import arange
from scipy.stats import binom

def Problema1(nrSimulari):
    urna = ("rosu", "albastru", "verde")

    nrBileRosii = 5
    nrBileAlbastre = 3
    nrBileVerzi = 2

    countA = 0
    countB = 0

    for _ in range(nrSimulari):
        bileExtrase = random.choices(urna, weights=[nrBileRosii, nrBileAlbastre, nrBileVerzi], k=3)
        if bileExtrase[0] == "rosu" or bileExtrase[1] == "rosu" or bileExtrase[2] == "rosu":
            countA += 1
            if len(set(bileExtrase)) == 1:
                countB += 1

    pAnB = comb(5, 3) / comb(10, 3)
    pA = 1 - comb(5, 3) / comb(10, 3)

    prob = pAnB / pA

    print("i) Valoarea prin simulari repetate: ", countB / nrSimulari)
    print("ii) Valoarea teoretica: ", prob)


def Problema2():
    data = [randrange(1, 7) for _ in range(500)]
    bin_edges = [k + 0.5 for k in range(6)]
    hist(data, bin_edges, density=True, rwidth=0.9, color='green', edgecolor='black',
         alpha=0.5, label='frecvente relative')
    distribution = dict([(i, 1 / 6) for i in range(1, 7)])
    bar(list(distribution.keys()), list(distribution.values()), width=0.85, color='red', edgecolor='black',
        alpha=0.6, label='probabilitati')
    legend(loc='lower left')
    grid()
    show()


def Problema3():
    n = 5  # Numărul de extrageri
    p = 6 / 10  # Probabilitatea de a extrage o bilă cu cifra 1

    # Generăm 1000 de valori pentru X
    sample_size = 1000
    X_values = binom.rvs(n, p, size=sample_size)
    print("a) ", X_values)

    hist(X_values, bins=arange(0, 6, 1), density=True, alpha=0.5, label='Frecvențe relative')

    # Calculăm valorile teoretice folosind funcția de probabilitate a masei
    X_theoretical = [binom.pmf(k, n, p) for k in range(6)]  # calc prob ca bilele numerotate cu 1 sa apara de k ori

    # Afișăm barele corespunzătoare valorilor teoretice
    bar(arange(0, 6, 1), X_theoretical, color = 'red', alpha=0.5, label='Valorile teoretice')

    xlabel('X')
    ylabel('Probabilitate')
    legend()
    show()

    # Estimăm probabilitatea P(2 < X ≤ 5) folosind funcția CDF
    probability_estimated = binom.cdf(5, n, p) - binom.cdf(1, n, p)

    # Calculăm valoarea teoretică
    probability_theoretical = sum(X_theoretical[2:6])

    print("c) Probabilitate estimată:", probability_estimated)
    print("c) Probabilitate teoretică:", probability_theoretical)


def arunca_zaruri_pb4():
    return sum(random.randint(1, 6) for _ in range(3))


def simulare_probabilitati_pb4(numar_aruncari, numar_de_obtinut):
    numar_de_obtinut = int(numar_de_obtinut)
    numar_castiguri = 0

    for _ in range(numar_aruncari):
        suma_obținuta = arunca_zaruri_pb4()
        if suma_obținuta == numar_de_obtinut:
            numar_castiguri += 1

    probabilitate = numar_castiguri / numar_aruncari
    return probabilitate


def probabilitate_teoretica_pb4(numar_de_obtinut):
    numar_de_obtinut = int(numar_de_obtinut)
    if numar_de_obtinut < 3 or numar_de_obtinut > 18:
        return 0
    numar_combinatii = 0
    for zar1 in range(1, 7):
        for zar2 in range(1, 7):
            for zar3 in range(1, 7):
                if zar1 + zar2 + zar3 == numar_de_obtinut:
                    numar_combinatii += 1
    probabilitate = numar_combinatii / 216
    return probabilitate


def Problema4():
    numar_aruncari = 100000
    rezultate_probabilitati = []

    for numar_de_obtinut in range(3, 19):
        probabilitate = simulare_probabilitati_pb4(numar_aruncari, numar_de_obtinut)
        rezultate_probabilitati.append(probabilitate)

    # Afisam histograma probabilitatilor
    bar(range(3, 19), rezultate_probabilitati)
    xlabel('Suma de obținut')
    ylabel('Probabilitate')
    title('Histograma probabilităților')
    show()

    rezultate_probabilitati_teoretice = [probabilitate_teoretica_pb4(numar) for numar in range(3, 19)]

    # Afisam histograma probabilitatilor teoretice
    bar(range(3, 19), rezultate_probabilitati_teoretice)
    xlabel('Suma de obținut')
    ylabel('Probabilitate')
    title('Histograma probabilităților teoretice')
    show()


def main():
    # Problema1(100000)
    # Problema2()
    # Problema3()
    Problema4()

main()
