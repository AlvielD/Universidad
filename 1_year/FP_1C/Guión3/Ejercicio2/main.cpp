#include <iostream>
#define M 10
using namespace std;

class matrices
{
    int tabla[M];
public:
    void cargar();
    bool encontrar();
};

void matrices::cargar()
{
    int i;

    for(i=0; i<=M; i++)
    {
        tabla[i] = i;
    };
}

bool matrices::encontrar()
{
    int NumBuscado, i;
    bool encontrado;
    i=0;
    encontrado=false;

    cout << "Introduzca el numero que desea comprobar si esta en la tabla: ";
    cin >> NumBuscado;

    while(i<=M && !encontrado)
    {
        if(tabla[i]==NumBuscado)
            encontrado = true;
        else
            i++;
    }

    return encontrado;
}


int main()
{
    matrices v;
    bool p;
    v.cargar();
    p = v.encontrar();

    cout << p;

    return 0;
}
