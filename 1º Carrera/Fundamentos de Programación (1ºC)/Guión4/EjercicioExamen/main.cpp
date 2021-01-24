// ----------- MODELO A1 -----------------------------------------------------------
// ----------- 2ª prueba práctica - curso 18/19 convocatoria de febrero ------------
// ----------- NOMBRE DEL ALUMNO: Álvaro Esteban Muñoz
// ----------- DNI DEL ALUMNO: 45157241-F

#include <iostream>
#include <cmath>
#include <cstring>

using namespace std;

class Punto
{
    float x;
    float y;
public:
    Punto();
    void CambiarCoord (float cx, float cy);
    float CoordX ();
    float CoordY ();
};

Punto::Punto()
{
    x=0;
    y=0;
}

void Punto::CambiarCoord(float cx, float cy)
{
    x=cx;
    y=cy;
}

float Punto::CoordX()
{
    return x;
}

float Punto::CoordY()
{
    return y;
}


class Figura
{
    Punto Puntos[3];
    char Color[10];
public:
    Figura();
    void Crear();
    void Lados (float &L1, float &L2, float &L3);
    void MostrarCoord();
    void VerColor(char C[10]);
};

Figura::Figura()
{
    strcpy(Color, "sin color");
}

void Figura::Crear()
{
    float cx, cy;

    cout << "Introduzca las coordenas del triangulo a crear." << endl;
    for(int i=0; i<3; i++)
    {
        cout << "Coordenada " << i+1 << ": ";
        cin >> cx >> cy;
        Puntos[i].CambiarCoord(cx, cy);
    }
    cout << "Introduzca ahora el color del triangulo: ";
    cin >> Color;
}

void Figura::Lados (float &L1, float &L2, float &L3)
{
    L1 = sqrt(pow(Puntos[0].CoordX()-Puntos[1].CoordX(),2) +
              pow(Puntos[0].CoordY()-Puntos[1].CoordY(),2));

    L2 = sqrt(pow(Puntos[1].CoordX()-Puntos[2].CoordX(),2) +
              pow(Puntos[1].CoordY()-Puntos[2].CoordY(),2));

    L3 = sqrt(pow(Puntos[2].CoordX()-Puntos[0].CoordX(),2) +
              pow(Puntos[2].CoordY()-Puntos[0].CoordY(),2));
}

void Figura::MostrarCoord()
{
    int i;

    for (i=0; i<3; i++)
      cout << "(" << Puntos[i].CoordX() << ", " << Puntos[i].CoordY() << ") ";
}

void Figura::VerColor(char C[10])
{
  strcpy(C, Color);
}



void PerimetroArea(Figura F, float &P, float &A)
{
    float L1, L2, L3, S;

    F.Lados(L1, L2, L3);

    P = L1+L2+L3;

    S = P/2;

    A = sqrt(S * (S-L1) * (S-L2) * (S-L3));
}

int main()
{
    Figura F[10];
    int nFig, i, j;
    char col[10];
    float P, A;
    bool HayRojo = false;

    cout << "Introduzca el numero de figuras que desea crear: ";
    cin >> nFig;

    for(i=0; i<nFig; i++)
    {
        F[i].Crear();
    }

    for(j=0; j<nFig; j++)
    {
        F[j].VerColor(col);
        if(strcmp(col, "rojo")==0)
        {
            HayRojo = true;
            PerimetroArea(F[j], P, A);
            cout << "El area y perimetro del triangulo " << j+1 << " de coordenadas ";
            F[j].MostrarCoord();
            cout << " son: " << endl;
            cout << "Perimetro: " << P << endl;
            cout << "Area: " << A << endl;
        }
    }

    if(HayRojo == false)
        cout << "No hay ningun triangulo rojo entre los creados.";

    return 0;
}
