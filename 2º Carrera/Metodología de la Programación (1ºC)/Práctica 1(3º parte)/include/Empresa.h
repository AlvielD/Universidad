#ifndef EMPRESA_H
#define EMPRESA_H

#include "Fecha.h" //definicion clase Fecha
#include "Cliente.h" // definicion clase Cliente
#include "Contrato.h" // definicion de la clase Contrato
#include "ContratoTP.h" // definicion de la clase ContratoTP
#include "ContratoMovil.h" // definicion de la clase ContratoMovil
#include <typeinfo> // para poder usar typeid
#include <stdlib.h> // para poder usar system("CLS")


class Empresa
{
    public:
        Empresa();
        virtual ~Empresa();

        void cargarDatos();
        void ver();
        int nContratosTP();
        void crearContrato();
        bool cancelarContrato(int idContrato);
        bool bajaCliente(long int dni);
        void descuento(int dispercen);

    protected:

    private:
        Cliente *Clientes[100];
        int nCli;
        Contrato **Contratos;
        int nCont;
        int maxCont;

};

#endif // EMPRESA_H
