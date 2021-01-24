#include <iostream>
#include <stdlib.h>
#include <conio.h>

using namespace std;

class TicTacToe
{
    char Tablero[3][3];
public:
    TicTacToe();
    void LimpiarTablero();
    void Pintar();
    bool PonerFicha(char ficha, int fila, int columna);
    bool ComprobarFila(char ficha, int fila);
    bool ComprobarColumna(char ficha, int columna);
    bool ComprobarDiagonal(char ficha, int fila, int columna);
    bool TableroCompleto();
};

TicTacToe::TicTacToe()
{
    LimpiarTablero();
}

void TicTacToe::LimpiarTablero()
{
    int i, h;

    for(i=0; i<3; i++)
        for(h=0; h<3; h++)
            Tablero[i][h] = ' ';
}

void TicTacToe::Pintar()
{
    cout << endl;
    cout << Tablero[0][0] << "|" << Tablero[0][1] << "|" << Tablero[0][2] << endl;
    cout << "-+-+-" << endl;
    cout << Tablero[1][0] << "|" << Tablero[1][1] << "|" << Tablero[1][2] << endl;
    cout << "-+-+-" << endl;
    cout << Tablero[2][0] << "|" << Tablero[2][1] << "|" << Tablero[2][2] << endl;
}

bool TicTacToe::PonerFicha(char ficha, int fila, int columna)
{
    bool ReturnedValue = false;

    if(Tablero[fila][columna] == ' ')
    {
        Tablero[fila][columna] = ficha;
        ReturnedValue = true;
    }

    return ReturnedValue;
}

bool TicTacToe::ComprobarFila(char ficha, int fila)
{
    int i;
    bool Enraya = true;

    for(i=0; i<3; i++)
        if(Tablero[fila][i] != ficha)
            Enraya = false;

    return Enraya;
}

bool TicTacToe::ComprobarColumna(char ficha, int columna)
{
    int i;
    bool Enraya = true;

    for(i=0; i<3; i++)
        if(Tablero[i][columna] != ficha)
            Enraya = false;

    return Enraya;
}

bool TicTacToe::ComprobarDiagonal(char ficha, int fila, int columna)
{
    bool Enraya = false;

    if(fila==1 && columna==1)
    {
        if((Tablero[0][0]==ficha && Tablero[1][1]==ficha && Tablero[2][2]==ficha) || (Tablero[0][2]==ficha && Tablero[1][1]==ficha && Tablero[2][0]==ficha))
            Enraya = true;
    }
    else
        if((fila==0 && columna==0) || (fila==2 && columna==2))
        {
            if((Tablero[0][0]==ficha && Tablero[1][1]==ficha && Tablero[2][2]==ficha))
               Enraya = true;
        }
        else
            if((fila==2 && columna==0) || (fila==0 && columna==2))
            {
                if((Tablero[0][2]==ficha) && (Tablero[1][1]==ficha) && (Tablero[2][0]==ficha))
                Enraya = true;
            }

    return Enraya;
}

bool TicTacToe::TableroCompleto()
{
    int i, h;
    bool Full = true;

    for(i=0; i<3; i++)
        for(h=0; h<3; h++)
            if(Tablero[i][h]==' ')
                Full = false;

    return Full;
}

void PedirPosicion(char ficha, int &fila, int &columna)
{
    cout << "Jugador " << ficha << " introduzca fila y columna donde desea colocar su ficha." << endl;

    cout << "Fila: ";

    do
    {
        cin >> fila;
        fila--;

        if(fila<0 || fila>2)
            cout << "Valor erroneo, introduzca la fila de nuevo: ";

    }while(fila<0 || fila>2);

    cout << "Columna: ";

    do
    {
        cin >> columna;
        columna--;

        if(columna<0 || columna>2)
            cout << "Valor erroneo, introduzca la columna de nuevo: ";

    }while(columna<0 || columna>2);
}

int main()
{
    char respuesta;

    do
    {
        TicTacToe T;
        char ficha;
        bool PosicionLibre, TableroFull, Enraya, Filaenraya, Columnaenraya, Diagonalenraya;
        int fila, columna, i; //i = indice para saber el turno del jugador

        i=1;

        T.Pintar();

        do
        {
            if(i%2 != 0)
                ficha = 'X';
            else
                ficha = 'O';

            //Bucle para pedir posicion y colocar la ficha (en caso de que la casilla esté ocupada, se repite el proceso)
            do
            {
                PedirPosicion(ficha, fila, columna);
                PosicionLibre = T.PonerFicha(ficha, fila, columna);
                if(PosicionLibre == false)
                    cout << "Posicion ocupada, introduzca de nuevo fila y columna." << endl;

            }while(PosicionLibre == false);

            system("CLS");
            T.Pintar();

            //Comprueba si se ha realizado 3 en raya
            Filaenraya = T.ComprobarFila(ficha, fila);
            Columnaenraya = T.ComprobarColumna(ficha, columna);
            Diagonalenraya = T.ComprobarDiagonal(ficha, fila, columna);

            if(Filaenraya == true || Columnaenraya == true || Diagonalenraya == true)
                Enraya = true;
            else
                Enraya = false;

            if(Enraya == true)
                cout << "Enhorabuena, el ganador es el jugador " << ficha << " :D";
            else
            {
                TableroFull = T.TableroCompleto();
                if(TableroFull == true)
                    cout << "Oops, parece que se ha producido un empate :3";
                else
                    i++;
            }

        }while(Enraya == false && TableroFull == false);

        cout << "Desean jugar de nuevo? (S para si, N para no)" << endl;

        do
        {
            respuesta = toupper(getch());
            if(respuesta!='S' && respuesta!='N')
                cout << "Respuesta incorrecta, introduzca de nuevo: ";

        }while(respuesta!='S' && respuesta!='N');

    }while(respuesta == 'S');

    cout << "Gracias por jugar :D" << endl;

    return 0;
}
