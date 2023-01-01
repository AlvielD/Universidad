#include <iostream>
#define M 2
#define N 4

using namespace std;

typedef char cadena[30];

struct persona
{
    int DNI;
    cadena nombre;
};

class matrices
{
    persona tabla[M][N];
public:
    void cargar();
    void encontrar();
};

void matrices::cargar()
{
    int i, h;

    for(i=0; i<M; i++)
        for(h=0; h<N; h++)
            {
                cout << "Introduzca DNI de la persona" << ((4*i)+h+1) << ": ";
                cin >> tabla[i][h].DNI;
                cout << "Introduzca Nombre de la persona" << ((4*i)+h+1) << ": ";
                cin >> tabla[i][h].nombre;
            };
}

void matrices::encontrar()
{
    int i, h, DNIBusc, fila, columna;
    bool encontrado;
    i=0;
    encontrado=false;

    cout << "Indique el DNI que desea revisar: ";
    cin >> DNIBusc;

    while(i<=M && !encontrado)
    {
        h=0;
        while(h<=N && !encontrado)
        {
            if(tabla[i][h].DNI==DNIBusc)
                {
                    encontrado=true;
                    fila=i;
                    columna=h;
                }
            else
                h++;
        }
        i++;
    }

    if(encontrado==true)
        cout << "El DNI buscado se halla en la tabla y su dueño/a es: " << tabla[fila][columna].nombre;
    else
        cout << "El DNI no se halla en la tabla";
}

int main()
{
    matrices v;

    v.cargar();
    v.encontrar();

    return 0;
}
