#include <iostream>
#include <cstdlib>
#include <time.h>

using namespace std;

class Juego
{
    int numero;
    int n_intento;
    int intento;

    int pedir_numero();
public:
    void generar_numero();
    void jugada();
};

int Juego::pedir_numero()
{
    int n;

    cout << "Introduce numero: ";
    cin >> n;

    return n;
}

void Juego::generar_numero()
{
    srand(time(NULL));

    numero = 1+(rand()%(999-1));
}

void Juego::jugada()
{
    Juego J;
    int UserNum, i=0;

    J.generar_numero();

    cout << "Adelante, intenta adivinar en que numero estoy pensando ;)" << endl;

    do
    {
        UserNum = J.pedir_numero();

        if(UserNum==numero)
            cout << endl << "Enhorabuena, has acertado :D";
        else
            if(UserNum>numero)
                cout << "Oops, te has pasado :c" << endl;
            else
                cout << "Casi que no llegas :-/" << endl;

        if(i==9)
            cout << "Lo siento has perdido D:";

        i++;

    }while(UserNum!=numero && i<=9);

}

int main()
{
    Juego J;

    J.jugada();

    return 0;
}
