#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("in.txt");

struct ListaAdiacenta{
    int varf;
    int vf_adiacente[100];
    int grad_varf;
};

struct Muchie{
    int varf1, varf2;
};

/***
 * Citeste numarul n si muchiile grafului din fisier si construieste matricea de adiacenta corespunzatoare
 * @param n numar natural nenul
 * @param adiacenta matricea de adiacenta
 */
void citire_formare_matrice_adiacenta(int &n, int adiacenta[][100]){

    fin >> n;
    int varf1, varf2;
    while (fin >> varf1 >> varf2){
        adiacenta[varf1][varf2] = 1;
        adiacenta[varf2][varf1] = 1;
    }
}

/***
 * Afiseaza o matrice cu n linii si m coloane
 * @param n int - numar de linii
 * @param m int - numar de coloane
 * @param matrice matrice cu elemente int
 */
void afisare_matrice(int n, int m, int matrice[][100]){
    for (int i = 1; i <= n; i++){
        for (int j = 1; j <= m; j++)
            cout << matrice[i][j] << " ";
        cout << endl;
    }
}


/***
 * Transforma matricea de adiacenta in lista de adiacenta
 * @param n numarul de varfuri (n*n este dimensiunea matricei de adiacenta)
 * @param adiacenta matricea de adiacenta
 * @param vector ListaAdiacenta - lista de adiacenta
 */
void matrice_adiacenta_in_lista_adiacenta(int n, int adiacenta[][100], ListaAdiacenta vector[]){
    for (int i = 1; i <= n; i++){
        vector[i].varf = i;
        vector[i].grad_varf = 0;
        int indice = 0;
        for (int j = 1; j <= n; j++)
            if (adiacenta[i][j] == 1){
                vector[i].grad_varf++;
                vector[i].vf_adiacente[indice++] = j;
            }
    }

}


/***
 * Afiseaza lista de adiacenta
 * @param vector ListaAdiacenta
 * @param n numar varfuri (nr natural nenul)
 */
void afisare_lista_adiacenta(ListaAdiacenta vector[], int n){
    for (int i = 1; i <= n; i++){
        cout << i << ": ";
        int lg_lista_adiacente = vector[i].grad_varf;
        for (int j = 0; j < lg_lista_adiacente; j++)
            cout << vector[i].vf_adiacente[j] << " ";
        cout << endl;
    }
}



/***
 * Transforma lista de adiacenta in matrice de incidenta(pe linii se afla varfurile, pe coloane muchiile)
 * @param incidenta matricea de incidenta
 * @param vector ListaAdiacenta - lista de adiacenta
 * @param n numar varfuri
 * @param nrColoaneIncidenta numarul de coloane pe care il va avea matricea de incidenta in functie de cate muchii are graful
 */
void lista_adiacenta_in_matrice_incidenta(int incidenta[][100], ListaAdiacenta vector[], int n, int &nrColoaneIncidenta){
    nrColoaneIncidenta = 0;
    for (int i = 1; i <= n; i++){
        int lg_vf_adiacente = vector[i].grad_varf;
        for (int j = 0; j < lg_vf_adiacente; j++)
            if (i < vector[i].vf_adiacente[j]){
            nrColoaneIncidenta++;
            incidenta[i][nrColoaneIncidenta] = 1;
            incidenta[vector[i].vf_adiacente[j]][nrColoaneIncidenta] = 1;
        }
    }
}

/***
 * Transforma matricea de incidenta in lista de adiacenta
 * @param incidenta matrice de incidenta
 * @param vector_nou ListaAdiacenta - lista de adiacenta
 * @param n - numar varfuri (numar natural nenul)
 * @param nrColoaneIncidenta numarul de coloane pe care o are matricea de incidenta
 */
void matrice_incidenta_in_lista_adiacenta(int incidenta[][100], ListaAdiacenta vector_nou[], int n, int nrColoaneIncidenta){
    for (int i = 1; i <= n; i++)
        vector_nou[i].grad_varf = 0; //initial toate nodurile vor avea grad 0

    //iteram matricea de incidenta pe coloane
    for (int j = 1; j <= nrColoaneIncidenta; j++){
        int gasit = 0, capat1, capat2;
        for (int i = 1; i <= n; i++)
            if (incidenta[i][j] == 1) //daca nodul i este incident cu muchia j
                if (gasit == 0){ //daca nu am mai gasit unu nod incident cu muchia j, il retinem in capat1, altfel in capat2
                    gasit = 1;
                    capat1 = i;
                }
                else
                    capat2 = i;

        //pentru primul capat al muchiei j, capat1, il adaugam in lista de adiacenta si ii adaugam vecinul sau capat2
        vector_nou[capat1].varf = capat1;
        vector_nou[capat1].grad_varf++;
        int grad1 = vector_nou[capat1].grad_varf - 1;
        vector_nou[capat1].vf_adiacente[grad1] = capat2;

        //pentru al doilea capat al muchiei j, capat2, il adaugam in lista de adiacenta si ii adaugam vecinul sau capat1
        vector_nou[capat2].varf = capat2;
        vector_nou[capat2].grad_varf++;
        int grad2 = vector_nou[capat2].grad_varf - 1;
        vector_nou[capat2].vf_adiacente[grad2] = capat1;
    }

}


/***
 * Transforma lista de adiacenta in matrice de adiacenta
 * @param vector_nou ListaAdiacenta - lista de adiacenta
 * @param adiacenta_noua matricea de adiacenta
 * @param n numar varfuri
 */
void lista_adiacenta_in_matrice_adiacenta(ListaAdiacenta vector_nou[], int adiacenta_noua[][100], int n){
    //iteram lista de adiacenta
    for (int i = 1; i <= n; i++){
        int lg_vf_adiacente = vector_nou[i].grad_varf;
        for (int j = 0; j < lg_vf_adiacente; j++){ //iteram varfurile adiacenta cu varful i
            int varf_coloana = vector_nou[i].vf_adiacente[j];
            adiacenta_noua[i][varf_coloana] = 1; //punem 1 in matricea de adiacenta pe linia unde e vf i si coloana coresp. vecinului sau
        }
    }
}


/***
 * Tranforma matricea de adiacenta in lista de muchii
 * @param adiacenta_noua matricea de adiacenta
 * @param lista_muchii Muchie -  lista muchiilor
 * @param n numar varfuri
 * @param nrMuchii - numarul de muchii pe care il are graful
*/
void matrice_adiacenta_in_lista(int adiacenta_noua[][100], Muchie lista_muchii[], int n, int &nrMuchii){
    nrMuchii = 1;
    for (int i = 1; i <= n; i++)
        for (int j = i+1; j <= n; j++)
            if (adiacenta_noua[i][j] == 1){ //daca exista muchie intre i si j se retine in lista
                lista_muchii[nrMuchii].varf1 = i;
                lista_muchii[nrMuchii].varf2 = j;
                nrMuchii++;
            }
    nrMuchii--;
}

/***
 * Afiseaza lista de muchii
 * @param lista_muchii Muchie - lista muchiilor
 * @param nrMuchii numarul de muchii pe care il are graful
 */
void afisare_lista_muchii(Muchie lista_muchii[], int nrMuchii){

    for (int i = 1; i <= nrMuchii; i++)
        cout << "(" << lista_muchii[i].varf1 << "," << lista_muchii[i].varf2 << ")" << endl;
}


/***
 * Afiseaza nodurile izolate(gradul nodului este zero)
 * @param adiacenta matricea de adiacenta
 * @param n numar varfuri
 */
void noduri_izolate(int adiacenta[][100], int n){

    int gasit = 0;
    for (int i = 1; i <= n; i++) {
        int grad = 0;
        for (int j = 1; j <= n; j++)
            if (adiacenta[i][j] == 1)
                grad++;
        if (grad == 0){
            cout << i << " ";
            gasit = 1;
        }
    }
    if (gasit == 0)
        cout << "Nu exista noduri izolate";
}


/***
 * Verifica daca graful este regulat (toate varfurile au acelasi grad)
 * @param adiacenta matricea de adiacenta
 * @param n nr varfuri
 * @return true - daca graful este regulat
 *         false - daca graful nu este regulat
 */
bool graf_regular(int adiacenta[][100], int n){

    ListaAdiacenta vector[100];
    matrice_adiacenta_in_lista_adiacenta(n, adiacenta, vector);
    int grad_general = vector[1].grad_varf;
    for (int i = 2; i <= n; i++)
        if (vector[i].grad_varf != grad_general) return false;
    return true;
}

/***
 * Parcurgere in adancime a grafului si completarea vectorului viz care retine pt fiecare nod daca a fost vizitat
 * @param x int - un varf al grafului
 * @param viz vector de vizitat - retine daca un nod a fost vizitat
 * @param adiacenta matricea de adiacenta
 * @param n numar de varfuri
 */
void DFS(int x, int viz[], int adiacenta[][100], int n){

    viz[x] = 1;
    for (int i = 1; i <= n; i++)
        if (adiacenta[x][i] == 1 && viz[i] == 0)
            DFS(i, viz, adiacenta, n);
}


/***
 * Verifica daca un graf este conex
 * @param viz vector de vizitat
 * @param adiacenta matricea de adiacenta
 * @param n numar varfuri
 * @return true - daca graful este conex
 *         false - daca graful nu este conex
 */
bool conex(int viz[], int adiacenta[][100], int n){

    DFS(1, viz, adiacenta, n);
    for (int i = 1; i <= n; i++)
        if (viz[i] == 0) return false;
    return true;
}


/***
 * Returneaza lungimea lantului de la varf1 la varf2
 * @param adiacenta matrice de adiacenta
 * @param varf1 int
 * @param varf2 int
 * @param n nr varfuri
 * @return lungimea lantului sau un numar mare daca nu exista lant de la varf1 la varf2
 */
int lungime_drum(int adiacenta[][100], int varf1, int varf2, int n){

    if (adiacenta[varf1][varf2] == 1)
        return 1;
    if (varf1 == varf2)
        return 0;
    for (int i = varf1 + 1; i <= n; i++)
        if (adiacenta[varf1][i] == 1)
            return lungime_drum(adiacenta, i, varf2, n) + 1;
    return INT32_MAX - n;
}


/***
 * Formeaza matricea drumurilor
 * @param adiacenta matricea de adiacenta
 * @param drumuri matricea drumurilor
 * @param n numar varfuri
 */
void matrice_drumuri(int adiacenta[][100], int drumuri[][100], int n){

    for (int i = 1; i <= n; i++)
        for (int j = i; j <= n; j++){
            int lungime = lungime_drum(adiacenta, i, j, n);
            drumuri[i][j] = lungime;
            drumuri[j][i] = lungime;
        }
}

int main() {

    int n, adiacenta[100][100] = {0}, incidenta[100][100] = {0}, nrColoaneIncidenta, nrMuchii, regular, conexitate;
    int adiacenta_noua[100][100] = {0}, drumuri[100][100] = {INT32_MAX};
    ListaAdiacenta vector[100];
    ListaAdiacenta vector_nou[100];
    Muchie lista_muchii[100];
    int viz[100] = {0};

    int comanda;
    while(1){
        cout << endl << "MENIU" << endl;
        cout << "1 - problema 1" << endl;
        cout << "2 - problema 2" << endl;
        cout << "3 - exit" << endl;
        cout << "Introduceti comanda: ";
        cin >> comanda;
        cout << endl;

        switch (comanda) {
            case 1:
                citire_formare_matrice_adiacenta(n, adiacenta);
                cout << "Matricea de adiacenta - obtinuta din citire" << endl;
                afisare_matrice(n, n, adiacenta);

                cout << endl << "Lista de adiacenta - obtinuta din matricea de adiacenta" << endl;
                matrice_adiacenta_in_lista_adiacenta(n, adiacenta, vector);
                afisare_lista_adiacenta(vector, n);

                cout << endl << "Matricea de incidenta - obtinuta din lista de adiacenta" << endl;
                lista_adiacenta_in_matrice_incidenta(incidenta, vector, n, nrColoaneIncidenta);
                afisare_matrice(n, nrColoaneIncidenta, incidenta);

                cout << endl << "Lista de adiacenta - obtinuta din matricea de incidenta" << endl;
                matrice_incidenta_in_lista_adiacenta(incidenta, vector_nou, n, nrColoaneIncidenta);
                afisare_lista_adiacenta(vector_nou, n);

                cout << endl << "Matricea de adiacenta - obtinuta din lista de incidenta" << endl;
                lista_adiacenta_in_matrice_adiacenta(vector_nou, adiacenta_noua, n);
                afisare_matrice(n,n,adiacenta);

                cout << endl << "Lista muchiilor" << endl;
                matrice_adiacenta_in_lista(adiacenta_noua, lista_muchii, n, nrMuchii);
                afisare_lista_muchii(lista_muchii, nrMuchii);
                break;

            case 2:
                citire_formare_matrice_adiacenta(n, adiacenta);
                cout << "a) Nodurile izolate din graf: ";
                noduri_izolate(adiacenta, n);
                cout << endl;

                regular = graf_regular(adiacenta, n);
                if (regular == false)
                    cout << "b) Graful nu este regular" << endl;
                else
                    cout << "b) Graful este regular" << endl;

                cout << "c) Matricea drumurilor"<< endl;
                matrice_drumuri(adiacenta, drumuri, n);
                afisare_matrice(n, n, drumuri);

                conexitate = conex(viz, adiacenta, n);
                if (conexitate == false)
                    cout << "d) Graful nu este conex" << endl;
                else
                    cout << "d) Graful este conex" << endl;
                break;
            case 3:
                return 0;
            default:
                cout << "Comanda invalida" << endl;
        }
    }
    fin.close();
    return 0;
}
