%{
#include <stdio.h>
#include <stdlib.h>
#include "analizator.h"

extern int yylex();
extern FILE* yyin;
int hasErrors = 0;

void yyerror(const char* s);
%}
%token ID CONST START END READ WRITE PUNCT PUNCTVIRGULA SEMNCITIRE SEMNSCRIERE 
%token ATRIB PLUS MINUS DIV MOD INMULTIRE PARANTEZADESCHISA PARANTEZAINCHISA IF
%token ACOLADADESCHISA ACOLADAINCHISA ELSE MAIMIC MAIMARE EGAL DIFERIT SI SAU WHILE STRUCT VIRGULA
%token INT BOOL FLOAT CATTIMP EXECUTA SFCATTIMP 
%left SAU
%left SI
%left EGAL DIFERIT MAIMIC MAIMARE
%left PLUS MINUS
%left INMULTIRE DIV MOD
%%
program:                START lista_instructiuni END;
citire:                 READ stream_citire;
stream_citire:          SEMNCITIRE ID 
                        | SEMNCITIRE ID stream_citire
                        | SEMNCITIRE ID PUNCT ID;
scriere:                WRITE stream_scriere;
stream_scriere:         SEMNSCRIERE ID 
                        | SEMNSCRIERE ID stream_scriere
                        | SEMNSCRIERE ID PUNCT ID;
lista_instructiuni:     instr lista_instructiuni
                        | instr;
instr:                  atribuire PUNCTVIRGULA
                        | citire PUNCTVIRGULA
                        | scriere PUNCTVIRGULA
                        | conditionala
                        | repetitiva
                        | declaratie PUNCTVIRGULA;
atribuire:              ID ATRIB expresie;
expresie:               ID
                        | CONST
                        | ID operatie expresie
                        | CONST operatie expresie
                        | ID PUNCT ID operatie expresie
                        | ID PUNCT ID;
operatie:               PLUS
                        | MINUS
                        | DIV
                        | MOD
                        | INMULTIRE;
conditionala:           IF PARANTEZADESCHISA conditie PARANTEZAINCHISA ACOLADADESCHISA lista_instructiuni ACOLADAINCHISA
                        | IF PARANTEZADESCHISA conditie PARANTEZAINCHISA ACOLADADESCHISA lista_instructiuni ACOLADAINCHISA ELSE ACOLADADESCHISA lista_instructiuni ACOLADAINCHISA %prec SAU;
conditie:               expresie_logica
                        | expresie_logica operator_logic conditie;
expresie_logica:        ID comparator ID
                        | ID comparator CONST;
comparator:             MAIMIC
                        | MAIMARE
                        | EGAL
                        | DIFERIT;
operator_logic:         SI 
                        | SAU;
repetitiva:             WHILE PARANTEZADESCHISA expresie_logica PARANTEZAINCHISA ACOLADADESCHISA lista_instructiuni ACOLADAINCHISA
                        | CATTIMP PARANTEZADESCHISA ID PARANTEZAINCHISA EXECUTA lista_instructiuni SFCATTIMP;
tip_de_data_predef:     STRUCT ID ACOLADADESCHISA declaratie ACOLADAINCHISA PUNCTVIRGULA;
lista_declaratii:       ID
                        | ID VIRGULA lista_declaratii;
declaratie:             tip_data lista_declaratii;
tip_data:               INT
                        | BOOL
                        | FLOAT
                        | tip_de_data_predef;
%%
void yyerror(const char* s) {
    hasErrors = 1;
    fprintf(stderr, "Syntax error on line %d\n", num_lines);
    exit(EXIT_FAILURE);
}

int main(int argc, char** argv) 
    {     ++argv, --argc;         /* skip over program name */ 
        if ( argc > 0 ) 
            yyin = fopen( argv[0], "r" ); 
        else 
            yyin = stdin; 
        readFromFile();
        yyparse();

        if (hasErrors == 0){
            printFip();
            printTs();
        }
    } 