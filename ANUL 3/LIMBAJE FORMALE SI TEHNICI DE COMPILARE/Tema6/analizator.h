#ifndef DEFS_H
#define DEFS_H
#include "asm.h"

extern int num_lines, num_chars;
extern int hasErrors;

void readFromFile();

int getCodAtom(char atom[]);

int addInTs(char atom[]);

void printFip();

void printTs();

void adaugaConst(char constanta[]);

void adaugaId(char id[]);

void adaugaCuvantRezervat(char cuvant[]);

#endif