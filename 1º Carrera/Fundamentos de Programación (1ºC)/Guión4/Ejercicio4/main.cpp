#include <iostream>
#include <string.h>
#include <conio.h>
#include <cstdlib>
#include <ctype.h>
#define MAX_CUENTAS 10 //Número de Cuentas
#define MAX_CLIENTES 100 //Número de clientes

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

class Cliente
{
    Cadena Nombre; //Nombre y dirección
    Cadena Direccion;
    Cuenta Cuentas[MAX_CUENTAS]; //cuentas corrientes
    int NoCuentas; //Nº de cuentas abiertas
public:
    Cliente();
    void ActualizarCliente(Cadena pNomb, Cadena pDir);
    void DameNombre(Cadena pNom);
    void DameDireccion(Cadena pDir);
    int BuscarCuenta(int pNoCuenta);
    bool CrearCuenta(Cuenta pCu);
    bool ActualizarCuenta(Cuenta pCu);
    bool BorrarCuenta(int pNoCuenta);
    int DameNoCuentas();
    Cuenta DameCuenta(int pos);
    void Mostrar(char Campo);
};

Cliente::Cliente()
{
    strcpy(Nombre, " ");
    strcpy(Direccion, " ");
    NoCuentas = 0;
}

void Cliente::ActualizarCliente(Cadena pNomb,Cadena pDir)
{
    strcpy(Nombre, pNomb);
    strcpy(Direccion, pDir);
    NoCuentas = 0;
}

void Cliente::DameNombre(Cadena pNom)
{
    strcpy(pNom, Nombre);
}

void Cliente::DameDireccion(Cadena pDir)
{
    strcpy(pDir, Direccion);
}

int Cliente::BuscarCuenta(int pNoCuenta)
{
    bool Encontrado = false;
    int i = 0;

    while(i < NoCuentas && Encontrado == false)
    {
        if (Cuentas[i].DameNoCuenta() == pNoCuenta)
            Encontrado = true;
        else
            i++;
    }

        if(Encontrado == false)
            i = -1;

        return i;
}

bool Cliente::CrearCuenta(Cuenta pCu)
{
    bool Resultado = false;
    int ResultBusc;

    if(NoCuentas < MAX_CUENTAS)
    {
        ResultBusc = BuscarCuenta(pCu.DameNoCuenta());
        if(ResultBusc == -1)
        {
            Cuentas[NoCuentas] = pCu;
            NoCuentas++;
            Resultado = true;
        }
    }

    return Resultado;
}

bool Cliente::ActualizarCuenta(Cuenta pCu)
{
    bool Resultado = false;
    int ResultBusc;

        ResultBusc = BuscarCuenta(pCu.DameNoCuenta());
        if(ResultBusc != -1)
        {
            Cuentas[ResultBusc] = pCu;
            Resultado = true;
        }

    return Resultado;
}

bool Cliente::BorrarCuenta(int pNoCuenta)
{
    bool Resultado;
    int ResultBusc;

    ResultBusc = BuscarCuenta(pNoCuenta);
    if(ResultBusc == -1)
    {
        cout << "Lo siento, pero esa cuenta no existe :c" << endl;
        Resultado = false;
    }
    else
    {
        int j;
        NoCuentas--;
        for(j = ResultBusc; j<=NoCuentas; j++)
        {
            Cuentas[j]=Cuentas[j+1];
        }
        Resultado = true;
        cout << "La cuenta ha sido eliminada satisfactoriamente." << endl;
    }
}

int Cliente::DameNoCuentas()
{
    return NoCuentas;
}

Cuenta Cliente::DameCuenta(int pos)
{
    return Cuentas[pos];
}

void Cliente::Mostrar(char Campo)
{
    Cadena Nom;
    Cadena Dir;
    Cuenta CuentMostrada;

    if(Campo =='s' || Campo == 't')
    {
        Cliente.DameNombre(Nom);
        cout << "Cliente: " << Nom << endl;
        Cliente.DameDireccion(Dir);
        cout << "Direccion: " << Dir << endl;
    }

    if(Campo == 'c' || Campo == 't')
    {
        int i;
        for(i=0; i<NoCuentas; i++)
        {
            cout << "Cuenta " << i+1 << endl;
            CuentMostrada = Cliente.DameCuenta(i);
            cout << "Numero de cuenta: " << CuentMostrada.DameNoCuenta() << endl;
            cout << "Saldo de la cuenta: " << CuentMostrada.DameSaldo() << endl;
            if(CuentMostrada.EstaBloqueada()==true)
                cout << "Esta cuenta permanece bloqueada." << endl;
            else
                cout << "Esta cuenta permanece activa." << endl;
        }
    }
}

int BuscarCliente(Cliente Ctes[MAX_CLIENTES], int NCtes, Cadena Nombre)
{
    int i = 0;
    bool encontrado = false;
    Cadena Nom;
    int pos;

    while(encontrado == false && i < NCtes)
    {
        Ctes[i].DameNombre(Nom);
        if(Nom == Nombre)
            pos = i;
        else
            i++
    }
}

int Menu()
{
    char a = 164;
    char o = 162;
    char u = 163;
    int respuesta;

    cout << "\t<------------------------------------------------>" << endl;
    cout << "\t|                                                |" << endl;
    cout << "\t|       1 A" << a << "adir un cliente                      |" << endl;
    cout << "\t|       2 Actualizar direcci" << o << "n del cliente       |" << endl;
    cout << "\t|       3 Mostrar un cliente                     |" << endl;
    cout << "\t|       4 Mostrar todos los clientes             |" << endl;
    cout << "\t|       5 Submen" << u << " Gesti" << o << "n de Cuentas             |" << endl;
    cout << "\t|       6 Salir                                  |" << endl;
    cout << "\t|       Elige opci" << o << "n:                            |" << endl;
    cout << "\t|                                                |" << endl;
    cout << "\t<------------------------------------------------>" << endl;

    respuesta = getch();

    return respuesta;
}

int main()
{


    Cliente Datos[MAX_CLIENTES];
    int nClientes = 0;
    int respuestamenu, respmenucuenta;
    Cadena nom;
    Cadena NombreComparado;
    Cadena dir;
    int i;
    bool encontrado;
    char campo;

    do
    {
        respuestamenu = Menu();

        switch(respuestamenu)
        {
            case 1: cout << "Introduzca nombre y direccion del cliente a crear\nNombre: "; cin >> nom;
                    cout << "Direccion: "; cin >> dir;
                    Datos[nClientes].ActualizarCliente(nom, dir);
                    break;
            case 2: cout << "Introduzca el nombre del cliente que desea actualizar: "; cin >> NombreComparado;
                    i = 0;
                    while(i<nClientes && encontrado == false)
                    {
                        Datos[i].DameNombre(nom);
                        if(strcmp(NombreComparado, nom)==0)
                        {
                            encontrado = true;
                            cout << "Introduzca la nueva direccion del " << nom << ": ";
                            cin >> dir;
                            Datos[i].ActualizarCliente(nom, dir);
                        }
                        else
                            i++;
                    }

                    if(encontrado==false)
                        cout << "Error 404, el cliente no existe." << endl;

                    return encontrado;
                    break;
            case 3: cout << "Introduzca el nombre del cliente que desea mostrar: ";
                    cin >> NombreComparado;
                    i = 0;
                    while(i<nClientes && encontrado == false)
                    {
                        Datos[i].DameNombre(nom);
                        if(strcmp(NombreComparado, nom)==0)
                        {
                            encontrado = true;
                            cout << "Que informacion desea mostrar(c para nombre y direccion, s para la informacion de todas sus cuentas y t para ambos):  ";
                            cin >> campo;
                            Datos[i].Mostrar(campo);
                        }
                        else
                            i++;
                    }
                    break;
            case 4: campo = 't';
                    for(i = 0; i<nClientes; i++)
                    {
                        Datos[i].Mostrar(campo);
                    }
                    break;
            case 5: cout << "Introduzca el nombre del cliente cuyas cuentas desea gestionar "; cin >> NombreComparado;
                    i = 0;
                    while(i<nClientes && encontrado == false)
                    {
                        Datos[i].DameNombre(nom);
                        if(strcmp(NombreComparado, nom)==0)
                        {
                            encontrado = true;
                            do
                            {
                                respmenucuenta = MenuCuentas();

                                system("CLS");

                                switch(respmenucuenta)
                                {

                                }
                            }

                        }
                        else
                            i++;
                    }
                    break;
        }
    }while(respuestamenu=='6');
    return 0;
}
