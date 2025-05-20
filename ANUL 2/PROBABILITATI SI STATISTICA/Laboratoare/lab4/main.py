import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import hypergeom, geom, bernoulli


def simulare_deplasament(num_steps, p):
    """
       Simulează deplasarea unui punct pe axa

       :param num_steps: nr de pași ai simulării.
       :param p: probabilitatea de a merge la dreapta într-un pas (1 - p pentru a merge la stânga).
       :return: lista pozițiilor la fiecare pas.
       """
    pozitii = [0]  # inițializăm poziția curentă la 0

    for _ in range(num_steps):
        # generăm o probabilitate și decidem direcția deplasării
        x = bernoulli.rvs(p)
        directie = 2 * x - 1
        # actualizăm poziția curentă
        pozitie_veche = pozitii[-1]
        pozitie_curenta = pozitie_veche + directie
        pozitii.append(pozitie_curenta)

    return pozitii


def histograma(num_steps, p):
    """
    Afișează histograma pozițiilor finale după simularea unui număr de pași dat.

    :param num_steps: numarul de pași ai simulării.
    :param p: probabilitatea de a merge la dreapta într-un pas (1 - p pentru a merge la stânga).
    """
    numar_simulari = 1000
    pozitii = []

    for _ in range(numar_simulari):
        positions = simulare_deplasament(num_steps, p)
        pozitii.append(positions[-1])

    # Afișăm histograma
    plt.hist(pozitii, bins='auto', alpha=0.7, rwidth=0.85)
    plt.grid(axis='y', alpha=0.75)
    plt.xlabel('Poziția finală')
    plt.ylabel('Frecvență')
    plt.title('Histograma Pozițiilor Finale după 1000 de simulări')
    plt.show()

def simulare_deplasament_pe_cerc(num_steps, p, n):
    """
    Simulează deplasarea unui punct material pe un cerc cu n noduri.

    :param num_steps: nr de pași ai simulării.
    :param p: probabilitatea de a merge la dreapta într-un pas (1 - p pentru a merge la stânga).
    :param n: numarul de noduri pe cerc.
    :return: lista pozițiilor pe cerc la fiecare pas.
    """
    pozitii = [0]  # inițializăm poziția curentă la 0

    for _ in range(num_steps):
        # generăm o probabilitate și decidem direcția deplasării
        directie = np.random.choice([-1, 1], p=[1 - p, p])
        # actualizăm poziția curentă pe cerc
        pozitie_curenta = (pozitii[-1] + directie) % n
        pozitii.append(pozitie_curenta)

    return pozitii

def histograma_cercuri():
    """
    Afișează histograma pozițiilor finale pe cerc după simularea unui număr de pași dat.

    :param num_steps: nr de pași ai simulării.
    :param p: probabilitatea de a merge la dreapta într-un pas (1 - p pentru a merge la stânga).
    :param n: nr de noduri pe cerc.
    """
    nr_pasi = 100
    p = 0.5
    n = 10
    nr_simulari = 1000

    pozitii_finale_cerc = []

    for _ in range(nr_simulari):
        poz_cerc = simulare_deplasament_pe_cerc(nr_pasi, p, n)
        pozitii_finale_cerc.append(poz_cerc[-1])

    # Afișăm histograma
    plt.hist(pozitii_finale_cerc, bins=np.arange(-0.5, n, 1), alpha=0.7, rwidth=0.85)
    plt.xticks(np.arange(0, n, 1))
    plt.grid(axis='y', alpha=0.75)
    plt.xlabel('Poziția finală pe cerc')
    plt.ylabel('Frecvență')
    plt.title('Histograma Pozițiilor Finale pe Cerc după 1000 de simulări')
    plt.show()

def simuleaza_pana_la_castig(total_numere, total_numere_castigatoare, numere_pe_bilet):
    """
        Simulează extragerile până la primul bilet câștigător

        :param total_numere: totalul de nr disponibile pentru alegere.
        :param total_numere_castigatoare: nr de numere câștigătoare pe bilet.
        :param numere_pe_bilet: nr total de numere pe un bilet.
        :return: nr de bilete necâștigătoare până la primul bilet câștigător.
        """
    bilete_necastigatoare = 0

    while True:
        # Extragem un bilet aleatoriu
        bilet_ales = hypergeom.rvs(total_numere, total_numere_castigatoare, numere_pe_bilet)

        # Verificăm dacă biletul ales are cel puțin 3 numere câștigătoare
        if bilet_ales >= 3:
            break
        else:
            bilete_necastigatoare += 1

    return bilete_necastigatoare


def problema2():
    # Exemplu de utilizare pentru simularea până la primul bilet câștigător
    total_numere = 49
    total_numere_castigatoare = 6
    numere_pe_bilet = 6

    rezultate_simulare = [simuleaza_pana_la_castig(total_numere, total_numere_castigatoare, numere_pe_bilet) for _ in
                          range(1000)]

    print("2a) Numărul de bilete necâștigătoare până la primul bilet câștigător:", rezultate_simulare)

    # Exemplu de utilizare pentru estimarea probabilității
    num_simulare = 1000
    num_bilete_consecutive_necastigatoare = 10

    rezultate_simulate = [simuleaza_pana_la_castig(total_numere, total_numere_castigatoare, numere_pe_bilet) for _ in
                          range(num_simulare)]

    # Estimare probabilitate simulată
    prob_simulata = sum(i >= num_bilete_consecutive_necastigatoare for i in rezultate_simulate) / num_simulare
    p = sum(hypergeom.pmf(k,49,6,6) for k in range(3,7))
    # Estimare probabilitate teoretica
    prob_teoretica = 1 - geom.cdf(9,p)

    print(f"2b) Probabilitate simulată: {prob_simulata:.4f}")
    print(f"2b) Probabilitate teoretică: {prob_teoretica:.4f}")


def main():
    # EXERCITIUL 1a
    num_steps = 20
    p = 0.7
    positions = simulare_deplasament(num_steps, p)

    print("1a) Pozițiile la fiecare pas:", positions)

    # EXERCITIUL 1b
    histograma(num_steps, p)

    # EXERCITIUL 1c
    histograma_cercuri()


    #Exercitiul 2
    problema2()


main()