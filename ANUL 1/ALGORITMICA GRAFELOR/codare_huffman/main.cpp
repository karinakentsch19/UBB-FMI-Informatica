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

void encode(Nod* radacina, string str, unordered_map<char, string> &huffmanCode){
    if (radacina == nullptr)
        return;
    if (!radacina->stanga && !radacina->dreapta)
        huffmanCode[radacina->caracter] = str;
    encode(radacina->stanga, str+"0", huffmanCode);
    encode(radacina->dreapta, str+"1", huffmanCode);

}

void Codare_Huffman(vector<Nod*> noduri, string& codare_huffman, char text[]){
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
        varf->caracter = std::min(varf->stanga->caracter, varf->dreapta->caracter);
        coada_cu_prioritati.push(varf);
    }
    Nod* radacina = coada_cu_prioritati.top();
    unordered_map<char, string> huffmanCode;
    encode(radacina, "", huffmanCode);
    for (int i = 0; i < strlen(text); i++)
        codare_huffman += huffmanCode[text[i]];
}

int main() {

    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");

    char text[10000];
    vector<Nod*> noduri;
    string codare_huffman = "";
    fin.getline(text, 10000);
    fin.close();
    frecventa_litere(text, noduri);
    Codare_Huffman(noduri, codare_huffman, text);
    fout << noduri.size() << "\n";
    for (int i = 0; i < noduri.size(); i++)
        fout << noduri[i]->caracter << " " << noduri[i]->frecventa << "\n";
    fout << codare_huffman;
    fout.close();
    return 0;
}
