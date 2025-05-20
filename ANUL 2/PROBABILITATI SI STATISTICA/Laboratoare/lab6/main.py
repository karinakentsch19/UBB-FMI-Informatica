from matplotlib import pyplot as plt
from numpy.random import normal
from scipy.stats import norm
from numpy import mean, std, linspace
from matplotlib.pyplot import show, hist, grid, legend, xticks, plot
from scipy.stats import expon, uniform
import numpy as np
from scipy.stats import uniform
from numpy import exp, mean
from scipy.integrate import quad


def problema1():
    intervale = (130, 135, 140, 145, 150, 155, 160, 165, 170, 175, 180, 185, 190, 195, 200, 205)

    FrecventaIntervale = {}
    for i in intervale:
        FrecventaIntervale[i] = 0

    valori_160_170 = []

    DistributieNormala = norm.rvs(loc=165, scale=10, size=1000)

    for val in DistributieNormala:
        for inf in intervale:
            sup = inf + 5
            if inf <= val < sup:
                FrecventaIntervale[inf] += 1
        if 160 <= val <= 170:
            valori_160_170.append(val)

    bin_edges = [k - 0.5 for k in intervale[0:len(intervale) - 1]]
    data = [FrecventaIntervale[key] for key in FrecventaIntervale.keys()]
    hist(DistributieNormala, bins=bin_edges, density=True, rwidth=0.5, label='Frecventa Intervale')

    x = linspace(130, 200)
    plot(x, norm.pdf(x, 165, 10), color='blue', linestyle='-', linewidth=2)
    show()

    valoarea_medie = mean(valori_160_170)
    deviatia_standard = DistributieNormala.std()

    print("Valoarea medie:", valoarea_medie)
    print("Deviatia standard:", deviatia_standard)
    probabilitate = norm.cdf(170, 165, 10) - norm.cdf(160, 165, 10)
    print("Probabilitate: ", probabilitate)


def problema2():
    # Probabilitățile asociate cu imprimantele I1 și I2
    prob_I1 = 0.4
    prob_I2 = 0.6

    # Parametrii distribuțiilor
    lambda_I1 = 1 / 5
    lambda_I2 = 1 / (6 - 4)  # Pentru distribuția uniformă Unif[4, 6]

    # a) Estimarea valorii medii și deviației standard
    mean_poster = prob_I1 * expon(scale=1 / lambda_I1).mean() + prob_I2 * uniform(loc=4, scale=2).mean()
    std_poster = np.sqrt(prob_I1 * expon(scale=1 / lambda_I1).var() + prob_I2 * uniform(loc=4, scale=2).var())

    # b) Estimarea probabilității ca timpul de printare să fie mai mic de 5 secunde
    prob_less_than_5_poster = prob_I1 * expon.cdf(5, scale=1 / lambda_I1) + prob_I2 * uniform.cdf(5, loc=4, scale=2)

    # c) Probabilitatea teoretică pentru b)
    prob_theoretical_poster = prob_I1 * expon.cdf(5, scale=1 / lambda_I1) + prob_I2 * uniform.cdf(5, loc=4, scale=2)

    # Afișarea rezultatelor
    print("a) Estimarea valorii medii și deviației standard:")
    print(f"Poster: Media = {mean_poster}, Deviație standard = {std_poster}\n")

    print("b) Estimarea probabilității ca timpul de printare să fie mai mic de 5 secunde:")
    print(f"Poster: {prob_less_than_5_poster}\n")

    print("c) Probabilitatea teoretică pentru b):")
    print(f"Poster: {prob_theoretical_poster}")

#
# def fct(x):
#     val = - x * x
#     return exp(val)


def problema3():
    n = 60000
    U = uniform.rvs(-1, 4, n)
    g = lambda x: exp(-x * x)
    X = []


    valoare_aproximata = quad(g, -1, 3)[0]
    print("Valoarea estimata:", valoare_aproximata)
    for u in U:
        X.append(g(u))
    valoare_aproximata = 4 * 1/n * sum(X)
    print("Valoarea aproximata: ", valoare_aproximata)

def main():
    print("Problema 1")
    problema1()
    print("\nProblema 2")
    problema2()
    print("\nProblema 3")
    problema3()


main()
