#include <iostream>

using namespace std;

class Numero
{
    int a, b;

public:
    void informacion();
    int mcd();

};

void Numero::informacion()
{
    cout << "Introduzca dos numeros enteros: ";
    cin >> a >> b;

}

int Numero::mcd()
{
    while(a!=b)
    {
        while(a>b)
            {
                a=a-b;
            };

        while(b>a)
            {
                b=b-a;
            };
    };

    return a;

}
int main()
{
    int mcd;
    Numero H;

    H.informacion();
    mcd = H.mcd();

    cout << "Su maximo comun divisor es: " << mcd;

    return 0;
}
