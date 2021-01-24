#include "cartelera.h"
#include <iostream>

using namespace std;

Cartelera::Cartelera(): espectaculos()
{
    //Este método debe permitir crear una cartelera vacía
}

void Cartelera::insertaEspectaculo(const string& e)
{
    //Este método debe añadir un espectáculo, pero si dicho espectáculo
    //ya existe, no debe modificarse la info
    espectaculos.insert(PEspectaculos(e, DSalas()));

}

void Cartelera::insertaSala(const string& e, const string& s, const string& c)
{
    //Este método añadirá una sala y su ciudad a un espectáculo
    espectaculos[e].insert(PSalas(s,c));
    //espectaculo[e] --> Nos permite acceder al diccionario
    //de salas que está asociado a la clave e

}

void Cartelera::eliminaEspectaculo(const string& e)
{
   //Este método deberá eliminar un dato de un diccionario
   espectaculos.erase(e);
   //Elimina el diccionario asociado a e que contiene sus salas
   //pero no debería eliminar las salas en si mismas (CONSULTAR)
}

void Cartelera::eliminaSala(const string& e, const string& s)
{
    //Este método deberá eliminar una sala de un espectáculo dado
    DEspectaculos::iterator it = espectaculos.find(e); //Creamos un iterados para movernos por el diccionario
    if(it!= espectaculos.end()) //Si el iterador no llega al final, entonces hemos encontrado el espectáculo y podemos eliminar la sala
        it->second.erase(s); //Accedemos al segundo valor del par y borramos s
}

void Cartelera::listaEspectaculos()
{
    if(espectaculos.empty())
        cout <<"No hay ningun espectáculo."<<endl;
    else
    {
        cout <<"Lista de espectaculos:"<< endl;
        //Bucle que recorre desde el principio del diccionario hasta el final del diccionario
        for(DEspectaculos::iterator it = espectaculos.begin(); it!=espectaculos.end(); it++)
            cout << it->first << endl; //Muestra el primer campo del iterador (cadena de carácteres con el nombre del espectáculo)
    }
}

void Cartelera::listaSalas(const string& e)
{
    //Sacamos un iterador para el espectáculo que estamos buscando
    DEspectaculos::iterator it1 = espectaculos.find(e);
    if(it1 == espectaculos.end()) //Si llegamos al final, entonces el espectáculo no existe
        cout << "No existe el espectaculo" << endl;
    else
    {
        if(it1->second.empty()) //Si el diccionario de salas del espectáculo está vacío, no tiene salas
            cout << "No hay ninguna sala para el espectaculo " << e << "." << endl;
        else
        {
            cout << "Lista de salas para el espectaculo" << endl;
            //Recorremos desde el principio hasta el final del diccionario de salas para mostrar cada sala del espectáculo
            for(DSalas::iterator it2=it1->second.begin(); it2!=it1->second.end(); it2++)
                cout << it2->first << " - " << it2->second << endl;
        }

    }
}

