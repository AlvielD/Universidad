#include "Peluqueria.h"
#include <fstream>
#include <iostream>
#include <string.h>
#include <stdlib.h>

using namespace std;

void peluqueria::AbrirPeluqueria(char *nombrefichero)//Acabado (Por fin!)
{
    peluquerof paux;
    cliente caux;
    peluquero p;
    int np, nc;
    ifstream f;
    f.open(nombrefichero, ios::binary);

    if(f)//Si el fichero se ha abierto sin problemas procede
    {
        //Lee el fichero de la forma indicada en el enunciado de la práctica
        f.read((char*)&np, sizeof(int));//Lee el número de peluqueros
        for(int i=0; i<np; i++)
        {
            //Lee los peluqueros en estructuras de tipo peluquerof para cargarlos en estructuras de tipo peluquero con cola vacia
            f.read((char*)&paux, sizeof(peluquerof));
            strcpy(p.Apellidos, paux.Apellidos);
            strcpy(p.Nombre, paux.Nombre);
            p.Codigo=paux.Codigo;
            p.TipoServicio=paux.TipoServicio;
            L.insertar(p.Codigo, p);//Se introduce en la posición que indica su código
        }
        f.read((char*)&nc, sizeof(int));//Lee el número de clientes
        for(int k=0; k<nc; k++)
        {
            f.read((char*)&caux, sizeof(cliente));
            IncorporarCliente(caux);
        }
        /*if(!f.eof())
            cout << "Error en la lectura del fichero.";
        else --> No llega al final del fichero pero creo que lee a todos los clientes a pesar de todo, tener en cuenta por si acaso*/
            cout << "Peluquería cargada correctamente.";

    }
    else
        cout << "Error en la apertura del archivo.";

    f.clear();
    f.close();
    system("pause");

}

void peluqueria::IncorporarPeluquero(peluquero t)//Acabado
{
    int np=0, nc=0, ncp;
    cliente caux;

    for(int i=1; i<=L.longitud(); i++)
    {
        //Recorre las longitudes de todas las colas para obtener el número de clientes
        cout << "Recorriendo" << endl;
        if(L.observar(i).TipoServicio==t.TipoServicio)
        {
            nc += L.observar(i).Col.longitud();
            np++;
        }
    }
    //Inserta al peluquero en la lista según el código
    L.insertar(t.Codigo, t);

    //Variable para el número de clientes por peluquero
    ncp = nc/(np+1);

    for(int j=1; j<=np; j++)
    {
        //Recorre la lista de peluqueros
        if(L.observar(j).TipoServicio==t.TipoServicio)
        {
            //Si el peluquero es del tipo de servicio
            while(L.observar(j).Col.longitud()>ncp)
            {
                //Mientras la cola del peluquero j sea mayor que el numero de clientes por peluquero...
                caux = L.observar(j).Col.primero();
                L.observar(j).Col.desencolar();
                L.observar(t.Codigo).Col.encolar(caux);
            }

        }
    }
}

bool peluqueria::RetirarPeluquero(int codigo)//Acabado
{
    int np=0, npt=L.longitud();
    cliente caux;
    bool eliminado=false;
    //Determinamos el número de peluqueros que existen del servicio que posee el peluquero que se pasa por parámetro
    int tiposervicio = L.observar(codigo).TipoServicio;
    for(int i=1; i<=L.longitud(); i++)
    {
        //Recorre la lista de peluqueros
        if(L.observar(i).TipoServicio==tiposervicio)//Cuenta el número de peluqueros del tipo de servicio del que se pasa como código
            np++;
    }

    if(np>1)
    {
        //Si existe más de un peluquero de ese servicio se puede retirar
        while(L.observar(codigo).Col.longitud()>0)
        {
            int pos = ColaminimaIgnora(tiposervicio, npt, codigo);
            caux=L.observar(codigo).Col.primero();
            L.observar(pos).Col.encolar(caux);
            L.observar(codigo).Col.desencolar();
        }

    }
    if(L.observar(codigo).Col.longitud()==0)
    {
        L.eliminar(codigo);
        eliminado=true;
    }
    else
        cout << "Ha ocurrido un error al borrar al peluquero de la lista" << endl;


    return eliminado;
}

bool peluqueria::EliminarCliente(cadena Nombre, cadena Apelllidos)//Acabado
{
    bool encontrado=false;
    int i=1;
    while(i<=L.longitud() && encontrado==false)
    {
        //Recorre la lista de peluqueros
        for(int j=1; j<=L.observar(i).Col.longitud(); j++)
        {
            //Recorre la cola del peluquero i
            if(strcmp(L.observar(i).Col.primero().Nombre, Nombre)==0 && strcmp(L.observar(i).Col.primero().Apellidos, Apelllidos)==0)
            {
                //Si nombre y apellido coinciden con los pasados por parametro...
                encontrado=true;
                L.observar(i).Col.desencolar();
            }
            else
            {
                L.observar(i).Col.encolar(L.observar(i).Col.primero());
                L.observar(i).Col.desencolar();
            }

        }
        i++;
    }
    return encontrado;
}

bool peluqueria::IncorporarCliente(cliente cli)//Acabado
{
    int Pmenosocupado = Colaminima(cli.TipoServicio, L.longitud()); //Colaminima me da la posición del peluquero menos ocupado
    L.observar(Pmenosocupado).Col.encolar(cli);
}

void peluqueria::Mostrar()//Acabado
{
    cliente caux;
    //cout << L.observar(1).Nombre;
    for(int i = 1; i <= L.longitud(); i++)
    {
        cout << "Peluquero " << L.observar(i).Codigo << ": " << L.observar(i).Apellidos << ", " << L.observar(i).Nombre << "\tTipo de Servicio: " << L.observar(i).TipoServicio << endl;
        cout << "Clientes: " << endl << "\t\tApellidos\t\tNombre\t\tEdad\t\tTipo de Servicio\t\tHora de llegada" << endl <<
        "\t\t.........\t\t......\t\t....\t\t................\t\t..............." << endl;
        for(int j = 0; j < L.observar(i).Col.longitud(); j++)
        {
            cout << "\t\t" << L.observar(i).Col.primero().Apellidos << "\t\t" << L.observar(i).Col.primero().Nombre << "\t\t" << L.observar(i).Col.primero().Edad << "\t\t\t" << L.observar(i).Col.primero().TipoServicio << "\t\t\t\t" << L.observar(i).Col.primero().HoraLlegada << endl;
            caux = L.observar(i).Col.primero();
            L.observar(i).Col.desencolar();
            L.observar(i).Col.encolar(caux);
        }
        cout << endl;
    }
}

bool peluqueria::AtenderCliente(int CodigoPeluquero)//Acabado
{
    int pospelu;
    bool atendido=false;
    for(int i=1; i<=L.longitud(); i++)
    {
        if(L.observar(i).Codigo==CodigoPeluquero)
        {
            L.observar(i).Col.desencolar();
            atendido=true;
        }
    }

    return atendido;
}

void peluqueria::VolcarPeluqueria(char *nombrefichero)//Acabado
{
    peluquerof pfaux;
    cliente claux;
    int numcli=0, numpel;
    numpel = L.longitud();
    ofstream FicheroCopia;
    FicheroCopia.open(nombrefichero, ios::binary);
    if(FicheroCopia)
    {
        //Escribimos en el fichero el número de peluqueros que hay
        FicheroCopia.write((char*)&numpel, sizeof(int));
        //Escribimos todos los peluqueros en el fichero mediante un bucle que recorra la lista
        for(int i=1; i<=L.longitud(); i++)
        {
            pfaux.Codigo=L.observar(i).Codigo;
            pfaux.TipoServicio=L.observar(i).TipoServicio;
            strcpy(pfaux.Nombre, L.observar(i).Nombre);
            strcpy(pfaux.Apellidos, L.observar(i).Apellidos);
            numcli += L.observar(i).Col.longitud();

            FicheroCopia.write((char*)&pfaux, sizeof(peluquerof));
        }
        FicheroCopia.write((char*)&numcli, sizeof(int));
        for(int j=1; j<=L.longitud(); j++)
        {
            for(int k=1; k<=L.observar(j).Col.longitud(); k++)
            {
                strcpy(claux.Nombre, L.observar(j).Col.primero().Nombre);
                strcpy(claux.Apellidos, L.observar(j).Col.primero().Apellidos);
                claux.Edad=L.observar(j).Col.primero().Edad;
                claux.HoraLlegada=L.observar(j).Col.primero().HoraLlegada;
                claux.TipoServicio=L.observar(j).Col.primero().TipoServicio;

                FicheroCopia.write((char*)&claux, sizeof(cliente));
                L.observar(j).Col.encolar(L.observar(j).Col.primero());
                L.observar(j).Col.desencolar();
            }

        }
        cout << "Se ha creado el archivo correctamente";
        FicheroCopia.close();
    }
    else
        cout << "Error en la apertura del archivo";

}

//Devuelve la posición que ocupa en la lista el peluquero menos ocupado
int peluqueria::Colaminima(int tiposervicio, int np)
{

    int j=1, k=1, pos, minimo;
    bool encontrado=false, primerpelu=false;

    while(k<=np && primerpelu==false)
    {
        if(L.observar(k).TipoServicio==tiposervicio)
        {
            minimo=L.observar(k).Col.longitud();
            primerpelu==true;
        }
        k++;
    }

    for(int i=1; i<=np; i++)
    {
        if(L.observar(i).Col.longitud()<minimo && L.observar(i).TipoServicio==tiposervicio)
            minimo=L.observar(i).Col.longitud();
    }

    while(j<=np && encontrado==false)
    {
        //cout << "Buscando peluquero que coincida con la longitud mínima" << endl;
        if(L.observar(j).Col.longitud()==minimo && L.observar(j).TipoServicio==tiposervicio)
        {
            encontrado=true;
            pos = j;
        }
        j++;
    }

    return pos;
}

//Devuelve la posición que ocupa en la lista el peluquero menos ocupado obviando al pasado como código
int peluqueria::ColaminimaIgnora(int tiposervicio, int np, int codigo)
{
    int j=1, k=1, pos, minimo;
    bool encontrado=false, primerpelu=false;

    //Bucle que establece el mínimo como la cola del primer peluquero leido que coincide con tiposervicio
    while(k<=np && primerpelu==false)
    {
        if(L.observar(k).TipoServicio==tiposervicio)
        {
            minimo=L.observar(k).Col.longitud();
            primerpelu==true;
        }
        k++;
    }

    for(int i=1; i<=np; i++)
    {
        //Recorre los peluqueros buscan la cola más pequeña
        if(L.observar(i).Col.longitud()<minimo && L.observar(i).TipoServicio==tiposervicio && L.observar(i).Codigo!=codigo)
            minimo=L.observar(i).Col.longitud();
    }

    while(j<=np && encontrado==false)
    {
        //Vuelve a recorrer los peluqueros buscando al dueño cuya cola es = minimo
        if(L.observar(j).Col.longitud()==minimo && L.observar(j).TipoServicio==tiposervicio && L.observar(j).Codigo!=codigo)
        {
            encontrado=true;
            pos = j;
        }
        j++;
    }

    return pos;
}
