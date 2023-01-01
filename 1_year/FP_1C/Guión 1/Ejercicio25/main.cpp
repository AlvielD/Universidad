#include <iostream>
#include <math.h>
#define g 9.8

using namespace std;

class Energia
{
    float m, V, H;

public:

    void leer();
    float ECinetica();
    float EPotencial();

};

void Energia::leer()
{
    cout << "Introduzca los valores deseados para la masa (en kg), la velocidad (en km/h) y la altura (en km) de un cuerpo: ";
    cin >> m >> V >> H;

}

float Energia::EPotencial()
{
    float EP, h;
    h = H*1000;
    EP = m*g*h;

    return EP;
}

float Energia::ECinetica()
{
    float EC, v;
    v = (V*1000)/3600.0;
    EC = (1.0/2)*m*(pow(v, 2));

    return EC;
}

int main()
{
    Energia E;
    float EP, EC, EM;

    E.leer();
    EP = E.EPotencial();
    EC = E.ECinetica();
    EM = EP + EC;
    cout << "Energia potencial = " << EP;
    cout << "\n Energia cinetica = " << EC;
    cout << "\n Energia mecanica = " << EM;
    return 0;
}
