#include <iostream>

using namespace std;

class Juego
{
    int Vmin, Vmax, Vmed;
public:
    void PedirExtremos();
    char AdivinarNumero();
    bool CompruebaEsMenor();
    bool CompruebaEsMayor();
};

void Juego::PedirExtremos()
{
    cout << "Introduzca los extremos del intervalo en el que se comprende su numero [a, b] respectivamente: ";

    do
    {
        cin >> Vmin >> Vmax;

        if(Vmin>Vmax)
            cout << "Error. 'a' debe ser mayor que 'b'" << endl << "Introduzca de nuevo: ";

    }while(Vmin>Vmax);
}

char Juego::AdivinarNumero()
{
    char respuesta;

    Vmed = (Vmin + Vmax)/2;

    cout << "Es " << Vmed << " el numero que estas pensando? " << endl << "> (No, es mayor), < (No, es menor), = (Si, ese es)." << endl;

    do
    {
        cin >> respuesta;

        if(respuesta!='>' && respuesta!='<' && respuesta!='=')
            cout << "Error, respuesta no valida, responda de nuevo: ";

    }while(respuesta!='>' && respuesta!='<' && respuesta!='=');

    return respuesta;
}

bool Juego::CompruebaEsMenor()
{
    bool NoTrampas;

    NoTrampas=true;

    Vmax = Vmed - 1;

    if(Vmin>Vmax)
        NoTrampas=false;

    return NoTrampas;
}

bool Juego::CompruebaEsMayor()
{
    bool NoTrampas;

    NoTrampas=true;

    Vmin = Vmed + 1;

    if(Vmin>Vmax)
        NoTrampas=false;

    return NoTrampas;
}

int main()
{
    Juego J;
    char respuesta;
    bool NoTrampasMenor, NoTrampasMayor;

    J.PedirExtremos();

    do
    {
        respuesta = J.AdivinarNumero();

        if(respuesta=='<')
        {
            NoTrampasMenor = J.CompruebaEsMenor();
            if(NoTrampasMenor==false)
                cout << "Estas intentado engagnarme, hemos acabado de jugar :(";
        }
        else
        {
            if(respuesta=='>')
            {
                NoTrampasMayor = J.CompruebaEsMayor();
                if(NoTrampasMayor==false)
                    cout << "Estas intentado engagnarme, hemos acabado de jugar :(";
            }
            else
                cout << "Lo he conseguido, esta vez gano yo :D";
        }

    }while(respuesta!='=' && (NoTrampasMayor==true || NoTrampasMenor==true));

    return 0;
}
