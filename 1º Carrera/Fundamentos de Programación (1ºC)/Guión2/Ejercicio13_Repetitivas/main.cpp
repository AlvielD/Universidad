#include <iostream>
#include <math.h>
#define k 9000000000
using namespace std;

class Cargas
{
    float q1, q2, r;

public:

    void leer();
    double fuerza();

};

void Cargas::leer()
{
    float Q1, Q2;
    cout << "Introduzca los valores de las cargas(en microculombios) q1, q2 y la distancia r(en metros) respectivamente: ";
    cin >> Q1 >> Q2 >> r;
    q1 = Q1/1000000, q2 = Q2/1000000;
}

double Cargas::fuerza()
{
    float F;
    F = (k*q1*q2)/(pow(r, 2));

    return F;
}
int main()
{
    Cargas C;
    float F;
    char respuesta;
    bool resp;

    do
    {
        C.leer();
        F = C.fuerza();

        cout << "El valor de la fuerza es = " << F;
        if(F>0)
            cout << " (repulsion)";
        else
            if(F<0)
                cout << " (atraccion)";

        cout << "\nDesea ejecutar de nuevo el programa con otros datos?\nPulsa 's' para Si\nPulsa 'n' para No\n";

        do
        {
            cin >> respuesta;

            switch(respuesta)
            {
                case 's': resp = true;
                            break;
                case 'n': resp = false;
                            break;
                default: cout << "Respuesta no valida. Introduzca de nuevo: ";
            }
        }while(respuesta!='s' && respuesta!='n');


    }while(resp==true);

    if(resp==false)
        cout << "\nGracias por usar mis servicios, hasta la proxima :)";

    return 0;
}
