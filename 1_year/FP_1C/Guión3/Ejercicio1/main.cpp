#include <iostream>
#define M 9

using namespace std;

class uno
{
    int tabla[M];
public:
    void cargar();
    int maximo();
    int minimo();
};

void uno::cargar()
{
    int i;

    cout << "Introduzca los 10 valores de la tabla: " << endl;

    for(i=0; i<=M; i++)
    {
        cout << "Introduzca el valor de la casilla " << i+1 << ": ";
        cin >> tabla[i];
    }

}

int uno::maximo()
{
    int maxi, i;

    maxi = tabla[0];

    for(i=0; i<=M; i++)
    {
        if(tabla[i]>maxi)
            maxi = tabla[i];
    }

    return maxi;
}

int uno::minimo()
{
    int mini, i;

    mini = tabla[0];

    for(i=0; i<=M; i++)
    {
        if(tabla[i]<mini)
            mini = tabla[i];
    }

    return mini;
}


int main()
{
    uno v;
    int maxi, mini;

    v.cargar();
    maxi = v.maximo();
    mini = v.minimo();

    cout << "El valor maximo de la tabla es: " << maxi << endl;
    cout << "El valor minimo de la tabla es: " << mini << endl;

    return 0;
}
