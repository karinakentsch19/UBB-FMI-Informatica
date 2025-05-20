%{
#include <stdio.h>
#include <string.h>
#include "analizator.h"
#include "analizatorSintactic.tab.h"

int num_lines = 1;

struct {
    char atom[20];
    int cod;
} cod_atomi[100];
int num_atoms = 0;

void readFromFile() {
    FILE *file = fopen("codificari.csv", "r");
    if (file == NULL) {
        perror("Error opening file");
        return;
    }

    char line[100];
    while (fgets(line, sizeof(line), file) != NULL) {
        char atom[20];
        int cod;

        if (sscanf(line, "%19[^:]:%d", atom, &cod) == 2) {
                strncpy(cod_atomi[num_atoms].atom, atom, sizeof(cod_atomi[num_atoms].atom) - 1);
                cod_atomi[num_atoms].atom[sizeof(cod_atomi[num_atoms].atom) - 1] = '\0';
                cod_atomi[num_atoms].cod = cod;
                num_atoms++;
        } else {
            printf("Invalid line format: %s", line);
        }
    }

    fclose(file);
}

struct {
    int cod_atom;
    int ts_line;
} fip[10000];
int fip_len = 0;


struct {
    char atom[20];
    int number;
} ts[10000];
int ts_len = 0;

int getCodAtom(char atom[]){
    for(int i = 0 ; i < num_atoms ; i ++)
        if(strcmp(cod_atomi[i].atom, atom) == 0)
            return cod_atomi[i].cod;
    return -1;
}

int addInTs(char atom[]){
    for(int i = 0 ; i < ts_len; i ++)
        if(strcmp(ts[i].atom,atom) == 0)
            return ts[i].number;
    strncpy(ts[ts_len].atom, atom, sizeof(ts[ts_len].atom) - 1);
    ts[ts_len].atom[sizeof(ts[ts_len].atom) - 1] = '\0';
    ts[ts_len].number = ts_len;
    ts_len++;
    return ts_len-1;
}

void printFip() {
    printf("FIP (File Input Pair):\n");
    for (int i = 0; i < fip_len; i++) {
        printf("Index %d: Cod Atom = %d, TS Line = %d\n", i, fip[i].cod_atom, fip[i].ts_line);
    }
}


void printTs() {
    printf("TS (Symbol Table):\n");
    for (int i = 0; i < ts_len; i++) {
        printf("Index %d: Atom = %s\n", i, ts[i].atom);
    }
}

void adaugaConst(char constanta[]){
    int linieInTS = addInTs(constanta);
    fip[fip_len].cod_atom = getCodAtom("CONST");
    fip[fip_len].ts_line = linieInTS;
    fip_len++;
}

void adaugaId(char id[]){
    int linieInTS = addInTs(id);
    fip[fip_len].cod_atom = getCodAtom("ID");
    fip[fip_len].ts_line = linieInTS;
    fip_len++;
}

void adaugaCuvantRezervat(char cuvant[]){
    fip[fip_len].cod_atom = getCodAtom(cuvant);
    fip[fip_len].ts_line = -1;
    fip_len++;
}

%}
%option noyywrap
cifra   [0-9]
litera [a-zA-Z] 
%%
[ \t\r]+ /*fara spatiu, tab, enter*/
\n                                  { num_lines++; }
{cifra}{cifra}*{litera}+({litera}|{cifra})* {hasErrors=1; printf("Eroare la linia %d: Atom invalid %s\n", num_lines, yytext);}
{cifra}+(\.{cifra}+)?               {adaugaConst(yytext); return CONST;}
"cattimp" {adaugaCuvantRezervat(yytext); return CATTIMP;}
"executa" {adaugaCuvantRezervat(yytext); return EXECUTA;}
"sfcattimp" {adaugaCuvantRezervat(yytext); return SFCATTIMP;}
"start" {adaugaCuvantRezervat(yytext); return START;}
"end" {adaugaCuvantRezervat(yytext); return END;}
"read" {adaugaCuvantRezervat(yytext); return READ;}
"write" {adaugaCuvantRezervat(yytext); return WRITE;}
"if" {adaugaCuvantRezervat(yytext); return IF;}
"else" {adaugaCuvantRezervat(yytext); return ELSE;}
"while" {adaugaCuvantRezervat(yytext); return WHILE;}
"struct" {adaugaCuvantRezervat(yytext); return STRUCT;}
"int" {adaugaCuvantRezervat(yytext); return INT;}
"bool" {adaugaCuvantRezervat(yytext); return BOOL;}
"float" {adaugaCuvantRezervat(yytext); return FLOAT;}
"<<" {adaugaCuvantRezervat(yytext); return SEMNSCRIERE;}
">>" {adaugaCuvantRezervat(yytext); return SEMNCITIRE;}
"==" {adaugaCuvantRezervat(yytext); return EGAL;}
"!=" {adaugaCuvantRezervat(yytext); return DIFERIT;}
"&&" {adaugaCuvantRezervat(yytext); return SI;}
"||" {adaugaCuvantRezervat(yytext); return SAU;}
"{" {adaugaCuvantRezervat(yytext); return ACOLADADESCHISA;}
"}" {adaugaCuvantRezervat(yytext); return ACOLADAINCHISA;}
"(" {adaugaCuvantRezervat(yytext); return PARANTEZADESCHISA;}
")" {adaugaCuvantRezervat(yytext); return PARANTEZAINCHISA;}
";" {adaugaCuvantRezervat(yytext); return PUNCTVIRGULA;}
"," {adaugaCuvantRezervat(yytext); return VIRGULA;}
"=" {adaugaCuvantRezervat(yytext); return ATRIB;}
"+" {adaugaCuvantRezervat(yytext); return PLUS;} 
"-" {adaugaCuvantRezervat(yytext); return MINUS;}
"/" {adaugaCuvantRezervat(yytext); return DIV;}
"*" {adaugaCuvantRezervat(yytext); return INMULTIRE;}
"%" {adaugaCuvantRezervat(yytext); return MOD;}
"<" {adaugaCuvantRezervat(yytext); return MAIMIC;}
">" {adaugaCuvantRezervat(yytext); return MAIMARE;}
{litera}({litera}|{cifra})*         {adaugaId(yytext); return ID;}
.                                   {hasErrors=1; printf("Eroare la linia %d: Atom invalid %s\n", num_lines, yytext); } 
%%