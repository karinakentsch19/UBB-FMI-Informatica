import math
from random import randint, seed

from fontTools.qu2cu.qu2cu import Point
from matplotlib import pyplot, patches
from random import random
from math import dist

from matplotlib.patches import Rectangle, Circle


def generateDays(NumberOfDays):
    days = []
    for _ in range(NumberOfDays):
        day = randint(1, 365)
        days.append(day)
    return days


def Problema1(NumberOfSimulations):
    cazFavorabil = 0
    for _ in range(NumberOfSimulations):
        days = generateDays(23)
        repeat = False
        for day in days:
            if days.count(day) > 1:
                repeat = True
        if repeat:
            cazFavorabil += 1
    return cazFavorabil / NumberOfSimulations

def probabilitate():
    caz_fav = 1
    caz_pos = 1
    for nr in range(365, 343, -1):
        caz_fav *= nr
        caz_pos *= 365
    return caz_fav / caz_pos


print("Prob:", probabilitate())
print("Probabilitatea ca intr-un grup de 23 persoane cel puµin doua sa aiba aceeasi zi de nastere:", Problema1(80000))
print()



def unghi(p1, p2, p3):
    """
    Returneza true daca unghiul dintre 3 puncte e 0 <= u <= 90
    folosind formula cos(unghi) = (a1*a2 + b1*b2)/sqrt(..) ; cos > 0 => unghi 0-90
    :param p1: (x1,y1)
    :param p2: (x2,y2)
    :param p3: (x3,y3)
    :return: True/False
    """
    a1 = p1[0] - p2[0]
    b1 = p1[1] - p2[1]
    a2 = p3[0] - p2[0]
    b2 = p3[1] - p2[1]
    return (a1 * a2 + b1 * b2) >= 0

def Problema2(NumberOfPoints):
    """
    Consideram patratul de colturi (0,0) , (2,2)
    :param NumberOfPoints: Numarul de punctegenerate
    """
    centruCerc = (1, 1)

    countForA = 0
    countForB = 0
    countForC = 0

    # Generam patratul si cercul concentric
    fig, ax = pyplot.subplots()
    ax.plot(0, 0)
    ax.add_patch(Rectangle((0, 0), 2, 2))
    ax.add_patch(Circle((1, 1), 1, edgecolor='red', facecolor='none'))


    for _ in range(NumberOfPoints):

        point_x, point_y = random() * 2, random() * 2  # Generam coordonatele punctului
        point = (point_x, point_y)

        d = dist(centruCerc, point)
        if d <= 1:  # Daca puncutl e inclus in cerc
            countForA = countForA + 1
        if d < dist((0, 0), point) and d < dist((2, 0), point) and d < dist((0, 2), point) and d < dist((2, 2),
                                                                                                        point):  # Daca e mai apropiat de centru decat de varfuri
            countForB = countForB + 1

        contorAscutit, contorObtuz = 0, 0
        if unghi((0, 0), point, (2, 0)):
            contorAscutit += 1
        else:
            contorObtuz += 1
        if unghi((0, 0), point, (0, 2)):
            contorAscutit += 1
        else:
            contorObtuz += 1
        if unghi((2, 2), point, (2, 0)):
            contorAscutit += 1
        else:
            contorObtuz += 1
        if unghi((2, 2), point, (0, 2)):
            contorAscutit += 1
        else:
            contorObtuz += 1
        if contorAscutit == 2 and contorObtuz == 2:
            countForC += 1

        pyplot.plot(point_x, point_y, '.')

    pyplot.show()  # Afisare Grafic

    print("i)   Frecventa relativa a punctelor care sunt in interiorul cercului tangent laturilor  patratului:",
          countForA / NumberOfPoints)
    print(
        "ii)  Frecventa relativa a punctelor care sunt sunt mai apropiate de centrul patratului decat de varfurile patratului:",
        countForB / NumberOfPoints)
    print(
        "iii) Frecventa relativa a punctelor care formeaza cu varfurile patratului doua triunghiuri ascutitunghice si doua triunghiuri obtuzunghice:",
        countForC / NumberOfPoints)
    print()

    r = 1
    AriaCerc = math.pi * r * r
    l = 2
    AriaPatrat = l * l
    AriaPatratel = AriaPatrat / 2
    AriaSemidisc = AriaCerc / 2
    AriaPetala = (4 * AriaSemidisc - AriaPatrat) / 4
    print("Probabilitatea Geometrica pt A:", AriaCerc / AriaPatrat)
    print("Probabilitatea Geometrica pt B:", AriaPatratel / AriaPatrat)
    print("Probabilitatea Geometrica pt C:", 4 * AriaPetala / AriaPatrat)
    print()

Problema2(500)
Problema2(1000)
Problema2(2000)