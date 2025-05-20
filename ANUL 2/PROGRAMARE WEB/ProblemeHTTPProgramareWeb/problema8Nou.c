#include <windows.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
 
struct data {
  int nr;
  int tries;
};
 
int getCookie(int *id){
	char* cookieStr = getenv("HTTP_COOKIE");
	if(cookieStr == NULL)
		return -1;
	char* gameDataStr = strstr(cookieStr, "id=");
	if(gameDataStr == NULL)
		return -1;
	sscanf(gameDataStr, "id=%d", id);
	return 0;
} 
 
int getIdFromQueryString() {
	char buffer[2048];
	int id;
	getCookie(&id);
	return id;  
}
 
int getNumberFromQueryString() {
  char buffer[2048];
  int id, nr;
  strcpy(buffer, getenv("QUERY_STRING"));
  sscanf(buffer, "id=%d&nr=%d", &id, &nr);
  return nr;  
}

int init() {
    int r, id;
    HANDLE hFile;
    DWORD dwBytesWritten;
    char filename[MAX_PATH];
    struct data d;

    srand(GetCurrentProcessId());
    r = rand() % 100;

    do {
        id = rand();
        sprintf(filename, "C:\\xampp\\htdocs\\ProblemeHTTPProgramareWeb\\Temp\\%d.txt", id);
        hFile = CreateFile(filename, GENERIC_WRITE, 0, NULL, CREATE_NEW, FILE_ATTRIBUTE_NORMAL, NULL);
    } while (hFile == INVALID_HANDLE_VALUE);

    d.nr = r;
    d.tries = 0;
    if (!WriteFile(hFile, &d, sizeof(d), &dwBytesWritten, NULL)) {
        printf("Failed to write to file. Error code: %d\n", GetLastError());
    }
    CloseHandle(hFile);

    return id;
}
 
void destroy(int id) {
  char filename[100];
  sprintf(filename, "C:\\xampp\\htdocs\\ProblemeHTTPProgramareWeb\\Temp\\%d.txt", id);
  unlink(filename);
}
 
int getNumberFromFile(int id) {
    char filename[100];
    HANDLE hFile;
    DWORD bytesRead, bytesWritten;
    struct data d;

    sprintf(filename, "C:\\xampp\\htdocs\\ProblemeHTTPProgramareWeb\\Temp\\%d.txt", id);

    hFile = CreateFile(filename, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
    if (hFile == INVALID_HANDLE_VALUE)
        return -1;

    ReadFile(hFile, &d, sizeof(d), &bytesRead, NULL);
    d.tries++;
    SetFilePointer(hFile, 0, NULL, FILE_BEGIN);
    WriteFile(hFile, &d, sizeof(d), &bytesWritten, NULL);

    CloseHandle(hFile);

    return d.nr;
}
 
int getNoOfTries(int id) {
    char filename[100];
    HANDLE hFile;
    DWORD bytesRead;
    struct data d;

    sprintf(filename, "C:\\xampp\\htdocs\\ProblemeHTTPProgramareWeb\\Temp\\%d.txt", id);

    hFile = CreateFile(filename, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
    if (hFile == INVALID_HANDLE_VALUE) {
        printf("Failed to open file. Error code: %d\n", GetLastError());
        return -1;
    }

    if (!ReadFile(hFile, &d, sizeof(d), &bytesRead, NULL)) {
        printf("Failed to read from file. Error code: %d\n", GetLastError());
        CloseHandle(hFile);
        return -1;
    }

    CloseHandle(hFile);

    return d.tries;
}
 
int isNewUser() {
  if (getenv("QUERY_STRING") == NULL)
    return 1;
  if (strcmp(getenv("QUERY_STRING"), "") == 0)
    return 1;
  return 0;  
}
 
void printForm(int id) {
  printf("<form action='problema8Nou.cgi' method='get'>\n");
  printf("Nr: <input type='text' name='nr'><br>\n");
  printf("<input type='submit' value='Trimite'>\n");
  printf("</form>");;
}
 
int main() {
  int id, status;
  if (isNewUser()) {
    id = init();    
	printf("Set-Cookie: id=%d; path=/\n", id);
    status = 0;
  }
  else {
    int nr, nr2;
    id = getIdFromQueryString();
    nr = getNumberFromQueryString();
    nr2 = getNumberFromFile(id);
    if (nr2 == -1)
      status = 1;
    else if (nr == nr2)
      status = 2;
    else if (nr < nr2)
      status = 3;
    else if (nr > nr2)
       status = 4;                
  }
  printf("Content-type: text/html\n\n");
  printf("<html>\n<body>\n");
  
  switch (status) {
    case 0 : printf("Ati inceput un joc nou.<br>\n"); printForm(id); break;
    case 1 : printf("Eroare. Click <a href='problema8Nou.cgi'>here</a> for a new game!"); break;
    case 2 : printf("Ati ghicit din %d incercari. Click <a href='problema8Nou.cgi'>here</a> for a new game!</body></html>", getNoOfTries(id)); destroy(id); break;
    case 3 : printf("Prea mic!<br>\n"); printForm(id); break;
    case 4 : printf("Prea mare!<br>\n"); printForm(id);
  }
  
  printf("</body>\n</html>");
}