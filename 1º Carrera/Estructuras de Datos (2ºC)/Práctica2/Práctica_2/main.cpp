#include <iostream>
#include <locale.h>
#include <stdlib.h>
#include <windows.h>
#include "Peluqueria.h"

using namespace std;

typedef char cadena[50];

int main()
{
    setlocale(LC_ALL, "");//Escribir ñ y tildes
    SetConsoleTitle("Peluqueria Corte 2.0 Huelva");
    peluqueria p;

    char opc;

    do
    {
        system("cls");
        //Menú:------------------------------------------------------------------------------
        cout << "Peluquería Corte 2.0 Huelva" << endl <<
        "\t\t\t\t\t\tÁlvaro Esteban Muñoz" << endl <<
        "------------------------------------------------------------------------" << endl <<
        "1 - Leer fichero (rescatar copia)" << endl <<
        "2 - Insertar peluquero" << endl <<
        "3 - Insertar cliente" << endl <<
        "4 - Retirar peluquero" << endl <<
        "5 - Atender cliente" << endl <<
        "6 - Mostrar peluquería" << endl <<
        "7 - Eliminar un cliente" << endl <<
        "8 - Volcar a fichero (crear copia)" << endl <<
        "9 - Salir" << endl << endl << endl <<
        "Pulse la opción deseada : ";
        //-----------------------------------------------------------------------------------

        cin >> opc;

        switch(opc)
        {
        case '1': p.AbrirPeluqueria((char*)"inicial.dat");
                break;
        case '2':
        {
            cadena Apellido1, Apellido2;
            peluquero paux;
            cout << "Nuevo Peluquero" << endl <<
            "------------------------------------------------------------------------" << endl <<
            "Nombre: "; cin >> paux.Nombre; cout << endl <<
            "Apellidos: "; cin >> Apellido1 >> Apellido2; cout << endl;
            strcpy(Apellido1, strcat(Apellido1, " "));
            strcpy(paux.Apellidos, strcat(Apellido1, Apellido2));
            cout << "Código: "; cin >> paux.Codigo; cout << endl <<
            "Tipo de servicio: "; cin >> paux.TipoServicio; cout << endl;
            p.IncorporarPeluquero(paux);
        }break;
        case '3':
        {
            cadena Apellido1, Apellido2;
            cliente claux;
            cout << "Nuevo Cliente" << endl <<
            "------------------------------------------------------------------------" << endl <<
            "Nombre: "; cin >> claux.Nombre; cout << endl <<
            "Apellidos: "; cin >> Apellido1 >> Apellido2; cout << endl;
            strcpy(Apellido1, strcat(Apellido1, " "));
            strcpy(claux.Apellidos, strcat(Apellido1, Apellido2));
            cout << "Edad: "; cin >> claux.Edad; cout << endl <<
            "Tipo de servicio: "; cin >> claux.TipoServicio; cout << endl <<
            "Hora de llegada (minutos): "; cin >> claux.HoraLlegada; cout << endl;
            p.IncorporarCliente(claux);
        }break;
        case '4':
        {
            int codigo;
            bool retirado;
            cout << "Introduzca el código del peluquero que desea retirar: ";
            cin >> codigo;
            retirado = p.RetirarPeluquero(codigo);
            if(retirado==true)
                cout << "El peluquero ha sido retirado perfectamente." << endl;
            else
                cout << "El peluquero no puede ser retirado debido a que es el último de su servicio." << endl;

            system("pause");
        }break;
        case '5':
        {
            int codigo;
            bool atendido;
            cout << "Introduzca el código del peluquero cuyo cliente desea atender: "; cin >> codigo;
            atendido = p.AtenderCliente(codigo);
            if(atendido==true)
                cout << "El cliente ha sido atendido correctamente." << endl;
            else
                cout << "Hubo un error al atender al cliente." << endl;

            system("pause");
        }break;
        case '6':
        {
            p.Mostrar();
            system("pause");
        }break;
        case '7':
        {
            cadena Nombre, Apellido, Apellidoaux;
            bool Eliminado;
            cout << "Introduzca el nombre y apellidos del cliente que desea eliminar..." << endl <<
            "Nombre: "; cin >> Nombre; cout << endl <<
            "Apellidos: "; cin >> Apellido >> Apellidoaux; cout << endl;
            strcpy(Apellido, strcat(Apellido, " "));
            strcpy(Apellido, strcat(Apellido, Apellidoaux));
            Eliminado = p.EliminarCliente(Nombre, Apellido);

            if(Eliminado==true)
                cout << "El cliente fue eliminado sin problemas" << endl;
            else
                cout << "El cliente no pudo ser eliminado" << endl;

            system("pause");
        }
                break;
        case '8':
        {
            cadena NombreFich;
            cout << "Introduzca el nombre que desea darle al nuevo fichero: "; cin >> NombreFich;
            p.VolcarPeluqueria(NombreFich);
            system("pause");
        }break;
        case '9': cout << "Nos vemos :D";
                break;
        default : cout << "Opción no valida, introduzca de nuevo :c";
                break;
        }
    }while(opc!='9');

    return 0;
}
