#include <iostream>

using namespace std;

class Fecha
{
    int agno;

public:

    void leer();
    bool bisiesto();

};

void Fecha::leer()
{
    cout << "Introduzca el año que desea saber si es bisiesto o no: ";
    cin >> agno;

}

bool Fecha::bisiesto()
{
    return(agno % 400 == 0 || (agno % 4 == 0 && agno % 100 != 0));

}


int main()
{
    int a;
    Fecha F;
    F.leer();
    a = F.bisiesto();
    if (a == 0)
        cout << "Este agno no es bisiesto";
    else
        cout << "Este agno es bisiesto";

    return 0;
}
