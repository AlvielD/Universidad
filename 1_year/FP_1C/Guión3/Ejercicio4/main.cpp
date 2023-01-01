#include <iostream>
#include <string.h>
#define M 3
#define N 4

using namespace std;

typedef char cadena[30];

class cuatro
{
    cadena tabla[M][N];
public:
    void cargar();
    void encontrar();
};

void cuatro::cargar()
{
    int i, h;

    for(i=0; i<=M; i++)
        for(h=0; h<=N; h++)
            {
                cout << "Introduzca el elemento " << ((i*5)+h+1) << ": ";
                cin >> tabla[i][h];
            }
}

void cuatro::encontrar()
{
    cadena ebuscado;
    int i, h, fila, columna;
    bool encontrado;
    i=0;

    encontrado=false;

    cout << "Introduzca la palabra que desea buscar: ";
    cin >> ebuscado;

    while((i<=M) && (!encontrado))
    {
        h=0;

        while((h<=N) && (!encontrado))
        {
            if(strcmp(tabla[i][h], ebuscado)==0)
            {
                encontrado=true;
                fila=i+1;
                columna=h+1;
            }
            else
                h++;
        }
        i++;
    }

    if(encontrado==true)
    {
        cout << "La palabra buscada se encuentra en la fila " << fila << " y en la columna " << columna << endl;
    }
    else
    {
        cout << "Palabra no encontrada";
    }
}

int main()
{
    cuatro v;

    v.cargar();
    v.encontrar();

    return 0;
}
