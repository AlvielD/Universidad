#include <iostream>

using namespace std;

class Billete
{
    int distancia, dias, edad;

public:

    void informacion();
    float operacion();

};

void Billete::informacion()
{
    cout << "Introduzca la distancia del viaje, los dias de duracion y la edad del pasajero respectivamente: ";
    cin >> distancia >> dias >> edad;

}

float Billete::operacion()
{
    float precio;

    if ((distancia > 800 && dias > 7) || (edad > 55))
            precio = (0.5*distancia*0.25);
    else
        precio = (0.5*distancia);

    return precio;
}

int main()
{
    Billete B;
    float precio;
    B.informacion();
    precio = B.operacion();
    cout << "El coste del billete es: " << precio;
    return 0;
}
