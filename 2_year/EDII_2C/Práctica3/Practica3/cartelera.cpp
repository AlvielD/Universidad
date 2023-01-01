#include "cartelera.h"
#include <iostream>

using namespace std;

Cartelera::Cartelera(): espectaculos()
{
    //Este m�todo debe permitir crear una cartelera vac�a
}

void Cartelera::insertaEspectaculo(const string& e)
{
    //Este m�todo debe a�adir un espect�culo, pero si dicho espect�culo
    //ya existe, no debe modificarse la info
    espectaculos.insert(PEspectaculos(e, DSalas()));

}

void Cartelera::insertaSala(const string& e, const string& s, const string& c)
{
    //Este m�todo a�adir� una sala y su ciudad a un espect�culo
    espectaculos[e].insert(PSalas(s,c));
    //espectaculo[e] --> Nos permite acceder al diccionario
    //de salas que est� asociado a la clave e

}

void Cartelera::eliminaEspectaculo(const string& e)
{
   //Este m�todo deber� eliminar un dato de un diccionario
   espectaculos.erase(e);
   //Elimina el diccionario asociado a e que contiene sus salas
   //pero no deber�a eliminar las salas en si mismas (CONSULTAR)
}

void Cartelera::eliminaSala(const string& e, const string& s)
{
    //Este m�todo deber� eliminar una sala de un espect�culo dado
    DEspectaculos::iterator it = espectaculos.find(e); //Creamos un iterados para movernos por el diccionario
    if(it!= espectaculos.end()) //Si el iterador no llega al final, entonces hemos encontrado el espect�culo y podemos eliminar la sala
        it->second.erase(s); //Accedemos al segundo valor del par y borramos s
}

void Cartelera::listaEspectaculos()
{
    if(espectaculos.empty())
        cout <<"No hay ningun espect�culo."<<endl;
    else
    {
        cout <<"Lista de espectaculos:"<< endl;
        //Bucle que recorre desde el principio del diccionario hasta el final del diccionario
        for(DEspectaculos::iterator it = espectaculos.begin(); it!=espectaculos.end(); it++)
            cout << it->first << endl; //Muestra el primer campo del iterador (cadena de car�cteres con el nombre del espect�culo)
    }
}

void Cartelera::listaSalas(const string& e)
{
    //Sacamos un iterador para el espect�culo que estamos buscando
    DEspectaculos::iterator it1 = espectaculos.find(e);
    if(it1 == espectaculos.end()) //Si llegamos al final, entonces el espect�culo no existe
        cout << "No existe el espectaculo" << endl;
    else
    {
        if(it1->second.empty()) //Si el diccionario de salas del espect�culo est� vac�o, no tiene salas
            cout << "No hay ninguna sala para el espectaculo " << e << "." << endl;
        else
        {
            cout << "Lista de salas para el espectaculo" << endl;
            //Recorremos desde el principio hasta el final del diccionario de salas para mostrar cada sala del espect�culo
            for(DSalas::iterator it2=it1->second.begin(); it2!=it1->second.end(); it2++)
                cout << it2->first << " - " << it2->second << endl;
        }

    }
}

