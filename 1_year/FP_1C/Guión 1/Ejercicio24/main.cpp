#include <iostream>
#include <math.h>
#define e 0.00000000000885
#define pi 3.14159
using namespace std;

class CampoElec
{
    float q, r;

public:

    void leer();
    double intensidad();

};

void CampoElec::leer()
{
    float Q;
    cout << "Introduzca los valores deseados para q(en microculombios) y r(en metros): ";
    cin >> Q >> r;
    q = (Q/1000000);
}

double CampoElec::intensidad()
{
    float E;
    E = q/(4*pi*e*(pow(r, 2)));

    return E;
}

int main()
{
    float E;
    CampoElec C;
    C.leer();
    E = C.intensidad();
    cout << "La intensidad del campo electrico generada por su carga tiene un valor de " << E << " N/C";

    return 0;
}
