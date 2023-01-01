#include <iostream>
#include <locale.h>
#include <stdlib.h>
#include <windows.h>
#include "Peluqueria.h"

using namespace std;

int main()
{
    setlocale(LC_ALL, "");//Escribir � y tildes
    SetConsoleTitle("Peluqueria Corte 2.0 Huelva");
    peluqueria p;

    char opc;

    do
    {
        system("cls");
        //Men�:------------------------------------------------------------------------------
        cout << "Peluquer�a Corte 2.0 Huelva" << endl <<
        "\t\t\t\t\t\t�lvaro Esteban Mu�oz" << endl <<
        "------------------------------------------------------------------------" << endl <<
        "1 - Leer fichero (rescatar copia)" << endl <<
        "2 - Insertar peluquero" << endl <<
        "3 - Insertar cliente" << endl <<
        "4 - Retirar cliente" << endl <<
        "5 - Atender cliente" << endl <<
        "6 - Mostrar peluquer�a" << endl <<
        "7 - Eliminar un cliente" << endl <<
        "8 - Volcar a fichero (crear copia)" << endl <<
        "9 - Salir" << endl << endl << endl <<
        "Pulse la opci�n deseada : ";
        //-----------------------------------------------------------------------------------

        cin >> opc;

        switch(opc)
        {
        case '1': p.AbrirPeluqueria("inicial.dat");
                break;
        case '2': //Implementar Insertar peluquero
                break;
        case '3': //Implementar Insertar cliente
                break;
        case '4': //Implementar Retirar cliente
                break;
        case '5': //Implementar Atender cliente
                break;
        case '6': //Implementar Mostrar peluquer�a
                break;
        case '7': //Implementar Eliminar un cliente
                break;
        case '8': //Implementar Volcar a fichero
                break;
        case '9': cout << "Nos vemos :D";
                break;
        default : cout << "Opci�n no valida, introduzca de nuevo :c";
                break;
        }
    }while(opc!='9');

    return 0;
}
