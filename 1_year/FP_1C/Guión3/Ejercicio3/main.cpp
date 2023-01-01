#include <iostream>
#define M 10
#define N 15

using namespace std;

class tres
{
    int tabla[M][N];
public:
    void cargar();
    int encontrar();
};

void tres::cargar()
{
    int i, h;

    for(i=0; i<=M; i++)
        for(h=0; h<=N; h++)
            tabla[i][h] = (i*10)+h;
}

int tres::encontrar()
{
    int n, i, h, p;
    p=0;

    cout << "Introduzca el numero que desea comprobar: ";
    cin >> n;

    for(i=0; i<=M; i++)
        for(h=0; h<=N; h++)
            if(n==tabla[i][h])
                p=1;

    return p;
}

int main()
{
    tres v;
    int p;

    v.cargar();
    p = v.encontrar();
    cout << p;

    return 0;
}
