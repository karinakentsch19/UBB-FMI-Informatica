def compara(a, b):
    if a.medie_note_dto() > b.medie_note_dto():
        return True
    if a.medie_note_dto() == b.medie_note_dto() and a.get_student_dto().get_nume() < b.get_student_dto().get_nume():
        return True
    return False


def interclasare(left, right, cmp):
    """
    Interclaseaza doua subsiruri
    :param reverse: true/false
    :param key: criteriu sortare
    :param left: subsir
    :param right: subsir
    :return: sir interclasat
    """
    arr = []
    i = 0
    j = 0

    while i < len(left) and j < len(right):
        if cmp(left[i],right[j]) == True:
            arr.append(left[i])
            i = i + 1
        else:
            arr.append(right[j])
            j = j + 1

    while i < len(left):
        arr.append(left[i])
        i = i + 1

    while j < len(right):
        arr.append(right[j])
        j = j + 1

    return arr


def MergeSort(lista, cmp = compara):
    """
    Sorteaza lista
    :param reverse: true/false
    :param key: criteriu sortare
    :param lista: lista
    :return: lista sortata
    """
    lungime = len(lista)
    if lungime <= 1:
        return lista
    m = lungime//2
    left = MergeSort(lista[0:m], cmp)
    right = MergeSort(lista[m:lungime], cmp)
    return interclasare(left,right, cmp)