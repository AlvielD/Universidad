#ifndef LISTA_H_INCLUDED
#define LISTA_H_INCLUDED

#include "Cola.h"
#include <iostream>
#define INCREMENTO 4

using namespace std;


struct peluquero
{
    int Codigo;
    cadena Apellidos;
    cadena Nombre;
    int TipoServicio;
    cola Col;
};

class lista
{
    peluquero *elementos; // elementos de la lista
    int n;// nº de elementos que tiene la lista
    int Tama; // tamaño de la tabla en cada momento
public:
    lista(); // constructor de la clase
    ~lista();// destructor de la clase
    lista(peluquero &e);
    void vaciar();
    bool esvacia();
    int longitud();
    //void anadirIzq(peluquero e); No necesario implementar
    //void anadirDch(peluquero e); No necesario implementar
    //void eliminarIzq(); No necesario implementar
    //void eliminarDch(); No necesario implementar
    //peluquero observarIzq(); No necesario implementar
    //peluquero observarDch(); No necesario implementar
    //void concatenar(lista l); No necesario implementar
    bool pertenece(peluquero &e);
    void insertar(int i, peluquero &e);
    void eliminar(int i);
    void modificar(int i, peluquero &e);
    peluquero &observar(int i);
    int posicion(peluquero &e);
};

void VolcarPeluquero(peluquero &aux, peluquero &original);

#endif // LISTA_H_INCLUDED
