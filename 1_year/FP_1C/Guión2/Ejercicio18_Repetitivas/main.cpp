#include <iostream>

using namespace std;

class Young
{
    float seccion, longitud, carga, deformacion, Ilong;
public:
    void leer_datos();
    float calcular_esfuerzo();
    float calcular_deformacion();
    float modulo_young();
};

void Young::leer_datos()
{
    char resp1, resp2, resp3, resp4;

    cout << "Introduzca la carga, la seccion, la longitud inicial y el incremento de la longitud." << endl;

    cout << "Carga: ";
    cin >> carga;
    cout << endl;

    cout << "Ha introducido Newtons o KiloNewtons? (Pulse N para Newtons o K para KiloNewton)" << endl;
    cin >> resp1;

    if(resp1=='K')
        carga = carga * 1000;

    cout << "Seccion: ";
    cin >> seccion;
    cout << endl;

    cout << "Ha introducido metros o milimetros? (Pulse M para metros o m para milimetros)" << endl;
    cin >> resp2;

    if(resp2=='M')
        seccion = seccion * 1000;

    cout << "Longitud Inicial: ";
    cin >> longitud;
    cout << endl;

    cout << "Ha introducido metros o milimetros? (Pulse M para metros o m para milimetros)" << endl;
    cin >> resp3;

    if(resp3=='M')
        longitud = longitud * 1000;

    cout << "Incremento de Longitud: ";
    cin >> Ilong;
    cout << endl;

    cout << "Ha introducido metros o milimetros? (Pulse M para metros o m para milimetros)" << endl;
    cin >> resp4;

    if(resp4=='M')
        Ilong = Ilong * 1000;
}

float Young::calcular_esfuerzo()
{
    float s;

    cout << carga << endl;
    cout << seccion << endl;

    s = (carga/seccion);

    cout << s << endl;

    return s;
}

float Young::calcular_deformacion()
{
    float e;

    cout << Ilong << endl;
    cout << longitud << endl;

    e = (Ilong/longitud);

    cout << e << endl;

    return e;
}

float Young::modulo_young()
{
    float E, e, s;
    Young Y;

    e = Y.calcular_deformacion();
    s = Y.calcular_esfuerzo();

    E = (s/e);

    return E;
}

int main()
{
    Young Y;
    float E;

    Y.leer_datos();
    E = Y.modulo_young();

    cout << "Su modulo de Young es = " << E;
    return 0;
}
