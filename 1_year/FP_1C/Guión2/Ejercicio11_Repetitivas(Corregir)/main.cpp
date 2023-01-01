#include <iostream>

using namespace std;

class Fibonacci
{
    int N;

public:
    void PedirNElementos();
    float MostrarElementos();
    int ComprobarElemento();
};

void Fibonacci::PedirNElementos()
{
    do
    {
        cout << "Introduzca el numero de elementos para su sucesion de Fibonacci: ";
        cin >> N;

    }while(N<1);
}

float Fibonacci::MostrarElementos()
{
    int a, b, fib, suma, i;
    float media;

    a=0,b=1,suma=1;
    cout << a << " " << b << " ";
    for(i=3;i<=N;i++)
    {
        fib = a + b;
        suma = suma + fib;

        cout << fib << " ";

        a=b,b=fib;
    };
    media = suma / (float)N;

    return media;
}

int Fibonacci::ComprobarElemento()
{
    int i=4, posicion=-1, a=1, b=1, fib;

    cout << "Introduzca el numero que desea comprobar: ";
    cin >> N;

    if(N==0)
        posicion = 1;
    else
        if(N==1)
            cout << "\nLa posicion que ocupa su numero en la secuencia es 2 o 3";
        else
            while(i<=45 || N!=fib)
            {
                fib = a + b;

                if(fib==N)
                    posicion = i;
                else
                    a=b, b=fib;

                i++;
            };
    return posicion;
}

int main()
{
    Fibonacci F;
    int posicion;

    posicion = F.ComprobarElemento();

    if(posicion!=-1)
        cout << "\nLa posicion que ocupa su numero en la secuencia es: " << posicion;


    return 0;
}
