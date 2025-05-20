#include "string"
#include "iostream"
#include "fstream"
#include <Windows.h>
#include <algorithm>
#include <vector>
#define UNITATE 5
#define DIMENSIUNE 1700
using namespace std;

struct Punct {
	double x, y, z;
};

struct Punct2D {
	int x, y;
};

void writeHeader(ostream& out, int width, int height);
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

Punct2D project(Punct v) {
	double d = 200.0;
	return { (int)(DIMENSIUNE / 2 + (v.x * d / (d - v.z)) * UNITATE), (int)(DIMENSIUNE / 2 - (v.y * d / (d - v.z)) * UNITATE) };
}

vector<Punct> algoritmBresenham(Punct A, Punct B) {
	Punct2D AProject = project(A);
	Punct2D BProject = project(B);

	int offsetUnitate = (int)((UNITATE / sqrt(2)));

	int x1 = AProject.x;
	int y1 = AProject.y;
	int x2 = BProject.x;
	int y2 = BProject.y;

	vector<Punct> puncte;

	int dx = abs(x2 - x1);
	int dy = abs(y2 - y1);

	int sx = (x1 < x2) ? 1 : -1;
	int sy = (y1 < y2) ? 1 : -1;

	int err = dx - dy;

	while (true) {
		puncte.push_back({ (double)x1, (double)y1 });
		if (x1 == x2 && y1 == y2) break;

		int e2 = 2 * err;

		if (e2 > -dy) {
			err -= dy;
			x1 += sx;
		}
		if (e2 < dx) {
			err += dx;
			y1 += sy;
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

void translatiePunct(Punct& A, double dimensiuneX, double dimensiuneY, double dimensiuneZ) {
	A.x = A.x + dimensiuneX;
	A.y = A.y + dimensiuneY;
	A.z = A.z - dimensiuneZ;
}

void rotatie45Grade(Punct& A) {

	double rad = sqrt(2) / 2;
	double xNou = A.x * rad + A.z * rad;
	double yNou = A.y;
	double zNou = -rad * A.x + rad * A.z;

	A.x = xNou;
	A.y = yNou;
	A.z = zNou;
}

void transformCamera(Punct& v, Punct camPos, Punct lookAt) {
	Punct forward = { lookAt.x - camPos.x, lookAt.y, lookAt.z - camPos.z };
	double fLen = sqrt(forward.x * forward.x + forward.y * forward.y + forward.z * forward.z);
	forward = { forward.x / fLen, forward.y / fLen, forward.z / fLen };
	Punct up = { 0, 1, 0 };
	Punct right = { up.y * forward.z - up.z * forward.y, up.z * forward.x - up.x * forward.z, up.x * forward.y - up.y * forward.x }; double rLen = sqrt(right.x * right.x + right.y * right.y + right.z * right.z);
	right = { right.x / rLen, right.y / rLen, right.z / rLen };
	Punct newUp = { forward.y * right.z - forward.z * right.y, forward.z * right.x - forward.x * right.z, forward.x * right.y - forward.y * right.x };
	Punct transformed = { (v.x - camPos.x) * right.x + (v.y - camPos.y) * right.y + (v.z - camPos.z) * right.z, (v.x - camPos.x) * newUp.x + (v.y - camPos.y) * newUp.y + (v.z - camPos.z) * newUp.z, (v.x - camPos.x) * forward.x + (v.y - camPos.y) * forward.y + (v.z - camPos.z) * forward.z };

	v.x = transformed.x;
	v.y = transformed.y;
	v.z = transformed.z;
}

void addAll(vector<Punct>& colectie, const vector<Punct>& multime) {
	cout << multime.size() << '\n';
	colectie.insert(colectie.end(), multime.begin(), multime.end());
}

void desenareCub(string fileName, int n, int x, int y, int z, int l) {

	// Scalare cu 20
	l = l * 20;

	ofstream ofs;
	ofs.open(fileName + ".bmp");
	writeHeader(ofs, n, n);

	Punct centru;
	centru.x = n / 2;
	centru.y = n / 2;


	Punct A, B, C, D, Aprim, Bprim, Cprim, Dprim, OCub;
	Dprim.x = x; Dprim.y = y; Dprim.z = z;
	Cprim.x = x + l; Cprim.y = y; Cprim.z = z;
	Bprim.x = x + l; Bprim.y = y + l; Bprim.z = z;
	Aprim.x = x; Aprim.y = y + l; Aprim.z = z;

	A.x = Aprim.x; A.y = Aprim.y; A.z = Aprim.z + l;
	B.x = Bprim.x; B.y = Bprim.y; B.z = Bprim.z + l;
	C.x = Cprim.x; C.y = Cprim.y; C.z = Cprim.z + l;
	D.x = Dprim.x; D.y = Dprim.y; D.z = Dprim.z + l;


	rotatie45Grade(A);
	rotatie45Grade(B);
	rotatie45Grade(C);
	rotatie45Grade(D);
	rotatie45Grade(Aprim);
	rotatie45Grade(Bprim);
	rotatie45Grade(Cprim);
	rotatie45Grade(Dprim);

	double dimensiuneX = 40.0 - Dprim.x;
	double dimensiuneY = 40.0 - Dprim.y;
	double dimensiuneZ = -40.0 - Dprim.z;

	translatiePunct(A, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(B, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(C, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(D, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(Aprim, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(Bprim, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(Cprim, dimensiuneX, dimensiuneY, dimensiuneZ);
	translatiePunct(Dprim, dimensiuneX, dimensiuneY, dimensiuneZ);


	Punct camPos = { -50.0,50.0,-50.0 };
	Punct lookAt = { 0.0,0.0,0.0 };

	transformCamera(A, camPos, lookAt);
	transformCamera(B, camPos, lookAt);
	transformCamera(C, camPos, lookAt);
	transformCamera(D, camPos, lookAt);
	transformCamera(Aprim, camPos, lookAt);
	transformCamera(Bprim, camPos, lookAt);
	transformCamera(Cprim, camPos, lookAt);
	transformCamera(Dprim, camPos, lookAt);

	cout << A.x << ' ' << A.y << ' ' << A.z << '\n';
	cout << B.x << ' ' << B.y << ' ' << B.z << '\n';
	cout << C.x << ' ' << C.y << ' ' << C.z << '\n';
	cout << D.x << ' ' << D.y << ' ' << D.z << '\n';
	cout << Aprim.x << ' ' << Aprim.y << ' ' << Aprim.z << '\n';
	cout << Bprim.x << ' ' << Bprim.y << ' ' << Bprim.z << '\n';
	cout << Cprim.x << ' ' << Cprim.y << ' ' << Cprim.z << '\n';
	cout << Dprim.x << ' ' << Dprim.y << ' ' << Dprim.z << '\n';

	vector<Punct> cub;
	addAll(cub, algoritmBresenham(Dprim, Cprim));
	addAll(cub, algoritmBresenham(Cprim, Bprim));
	addAll(cub, algoritmBresenham(Bprim, Aprim));
	addAll(cub, algoritmBresenham(Aprim, Dprim));
	addAll(cub, algoritmBresenham(D, C));
	addAll(cub, algoritmBresenham(C, B));
	addAll(cub, algoritmBresenham(B, A));
	addAll(cub, algoritmBresenham(A, D));
	addAll(cub, algoritmBresenham(D, Dprim));
	addAll(cub, algoritmBresenham(C, Cprim));
	addAll(cub, algoritmBresenham(B, Bprim));
	addAll(cub, algoritmBresenham(A, Aprim));


	for (int row = 0; row < n; row++) {
		for (int col = 0; col < n; col++) {
			Punct punct;
			punct.x = col;
			punct.y = row;
			if (verificaDacaExistaPunct(punct, cub)) {
				ofs << (unsigned char)255;
				ofs << (unsigned char)0;
				ofs << (unsigned char)0;
			}
			else if ((punct.x >= centru.x && punct.y == centru.y)  // Ox
				|| (punct.x == centru.x && punct.y >= centru.y) // Oy
				|| (punct.x == punct.y && punct.x <= centru.x && punct.y <= centru.y) // Oz
				) {
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

	desenareCub("cub", DIMENSIUNE, 0, 0, 0, 1);
	return 0;
}
