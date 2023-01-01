#include <iostream>
#include <windows.h>
#include <stdlib.h>

using namespace std;

class Reloj
{
    int Hi, Mi, Si, SimTempo;

public:
    void Iniciar();
    void Simular();

};

void Reloj::Iniciar()
{
    bool ciclo=true;
    cout << "Indique la hora de simulacion (horas, minutos y segundos, respectivamente): ";
    cin >> Hi >> Mi >> Si;
    cout << "Indique ahora el tiempo que desea simular (en minutos): ";
    do
    {
        if(ciclo==false)
            cout << "Numero no valido (debe ser mayor que 0), introduzca de nuevo: ";

        cin >> SimTempo;

        SimTempo = SimTempo*60;
        ciclo = false;

    }while(SimTempo<=0);
}

void Reloj::Simular()
{
    system("CLS");
    int i=0;

    cout << "\n" << Hi << ":" << Mi << ":" << Si;

    while(i<=SimTempo)
    {
        Sleep(500);
        Si++, i++;

        if(Si==60)
            Mi++, Si=0;

        if(Mi==60)
            Hi++, Mi=0;

        system("CLS");
        cout << Hi << ":" << Mi << ":" << Si;
    };

}

int main()
{
    Reloj R;

    R.Iniciar();
    R.Simular();

    return 0;
}
