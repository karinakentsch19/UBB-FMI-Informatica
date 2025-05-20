#include <iostream>
#include <queue>
#include <vector>
#include <fstream>
#include <cstring>
#include <map>
#include <string>
#include <unordered_map>

using std::map;
using std::unordered_map;
using std::vector;
using std::priority_queue;
using std::cout;
using std::string;

struct Nod{
    char caracter;
    int frecventa;
    Nod *stanga, *dreapta;
};

struct compare{
    bool operator()(Nod* stanga, Nod* dreapta){
        return stanga->frecventa > dreapta->frecventa || (stanga->frecventa == dreapta->frecventa && stanga->caracter > dreapta->caracter);
    }
};

void frecventa_litere(char text[], vector<Nod*>& noduri){
    map<char, int> frecv;
    for (int i = 0; i < strlen(text); i++)
        frecv[text[i]]++;
    auto iterator = frecv.begin();
    while (iterator != frecv.end()){
        Nod* nod = new Nod;
        nod->caracter = iterator->first;
        nod->frecventa = iterator->second;
        nod->stanga = nullptr;
        nod->dreapta = nullptr;
        noduri.push_back(nod);
        iterator++;
    }
}

void decoding(Nod* radacina, string& str, string codare_huffman){
    int i = 0;
    while(i < codare_huffman.size()){
        Nod* prim = radacina;
        while(prim->stanga != nullptr && prim->dreapta != nullptr){
            if (codare_huffman[i] == '0')
                prim = prim->stanga;
            else
                prim = prim->dreapta;
            i++;
        }
        str += prim->caracter;
    }
}

void Decodare_Huffman(vector<Nod*> noduri, string& text, string& codare_huffman){
    priority_queue<Nod*,vector<Nod*>, compare> coada_cu_prioritati;

    for (int i = 0; i < noduri.size(); i++)
        coada_cu_prioritati.push(noduri[i]);

    while(coada_cu_prioritati.size() != 1){
        Nod* varf = new Nod;
        varf->stanga = coada_cu_prioritati.top();
        coada_cu_prioritati.pop();
        varf->dreapta = coada_cu_prioritati.top();
        coada_cu_prioritati.pop();
        varf->frecventa = varf->stanga->frecventa + varf->dreapta->frecventa;
        varf->caracter = std::min( varf->stanga->caracter, varf->dreapta->caracter);
        coada_cu_prioritati.push(varf);
    }
    Nod* radacina = coada_cu_prioritati.top();
    decoding(radacina, text, codare_huffman);
}

int main() {

    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");

    vector<Nod*> noduri;
    int nr_caractere;
    char caracter;
    int frecv;
    fin >> nr_caractere;
    for (int i = 0; i < nr_caractere; i++){
        fin.get();
        fin.get(caracter);
        fin >> frecv;
        Nod* nod = new Nod;
        nod->frecventa = frecv;
        nod->caracter = caracter;
        nod->dreapta = nullptr;
        nod->stanga = nullptr;
        noduri.push_back(nod);
    }
    string codare_huffman;
    fin >> codare_huffman;
    fin.close();

    string text;
    Decodare_Huffman(noduri, text, codare_huffman);

    fout << text;
    fout.close();
    return 0;
}
