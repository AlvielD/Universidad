#ifndef PELUQUERIA_H_INCLUDED
#define PELUQUERIA_H_INCLUDED

#include "Lista.h"
#include <fstream>

struct peluquerof
{
    int Codigo;
    cadena Apellidos;
    cadena Nombre;
    int TipoServicio;
};

class peluqueria
{
    lista L; //lista de los peluqueros que están atendiendo a los clientes
public:
    void AbrirPeluqueria(char *nombrefichero);
    void IncorporarPeluquero(peluquero t);
    bool RetirarPeluquero(int codigo);
    bool EliminarCliente(cadena Nombre, cadena Apelllidos);
    bool IncorporarCliente(cliente cli);
    void Mostrar();
    bool AtenderCliente(int CodigoPeluquero);
    void VolcarPeluqueria(char *nombrefichero);
    int Colaminima(int tiposervicio, int np);
};

#endif // PELUQUERIA_H_INCLUDED
