#include "Peluqueria.h"
#include <fstream>
#include <iostream>
#include <string.h>

using namespace std;

void peluqueria::AbrirPeluqueria(char *nombrefichero)
{
    peluquerof paux;
    cliente caux;
    peluquero p;
    int np, nc;
    ifstream f;
    f.open(nombrefichero, ios::binary);

    if(f)//Si el fichero se ha abierto sin problemas procede
    {
        //Lee el fichero de la forma indicada en el enunciado de la pr�ctica
        f.read((char*)&np, sizeof(int));//Lee el n�mero de peluqueros
        for(int i=0; i<np; i++)
        {
            //Lee los peluqueros en estructuras de tipo peluquerof para cargarlos en estructuras de tipo peluquero con cola vacia
            f.read((char*)&paux, sizeof(peluquerof));
            strcpy(p.Apellidos, paux.Apellidos);
            strcpy(p.Nombre, paux.Nombre);
            p.Codigo=paux.Codigo;
            p.TipoServicio=paux.TipoServicio;
            L.insertar(p.Codigo-1, p);//Se introduce en la posici�n que indica su c�digo
        }
        f.read((char*)&nc, sizeof(int));//Lee el n�mero de clientes
        for(int k=0; k<nc; k++)
        {

            f.read((char*)&caux, sizeof(cliente));
            bool agnadido=false;
            int codigo = Colaminima(caux.TipoServicio, np);//Leer descripci�n del m�todo
            L.observar(codigo-1).Col.encolar(caux);//Encola al cliente en la cola del peluquero con la menor cola de ese servicio

        }
        if(!f.eof())
            cout << "Error en la lectura del fichero.";
        else
            cout << "Peluquer�a cargada correctamente.";

    }
    else
        cout << "Error en la apertura del archivo.";

    f.clear();
    f.close();

}

void peluqueria::IncorporarPeluquero(peluquero t)//Pasarle clientes
{
    int np = L.longitud(), nc=0, ncp;
    cliente caux;

    for(int i = 0; i<np; i++)
    {
        if(L.observar(i).TipoServicio==t.TipoServicio)
            nc += L.observar(i).Col.longitud();
    }
    L.insertar(t.Codigo-1, t);

    ncp = nc/np;

    for(int j=0; j<np; j++)
    {
        if(L.observar(j).TipoServicio==t.TipoServicio)
            while(L.observar(j).Col.longitud()>ncp)
            {
                caux = L.observar(j).Col.primero();
                L.observar(j).Col.desencolar();
                L.observar(t.Codigo-1).Col.encolar(caux);
            }
    }
}

bool peluqueria::RetirarPeluquero(int codigo)
{
    int nc, npt=L.longitud();
    //Determinamos el n�mero de peluqueros que existen del servicio que posee el peluquero que se pasa por par�metro
    int tiposervicio = L.observar(codigo-1).TipoServicio;
    int minimo=Colaminima(tiposervicio, npt);
    for(int i=0; i<L.longitud(); i++)
    {
        if(L.observar(i).TipoServicio==tiposervicio)
            np++;
    }
    if(np>1)
    {
        //Si existe m�s de un peluquero de ese servicio se puede retirar
        nc=L.observar(codigo-1).Col.longitud();
        for(int j=0; j<npt; j++)
        {
            if(L.observar(j).TipoServicio==tiposervicio && L.observar(j).Col.longitud()<minimo)//La cola de este peluquero es la menor y es del servicio pedido

        }
    }

}

bool peluqueria::EliminarCliente(cadena Nombre, cadena Apelllidos)
{

}

bool peluqueria::IncorporarCliente(cliente cli)
{

}

void peluqueria::Mostrar()
{

}

bool peluqueria::AtenderCliente(int CodigoPeluquero)
{

}

void peluqueria::VolcarPeluqueria(char *nombrefichero)
{

}

//Devuelve el c�digo del peluquero con la cola m�nima perteneciente al par�metro pasado
int peluqueria::Colaminima(int tiposervicio, int np)
{
    int minimo=L.observar(0).Col.longitud();
    int pos;

    for(int i=0; i<np; i++)
    {
        if(L.observar(i).Col.longitud()<minimo)
        {
            minimo=L.observar(i).Col.longitud();
            pos = i;
        }

    }

    return L.observar(pos).Codigo;
}
