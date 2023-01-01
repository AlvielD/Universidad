#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <cstring>
#include <conio.h>

#include "Productos.h"
#include "Ventas.h"

using namespace std;

int main()
{
    //Variables del menú
    char nsp=164;
    productos p("resumen.dat", "Ventas.dat");
    int opc;

    //Variables del método anadirventa
    bool anadido;

    //Variables del método obtenerestadisticas
    int tipo, annoini, annofin;


    do
    {
        system("cls");
        cout << "\t Practica 1\t\t\tAlvaro Esteban Mu" << nsp << "oz" << endl << endl << endl <<
        "\t-----------------------------------------------------\n" <<
        "\t1.- Ver fichero detalle ventas\n" <<
        "\t2.- Ver fichero de productos\n" <<
        "\t3.- A" << nsp << "adir venta\n" <<
        "\t4.- Crear resumen ventas\n" <<
        "\t5.- Obtener estadisticas (tipo producto y periodo)\n" <<
        "\t0.- Salir\n" <<
        "\t----------------\n" <<
        "Elije opcion: ";
        cin >>  opc;

        switch(opc)
        {
            case 1: system("cls");
                    p.mostrarventas();
                    system("pause");
                    break;

            case 2: system("cls");
                    p.mostrarproductos();
                    system("pause");
                    break;

            case 3: system("cls");
                    anadido = p.anadirventa();

                    if(anadido==true)
                        cout << "Resumen de ventas actualizado correctamente.";
                    else
                        cout << "Error al actualizar el resumen.";

                    system("pause");
                    break;

            case 4: system("cls");
                    p.actualizarresumen();
                    system("pause");
                    break;

            case 5: system("cls");

                    cout << "Introduzca el tipo de producto del que quiere obtener las estadisticas: "; cin >> tipo;
                    cout << endl << "Introduzca el a" << nsp << "o de inicio y el a" << nsp << "o de fin del intervalo de tiempo en el que quiere que esten comprendidos los productos cuyas estadisticas se van a mostrar:";
                    cin >> annoini >> annofin;

                    p.obtenerestadisticas(tipo, annoini, annofin);
                    system("pause");
                    break;

            case 0: break;

            default: cout << "Opcion no valida, introduzca de nuevo.";

        }

    }while(opc!=0);


    return 0;
}
