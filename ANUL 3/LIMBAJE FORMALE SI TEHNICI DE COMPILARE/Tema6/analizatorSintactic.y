%{
#include "atrib.h"    
#include <stdio.h>
#include <stdlib.h>
#include "analizator.h"
#include <string.h>
#include <ctype.h>

extern int yylex();
extern FILE* yyin;
int hasErrors = 0;

void yyerror(const char* s);

char declbuffer[2500];
char codebuffer[2500];

int tempnr = 1;
void newTempName(char* s){
  sprintf(s,"temp%d", tempnr);
  tempnr++;
  sprintf(declbuffer, "%s%s dd 0, \n", declbuffer,s); 
}

char tempbuffer[250];
void parametrizare(char* str, char* copie){
  int is_number = 1; // Flag to check if the string is a number

    // Check if the string is a number
    for (int i = 0; str[i] != '\0'; i++) {
        if (!isdigit(str[i])) {
            is_number = 0; // If any character is not a digit, it's not a number
            break;
        }
    }

  if(is_number == 0)
    sprintf(copie, "[%s]", str);
  else
    sprintf(copie, str);
}

void copiazaTransformarea(char* operatie, char* cod, char* var1, char* var2, char* var){
  char copie_var1[100], copie_var2[100], copie_var[100];
  parametrizare(var1, copie_var1);
  parametrizare(var2, copie_var2);
  parametrizare(var, copie_var);
  if(strcmp("+", operatie) == 0){
    sprintf(tempbuffer, ADD_ASM_FORMAT, copie_var1, copie_var2, copie_var);
    strcat(cod, tempbuffer);
  } else if(strcmp("-", operatie) == 0){
    sprintf(tempbuffer, SUBTRACT_ASM_FORMAT, copie_var1, copie_var2, copie_var);
    strcat(cod, tempbuffer);
  } else if(strcmp("*", operatie) == 0){
    sprintf(tempbuffer, MULTIPLY_ASM_FORMAT, copie_var1, copie_var2, var, var);
    strcat(cod, tempbuffer);
  } else if(strcmp("/", operatie) == 0){
    sprintf(tempbuffer, DIVISION_ASM_FORMAT, var1, var1, copie_var2, copie_var);
    strcat(cod, tempbuffer);
  }else if(strcmp("%", operatie) == 0){
    sprintf(tempbuffer, MODULO_ASM_FORMAT, var1, var1, copie_var2, copie_var);
    strcat(cod, tempbuffer);
  }
}
%}
%union {
  char varname[10];
  attributes attrib;
  char opType[10];
  char codeType[2500];
}
%token START END READ WRITE PUNCT PUNCTVIRGULA SEMNCITIRE SEMNSCRIERE 
%token ATRIB PLUS MINUS DIV MOD INMULTIRE 
%token VIRGULA
%token INT BOOL FLOAT
%token <varname> ID
%token <varname> CONST
%type <attrib> expresie
%type <attrib> factor
%type <attrib> termen
%type <codeType> instr
%type <codeType> atribuire
%type <codeType> citire_scriere
%type <codeType> lista_declaratii
%type <codeType> declaratie
%type <codeType> lista_instructiuni
%left PLUS MINUS
%left INMULTIRE DIV MOD
%%
program:                START declaratie PUNCTVIRGULA lista_instructiuni END{
    printf(PROGRAM_STRUCTURE, declbuffer, $4);
};
citire_scriere:                 READ SEMNCITIRE ID{
    sprintf($$, CITIRE_VARIABILA, $3);
}
                       | WRITE SEMNSCRIERE ID{
                        char copie[100];
                        parametrizare($3, copie);
                        sprintf($$, SCRIERE_VARIABILA, copie);
                       };

lista_instructiuni:     instr lista_instructiuni{
    sprintf($$, "%s\n%s", $1, $2); // Concatenate $1 and $2 into $$
}
                        | instr{
    sprintf($$, "%s", $1); // $1 is the single instruction
};
instr:                  atribuire PUNCTVIRGULA{
    sprintf($$, "%s\n", $1);
}
                        | citire_scriere PUNCTVIRGULA{
    sprintf($$, "%s\n", $1);
};

                        
atribuire:              ID ATRIB expresie{
    sprintf($$, "%s\n mov eax, [%s]\n mov [%s], eax\n", $3.cod, $3.varn, $1);
};
expresie:               termen{
     strcpy($$.cod, $1.cod); strcpy($$.varn, $1.varn); 
}
                        | expresie PLUS termen{ 
    newTempName($$.varn); 
    sprintf($$.cod, "%s\n%s\n", $1.cod, $3.cod); 
    copiazaTransformarea("+", $$.cod, $1.varn, $3.varn, $$.varn);
}
                        | expresie MINUS termen{ 
    newTempName($$.varn); 
    sprintf($$.cod, "%s\n%s\n", $1.cod, $3.cod); 
    copiazaTransformarea("-",$$.cod, $1.varn, $3.varn, $$.varn);
};

termen:                 factor{ strcpy($$.cod, $1.cod); strcpy($$.varn, $1.varn); }
                        | termen INMULTIRE factor{ 
    newTempName($$.varn); 
    sprintf($$.cod, "%s\n%s\n", $1.cod, $3.cod); 
    copiazaTransformarea("*",$$.cod, $1.varn, $3.varn, $$.varn);
}
                        | termen DIV factor{ 
    newTempName($$.varn); 
    sprintf($$.cod, "%s\n%s\n", $1.cod, $3.cod); 
    copiazaTransformarea("/",$$.cod, $1.varn, $3.varn, $$.varn);
}
                        | termen MOD factor{ 
    newTempName($$.varn); 
    sprintf($$.cod, "%s\n%s\n", $1.cod, $3.cod); 
    copiazaTransformarea("%",$$.cod, $1.varn, $3.varn, $$.varn);
};

factor:                 ID{ strcpy($$.cod, ""); strcpy($$.varn, $1); }
                        | CONST{ strcpy($$.cod, ""); strcpy($$.varn, $1); };

lista_declaratii:       ID{
    sprintf($$, "%s dd 0, \n", $1);
    strcat($$, "format db '%d', 0, \n");
}
                        | ID VIRGULA lista_declaratii{
    sprintf($$, "%s dd 0\n%s\n", $1, $3);
};
declaratie:             tip_data lista_declaratii{
    sprintf(declbuffer, "%s", $2);
};
tip_data:               INT
                        | BOOL
                        | FLOAT;
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
            //printFip();
            //printTs();
        }
    } 