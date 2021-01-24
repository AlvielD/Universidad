#include <iostream>

using namespace std;

class primo
{
    int n;
    bool p;

public:

    void cargar();
    bool esPrimo();
    void ListadePrimos();

};

void primo::cargar()
{
    cout << "Introduzca el numero que desea comprobar: ";
    cin >> n;
}

bool primo::esPrimo()
{
    int i=2;

    while(i<=(n/2) && p==true)
    {
        p=true;

        if((n%i)==0)
            p = false;
        i++;
    }
    return p;
}

void primo::ListadePrimos()
{
    int Vini, Vfin, Vchang;
    primo P;

    cout << "Introduzca un valor inicial y un valor final para su intervalo: ";
    cin >> Vini >> Vfin;

    if(Vfin < Vini)
        Vchang = Vfin, Vfin = Vini, Vini = Vchang;

    for(n=Vini; n<=Vfin; n++)
        {
            p = P.esPrimo();

            if(p==true)
                cout << n << " ";
        }

}

int main()
{
    primo P;
    P.ListadePrimos();

    return 0;
}
