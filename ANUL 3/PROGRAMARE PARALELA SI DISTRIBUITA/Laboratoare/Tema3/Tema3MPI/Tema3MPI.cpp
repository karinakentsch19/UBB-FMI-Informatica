#include <iostream>
#include "mpi.h"
#include <fstream>
#include "string.h"
#include "chrono"

using namespace std;

const int MAX_CIF = 100000000;

/*
	Citeste un numar mare dintr-un fisier
*/
void citesteDinFisier(string numeFisier, int* numar, int& n) {
	ifstream fin(numeFisier);
	fin >> n;
	for (int i = 0; i < n; i++)
		fin >> numar[i];
	fin.close();
}

/*
	Scrie un numar mare dintr-un fisier
*/
void scrieInFisier(string numeFisier, int* numar, int n) {
	ofstream fout(numeFisier);
	fout << n << "\n";
	for (int i = 0; i < n; i++)
		fout << numar[i] << " ";
	fout.close();
}

/*
	Genereaza un numar mare de lungime n
*/
void genereazaNumarMare(int* numar, int n) {
	for (int i = 0; i < n; i++)
		numar[i] = rand() % 10;
}

/*
	Inverseaza un sir de numere
*/
void inverseazaSir(int* sir, int n) {
	for (int i = 0; i < n / 2; i++)
		swap(sir[i], sir[n - i - 1]);
}

/*
	Egaleaza doua numere mari.
	La numarul mai mic se adauga zerouri in fata pana are lungime egala cu numarul mai mare.
*/
void egaleazaLungimeNumere(int* numar1, int& n1, int n2) {
	inverseazaSir(numar1, n1);
	int numarZerouri = n2 - n1;
	while (numarZerouri) {
		numar1[n1] = 0;
		n1++;
		numarZerouri--;
	}
	inverseazaSir(numar1, n1);
}

void sumaSecventiala(int* numar1, int& n1, int* numar2, int& n2, int* numar3, int& n3) {
	int transport = 0, i;
	n3 = 0;

	if (n1 > n2) {
		egaleazaLungimeNumere(numar2, n2, n1);
	}
	else {
		egaleazaLungimeNumere(numar1, n1, n2);
	}

	i = n1 - 1; // Incepe adunarea de la cea mai putin semnificativa cifra

	while (i >= 0) {
		numar3[n3] = (numar1[i] + numar2[i] + transport) % 10;
		transport = (numar1[i] + numar2[i] + transport) / 10;
		i--;
		n3++;
	}
	if (transport != 0) {
		numar3[n3] = transport;
		n3++;
	}
	inverseazaSir(numar3, n3);
}

void adaugare_zero(int* a, int& na, int nrprocs) {
	if (na % nrprocs != 0) {
		inverseazaSir(a, na);
		int k = nrprocs - na % nrprocs;
		for (int i = 0; i < k; i++)
			a[na + i] = 0;
		na = na + k;
		inverseazaSir(a, na);
	}
}

void sterge_zero(int* sir, int& n) {
	inverseazaSir(sir, n);
	while (sir[n - 1] == 0) {
		n--;
	}
	inverseazaSir(sir, n);
}


//int main()
//{
// //Varianta 0
//	int n1, n2, n3;
//	int* numar1 = new int[MAX_CIF];
//	int* numar2 = new int[MAX_CIF];
//	int* numar3 = new int[MAX_CIF];
//	
//	//genereazaNumarMare(numar1, 1000);
//	//genereazaNumarMare(numar2, 1000);
//	//scrieInFisier("test2Numar1.txt", numar1, 1000);
//	//scrieInFisier("test2Numar2.txt", numar2, 1000);
//	/*genereazaNumarMare(numar1, 100);
//	genereazaNumarMare(numar2, 100000);
//	scrieInFisier("test3Numar1.txt", numar1, 100);
//	scrieInFisier("test3Numar2.txt", numar2, 100000);*/
//
//	auto timpStart = chrono::high_resolution_clock::now();
//
//	//citesteDinFisier("test1Numar1.txt", numar1, n1);
//	//citesteDinFisier("test1Numar2.txt", numar2, n2);
//	// 
//	//citesteDinFisier("test2Numar1.txt", numar1, n1);
//	//citesteDinFisier("test2Numar2.txt", numar2, n2);
//
//	citesteDinFisier("t4Numar1.txt", numar1, n1);
//	citesteDinFisier("test4Numar2.txt", numar2, n2);
//
//
//	sumaSecventiala(numar1, n1, numar2, n2, numar3, n3);
//
//	auto timpFinal = chrono::high_resolution_clock::now();
//	auto durata = chrono::duration_cast<chrono::microseconds>(timpFinal - timpStart);
//
//	cout << durata.count() << endl;
//	scrieInFisier("test4Rezultat.txt", numar3, n3);
//}

//int main(int argc, char* argv[]) {
//	//Varianta 1
//	int n1, n2, n3;
//	int* numar1 = new int[MAX_CIF];
//	int* numar2 = new int[MAX_CIF];
//	int* numar3 = new int[MAX_CIF];
//
//	int chunckSize;
//
//	int id, nrprocs;
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &nrprocs);
//	MPI_Comm_rank(MPI_COMM_WORLD, &id);
//
//	auto timpStart = chrono::high_resolution_clock::now();
//	if (id == 0) {
//		/*genereazaNumarMare(numar1, 100);
//		genereazaNumarMare(numar2, 100000);
//		scrieInFisier("test3Numar1.txt", numar1, 100);
//		scrieInFisier("test3Numar2.txt", numar2, 100000);*/
//
//		timpStart = chrono::high_resolution_clock::now();
//		citesteDinFisier("t4Numar1.txt", numar1, n1);
//		citesteDinFisier("test4Numar2.txt", numar2, n2);
//		if (n1 > n2) {
//			egaleazaLungimeNumere(numar2, n2, n1);
//		}
//		else {
//			egaleazaLungimeNumere(numar1, n1, n2);
//		}
//
//		adaugare_zero(numar1, n1, nrprocs - 1);
//		adaugare_zero(numar2, n2, nrprocs - 1);
//		n3 = n1;
//		chunckSize = n1 / (nrprocs - 1);
//
//		//procesul 0 imparte bucati din numar fiecarui proces
//		for (int i = 1; i < nrprocs; i++) {
//			MPI_Send(&chunckSize, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//			MPI_Send(numar1 + chunckSize * (i - 1), chunckSize, MPI_INT, i, 0, MPI_COMM_WORLD);
//			MPI_Send(numar2 + chunckSize * (i - 1), chunckSize, MPI_INT, i, 0, MPI_COMM_WORLD);
//		}
//
//
//		//primeste rezultatul
//		n3 = 0;
//		int lungimePartiala;
//		for (int i = 1; i < nrprocs; i++) {
//			MPI_Recv(&lungimePartiala, 1, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//			MPI_Recv(numar3 + n3, MAX_CIF, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//			n3 += lungimePartiala;
//		}
//
//		auto timpFinal = chrono::high_resolution_clock::now();
//		auto durata = chrono::duration_cast<chrono::microseconds>(timpFinal - timpStart);
//		cout << durata.count() << endl;
//		sterge_zero(numar3, n3);
//		scrieInFisier("test4Rezultat.txt", numar3, n3);
//	}
//	else {
//		MPI_Recv(&chunckSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//		int* auxNumar1 = new int[MAX_CIF];
//		int* auxNumar2 = new int[MAX_CIF];
//		int* auxNumar3 = new int[MAX_CIF];
//
//		MPI_Recv(auxNumar1, MAX_CIF, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//		MPI_Recv(auxNumar2, MAX_CIF, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//		int transport = 0;
//		for (int i = chunckSize - 1; i >= 0; i--) {
//			auxNumar3[i] = (auxNumar1[i] + auxNumar2[i] + transport) % 10;
//			transport = (auxNumar1[i] + auxNumar2[i] + transport) / 10;
//		}
//		if (id != 1) {
//			MPI_Send(&transport, 1, MPI_INT, id - 1, 0, MPI_COMM_WORLD);
//		}
//		else {
//			inverseazaSir(auxNumar3, chunckSize);
//			if (transport != 0) {
//				auxNumar3[chunckSize] = transport;
//				chunckSize++;
//			}
//			inverseazaSir(auxNumar3, chunckSize);
//		}
//		if (id != nrprocs - 1) {
//			int prevTransport;
//			MPI_Recv(&prevTransport, 1, MPI_INT, id + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//
//			int i = chunckSize - 1;
//			while (prevTransport) {
//				auxNumar3[i] = (auxNumar3[i] + prevTransport) % 10;
//				prevTransport = (auxNumar3[i] + prevTransport) / 10;
//				i--;
//			}
//		}
//		MPI_Send(&chunckSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//		MPI_Send(auxNumar3, chunckSize, MPI_INT, 0, 0, MPI_COMM_WORLD);
//	}
//	MPI_Finalize();
//
//	return 0;
//}

int main(int argc, char* argv[]) {
 //Varianta 2
	int n1, n2, n3;
	int* numar1 = new int[MAX_CIF];
	int* numar2 = new int[MAX_CIF];
	int* numar3 = new int[MAX_CIF];

	int chunckSize;

	int id, nrprocs;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &nrprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &id);
	
	auto timpStart = chrono::high_resolution_clock::now();
	if (id == 0) {
		/*genereazaNumarMare(numar1, 100);
		genereazaNumarMare(numar2, 100000);
		scrieInFisier("test3Numar1.txt", numar1, 100);
		scrieInFisier("test3Numar2.txt", numar2, 100000);*/

		timpStart = chrono::high_resolution_clock::now();
		citesteDinFisier("t4Numar1.txt", numar1, n1);
		citesteDinFisier("test4Numar2.txt", numar2, n2);
		if (n1 > n2) {
			egaleazaLungimeNumere(numar2, n2, n1);
		}
		else {
			egaleazaLungimeNumere(numar1, n1, n2);
		}
		
		adaugare_zero(numar1, n1, nrprocs);
		adaugare_zero(numar2, n2, nrprocs);
		n3 = n1;
		chunckSize = n1 / nrprocs;

		for (int i = 1; i < nrprocs; i++) {
			MPI_Send(&chunckSize, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
		}
	}
	
	if (id != 0) {
		MPI_Recv(&chunckSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
	}

	int* auxNumar1 = new int[MAX_CIF];
	int* auxNumar2 = new int[MAX_CIF];
	int* auxNumar3 = new int[MAX_CIF];

	MPI_Scatter(numar1, chunckSize, MPI_INT, auxNumar1, chunckSize, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Scatter(numar2, chunckSize, MPI_INT, auxNumar2, chunckSize, MPI_INT, 0, MPI_COMM_WORLD);

	int transport = 0;
	for (int i = chunckSize - 1; i >= 0; i--) {
		auxNumar3[i] = (auxNumar1[i] + auxNumar2[i] + transport) % 10;
		transport = (auxNumar1[i] + auxNumar2[i] + transport) / 10;
	}
	if (id != 0) {
		MPI_Send(&transport, 1, MPI_INT, id - 1, 0, MPI_COMM_WORLD);
	}
	if (id != nrprocs - 1) {
		int prevTransport;
		MPI_Recv(&prevTransport, 1, MPI_INT, id + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		
		int i = chunckSize - 1;
		while (prevTransport) {
			auxNumar3[i] = (auxNumar3[i] + prevTransport) % 10;
			prevTransport = (auxNumar3[i] + prevTransport) / 10;
			i--;
		}
	}
 //se aduna rezultatele partiale si se formeaza numarul final
	MPI_Gather(auxNumar3, chunckSize, MPI_INT, numar3, chunckSize, MPI_INT, 0, MPI_COMM_WORLD);

	if (id == 0) {
		inverseazaSir(numar3, n3);
		if (transport != 0) {
			numar3[n3] = transport;
			n3++;
		}
		inverseazaSir(numar3, n3);
		auto timpFinal = chrono::high_resolution_clock::now();
		auto durata = chrono::duration_cast<chrono::microseconds>(timpFinal - timpStart);
		cout << durata.count() << endl;
		sterge_zero(numar3, n3);
		scrieInFisier("test4Rezultat.txt", numar3, n3);
	}

	MPI_Finalize();
	
	return 0;
}