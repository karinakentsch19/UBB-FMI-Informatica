#include "string"
#include "iostream"
#include "fstream"
#include <Windows.h>
#include <algorithm>
#include <vector>
using namespace std;

struct Punct {
    int x, y;
};

void writeHeader(ostream& out, int width, int height);



void makeCheckerboardBMP(string fileName, int squaresize, int n) {
    ofstream ofs;
    ofs.open(fileName + ".bmp");
    writeHeader(ofs, n, n);

    for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
            // ofs << (unsigned char) 0;
            //   ofs << (unsigned char) 0;
            //   ofs << (unsigned char) 0;

            if ((col % 2 == 0) && (row % 2 == 0)) {
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
            }
            else {
                ofs << (unsigned char)255;
                ofs << (unsigned char)255;
                ofs << (unsigned char)255;
            }
            if ((col % 2 == 1) && (row % 2 == 1)) {
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
            }
        }
    }
}

void writeHeader(ostream& out, int width, int height) {
    if (width % 4 != 0) {
        cerr << "ERROR: There is a windows-imposed requirement on BMP that the width be a multiple of 4.\n";
        cerr << "Your width does not meet this requirement, hence this will fail.  You can fix this\n";
        cerr << "by increasing the width to a multiple of 4." << endl;
        exit(1);
    }

    BITMAPFILEHEADER tWBFH;
    tWBFH.bfType = 0x4d42;
    tWBFH.bfSize = 14 + 40 + (width * height * 3);
    tWBFH.bfReserved1 = 0;
    tWBFH.bfReserved2 = 0;
    tWBFH.bfOffBits = 14 + 40;

    BITMAPINFOHEADER tW2BH;
    memset(&tW2BH, 0, 40);
    tW2BH.biSize = 40;
    tW2BH.biWidth = width;
    tW2BH.biHeight = height;
    tW2BH.biPlanes = 1;
    tW2BH.biBitCount = 24;
    tW2BH.biCompression = 0;

    out.write((char*)(&tWBFH), 14);
    out.write((char*)(&tW2BH), 40);
}


void axeDeCoordonate(string fileName, int n) {
    ofstream ofs;
    ofs.open(fileName + ".bmp");
    writeHeader(ofs, n, n);

    for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {

            if (col == n / 2 || row == n / 2) {
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
            }
            else {
                ofs << (unsigned char)255;
                ofs << (unsigned char)255;
                ofs << (unsigned char)255;
            }
        }
    }
}

vector<Punct> algoritmBresenham(int x1, int y1, int x2, int y2) {
    int m_new = 2 * (y2 - y1);
    int slope_error_new = m_new - (x2 - x1);
    vector<Punct> puncte = {};
    for (int x = x1, y = y1; x <= x2; x++) {
        Punct punct;
        punct.x = x;
        punct.y = y;
        puncte.push_back(punct);

        // Add slope to increment angle formed 
        slope_error_new += m_new;

        // Slope error reached limit, time to 
        // increment y and update slope error. 
        if (slope_error_new >= 0) {
            y++;
            slope_error_new -= 2 * (x2 - x1);
        }
    }
    return puncte;
}

bool verificaDacaExistaPunct(Punct punct, vector<Punct> puncte) {
    for (Punct p : puncte) {
        if (p.x == punct.x && p.y == punct.y) {
            return true;
        }

    }
    return false;
}

// Funcția care implementează algoritmul Bresenham pentru cerc
vector<Punct> bresenhamCircle(int xc, int yc, int r) {
    vector<Punct> points;
    int x = 0;
    int y = r;
    int d = 3 - 2 * r;

    while (x <= y) {
        // Punctele corespondente fiecarei optime din cerc
        points.push_back({ xc + x, yc + y });
        points.push_back({ xc - x, yc + y });
        points.push_back({ xc + x, yc - y });
        points.push_back({ xc - x, yc - y });
        points.push_back({ xc + y, yc + x });
        points.push_back({ xc - y, yc + x });
        points.push_back({ xc + y, yc - x });
        points.push_back({ xc - y, yc - x });

        if (d < 0) {
            d = d + 4 * x + 6;
        }
        else {
            d = d + 4 * (x - y) + 10;
            y--;
        }
        x++;
    }
    return points;
}

// Funcție pentru verificarea dacă un punct se află în cerc
bool isPointOnCircle(int x, int y, const vector<Punct>& points) {
    for (const auto& p : points) {
        if (p.x == x && p.y == y) {
            return true;
        }
    }
    return false;
}

void desenareDreaptaSiCerc(string fileName, int n, int x1, int y1, int x2, int y2) {
    ofstream ofs;
    ofs.open(fileName + ".bmp");
    writeHeader(ofs, n, n);

    Punct centru;
    centru.x = n / 2;
    centru.y = n / 2;

    int x1Translatat = centru.x + x1 * 20;
    int y1Translatat = centru.y + y1 * 20;
    int x2Translatat = centru.x + x2 * 20;
    int y2Translatat = centru.y + y2 * 20;

    // Desenare Cerc
    float R = 2.5 * 20;
    int xCentruCerc = 3;
    int yCentruCerc = -4;
    xCentruCerc = centru.x + xCentruCerc * 20;
    yCentruCerc = centru.y + yCentruCerc * 20;
    vector<Punct> puncteCerc = bresenhamCircle(xCentruCerc, yCentruCerc, (int)R);

    vector<Punct> puncte = algoritmBresenham(x1Translatat, y1Translatat, x2Translatat, y2Translatat);
    for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
            Punct punct;
            punct.x = col;
            punct.y = row;
            if (isPointOnCircle(col, row, puncteCerc)) { 
                ofs << (unsigned char)255;
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
            }
            else if (verificaDacaExistaPunct(punct, puncte)) {
                ofs << (unsigned char)0;
                ofs << (unsigned char)255;
                ofs << (unsigned char)0;
            }
            else if (col == n / 2 || row == n / 2) {
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
                ofs << (unsigned char)0;
            }
            else {
                ofs << (unsigned char)255;
                ofs << (unsigned char)255;
                ofs << (unsigned char)255;
            }
        }
    }
}


int main() {

    makeCheckerboardBMP("creat1", 3, 400);
    //axeDeCoordonate("axeDeCoordonate", 400);
    desenareDreaptaSiCerc("desen", 400, -2, 3, 2, 5);
    return 0;
}
