#include <iostream>
#include <conio.h>
#include <cstdlib>
#include <ctype.h>
#define MAX_CUENTAS 100 //Número de Cuentas

using namespace std;

typedef char Cadena[50]; //Tipo de datos Cadena

class Cuenta //Contiene los datos de una cuenta bancaria
{
    float Saldo; //Saldo de la cuenta
    int NoCuenta; //Número de la cuenta
    bool Bloqueada; //true si está bloqueada
public:
    Cuenta();
    Cuenta(int pNo, float pSal);
    bool ActualizarSaldo(int pSal);
    void ActualizarBloqueo(bool pBloq);
    float DameSaldo();
    int DameNoCuenta();
    bool EstaBloqueada();
};

Cuenta::Cuenta()
{
    Saldo = 0;
    NoCuenta = 0;
    Bloqueada = false;
}

Cuenta::Cuenta(int pNo, float pSal)
{
    Saldo = pSal;
    NoCuenta = pNo;
    Bloqueada = false;
}

bool Cuenta::ActualizarSaldo(int pSal)
{
    bool Actualizado = false;

    if(Bloqueada == false)
    {
        Saldo = pSal;
        Actualizado = true;
    }

    return Actualizado;
}

void Cuenta::ActualizarBloqueo(bool pBloq)
{
    Bloqueada = pBloq;
}

float Cuenta::DameSaldo()
{
    return Saldo;
}

int Cuenta::DameNoCuenta()
{
    return NoCuenta;
}

bool Cuenta::EstaBloqueada()
{
    return Bloqueada;
}

int BuscarCuenta(Cuenta Ctas[MAX_CUENTAS], int NCuentas, int NoCuenta)
{
    bool Encontrado = false;
    int i = 0;

    while(i < NCuentas && Encontrado == false)
    {
        if (Ctas[i].DameNoCuenta() == NoCuenta)
            Encontrado = true;
        else
            i++;
    }

        if(Encontrado == false)
            i = -1;

        return i;
}


int MenuCuentas()
{
    char a = 164;
    char o = 162;
    int respuesta;

    cout << "\t<------------------------------------------------>" << endl;
    cout << "\t|                                                |" << endl;
    cout << "\t|       1 A" << a << "adir una cuenta a un cliente         |" << endl;
    cout << "\t|       2 Mostrar las cuentas del cliente        |" << endl;
    cout << "\t|       3 Borrar una cuenta del cliente          |" << endl;
    cout << "\t|       4 Modificar saldo de una cuenta          |" << endl;
    cout << "\t|       5 Modificar estado de una cuenta         |" << endl;
    cout << "\t|       6 Salir                                  |" << endl;
    cout << "\t|       Elige opci" << o << "n:                            |" << endl;
    cout << "\t|                                                |" << endl;
    cout << "\t<------------------------------------------------>" << endl;

    respuesta = getch();

    return respuesta;
};

int main()
{
    Cuenta DatosCuentas[MAX_CUENTAS];
    int nCuentas = 0;
    int NoCuenta;
    int respuesta;
    int respuestaBuscador;
    int saldo;
    bool Actualizado;
    char RespuestaBloq;
    bool Bloqueo;
    int j=0;


    do
    {
        respuesta = MenuCuentas();

        system("CLS");

        switch(respuesta)
        {
            case '1': if(nCuentas < MAX_CUENTAS)
                    {
                        cout << "Introduzca Numero de la cuenta: ";
                        cin >> NoCuenta;
                        respuestaBuscador = BuscarCuenta(DatosCuentas, nCuentas, NoCuenta);

                        if(respuestaBuscador == -1)
                        {
                            nCuentas++;
                            cout << "Introduzca el saldo de la cuenta: "; cin >> saldo;
                            DatosCuentas[nCuentas-1] = Cuenta(NoCuenta, saldo);
                        }
                        else
                        {
                            cout << "La cuenta introducida ya existe." << endl;
                        }
                    }
                    else
                    {
                        cout << "No queda espacio para introducir mas cuentas en el registro." << endl;
                    }
                    break;
            case '2': if(nCuentas > 0)
                    {
                        for(int i=0; i<nCuentas; i++)
                        {
                            cout << "Cuenta Numero " << DatosCuentas[i].DameNoCuenta() << "\n";
                            cout << "Saldo: " << DatosCuentas[i].DameSaldo() << "\n";
                            if(DatosCuentas[i].EstaBloqueada()==true)
                                cout << "Su cuenta permanece bloqueada.";
                            else
                                cout << "Su cuenta permanece activa.";

                            cout << endl << endl;
                        }
                    }
                    break;
            case '3': cout << "Introduzca el numero de la cuenta que desea eliminar: "; cin >> NoCuenta;

                      respuestaBuscador = BuscarCuenta(DatosCuentas, nCuentas, NoCuenta);

                      if(respuestaBuscador == -1)
                      {
                          cout << "La cuenta que desea eliminar no existe." << endl;
                      }
                      else
                      {
                          nCuentas--;
                          for(j = respuestaBuscador; j<nCuentas; j++)
                          {
                              DatosCuentas[j] = DatosCuentas[j+1];
                          }
                          cout << "Se ha eliminado la cuenta sin problemas." << endl;
                      }
                    break;
            case '4': cout << "Introduzca el numero de la cuenta que desea actualizar: ";
                      cin >> NoCuenta;
                      respuestaBuscador = BuscarCuenta(DatosCuentas, nCuentas, NoCuenta);
                      if(respuestaBuscador == -1)
                      {
                         cout << "La cuenta que desea actualizar no existe.";
                      }
                      else
                      {
                         cout << "Introduzca el nuevo saldo: ";
                         cin >> saldo;
                         Actualizado = DatosCuentas[respuestaBuscador].ActualizarSaldo(saldo);
                         if(Actualizado == false)
                            cout << "No se puede actualizar, la cuenta permanece bloqueada.";
                      }
                    break;
            case '5': cout << "Introduzca el numero de la cuenta que desea actualizar: ";
                      cin >> NoCuenta;
                      respuestaBuscador = BuscarCuenta(DatosCuentas, nCuentas, NoCuenta);
                      if(respuestaBuscador == -1)
                      {
                          cout << "La cuenta que desea actualizar no existe.";
                      }
                      else
                      {
                          do
                          {
                            cout << "Pulse S para bloquear la cuenta o N para desbloquear la cuenta: ";
                            RespuestaBloq = toupper(getch());

                          }while(RespuestaBloq != 'S' && RespuestaBloq != 'N');

                           if(RespuestaBloq == 'S')
                                Bloqueo = true;
                            else
                                Bloqueo = false;

                            DatosCuentas[respuestaBuscador].ActualizarBloqueo(Bloqueo);

                            cout << "La cuenta ha sido actualizada." << endl;
                      }
                      break;

        };
    }while(respuesta != '6');


        return 0;
}

