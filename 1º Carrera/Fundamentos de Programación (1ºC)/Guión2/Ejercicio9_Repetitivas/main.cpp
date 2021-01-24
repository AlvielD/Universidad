#include <iostream>

using namespace std;

class SumaNoPrimo
{
    int N;

public:
    void informacion();
    int suma();
};

void SumaNoPrimo::informacion()
{
    cout << "Introduzca de cuantos numeros quiere realizar la suma acumulada: ";
    cin >> N;
}

int SumaNoPrimo::suma()
{
    bool p;
    int i, h, suma;
    suma = 0;

    for(i=1;i<=N;i++)
    {
        p=true;
        h=2;

        while(h<=(i/2) && p==true)
        {
            if(i%h==0)
                p=false;
            h++;
        }
        if(p==false)
            suma = suma + i;
    }
    return suma;
}

int main()
{
    SumaNoPrimo S;
    int s;

    S.informacion();
    s = S.suma();

    cout << "Su suma total es: " << s;

    return 0;
}
