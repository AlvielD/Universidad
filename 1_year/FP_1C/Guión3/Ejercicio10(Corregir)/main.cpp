#include <iostream>
#include <cstdlib>
#include <ctime>

using namespace std;

struct Casilla
{
    char Letra;
    int Numero;
};

class Tesoro
{
    Casilla Tablero[5][5];
    int Puntos;
public:
    void Iniciar();
    bool Jugar();
    void MostrarTablero();
};

void Tesoro::Iniciar()
{
    int Randomi, Randomh;

    Puntos=15;

    srand(time(0));

    Tablero[][]






}

int main()
{
    Tesoro T;

    T.Iniciar();

    return 0;
}
