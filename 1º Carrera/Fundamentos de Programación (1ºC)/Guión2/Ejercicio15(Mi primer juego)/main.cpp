#include <iostream>

using namespace std;

class Adivinar
{
    int Nsecreto, Puntos;
public:
    void Inicio();
    void Jugar();
};

void Adivinar::Inicio()
{
    int Semilla, i, N, suma;
    suma=0;

    cout << "Introduzca el numero de puntos para empezar: ";
    cin >> Puntos;

    cout << "Introduzca el numero que se usara como semilla para generar el numero secreto: ";

    do
    {
        cin >> Semilla;

        if(Semilla==0)
            cout << "Esa semilla no es valida, introduce otra: ";

    }while(Semilla==0);

    N=((Semilla%90)*123);

    for(i=2; i<=(N/2); i++)
    {
        if(N%i==0)
            suma=suma+i;
    };

    suma=suma+1+N;

    if(N<suma) //Si N<suma=true entonces el numero es abundante.
        Nsecreto=(((Semilla%90)*123)%12459);
    else
        Nsecreto=(((Semilla%90)*123)%5397);
}

void Adivinar::Jugar()
{
    int op;

    cout << "Que comience el juego :D, intenta adivinar el numero que estoy pensando..." << endl;

    while(Puntos>0 && op!=Nsecreto)
    {
        cin >> op;

        if(op<Nsecreto)
        {
            cout << "Oops, te has quedado corto, menos 1 punto :c" << endl;
            Puntos=Puntos-1;
        }
        else
            if(op>Nsecreto)
            {
                cout << "Vaya, parece que te has pasado, menos 2 puntos :'(" << endl;
                Puntos=Puntos-2;
            }
            else
                if(op==Nsecreto)
                {
                    cout << "Enhorabuena :D, tu ganas";
                    cout << "Estos son tus puntos " << Puntos;
                };

    }

        if(Puntos==0)
            cout << "Lo siento has perdido :(";
}

int main()
{
    Adivinar J;
    char Resp;

    do
    {
        J.Inicio();
        J.Jugar();

        cout << "\nQuieres jugar otra vez? Pulsa 's' para Si o 'n' para No: ";

        cin >> Resp;

    }while(Resp=='s');

    return 0;
}
