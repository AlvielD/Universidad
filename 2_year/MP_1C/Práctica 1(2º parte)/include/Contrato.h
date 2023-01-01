#ifndef CONTRATO_H
#define CONTRATO_H

#include "Fecha.h"
#include <string.h>


class Contrato
{
    public:
        Contrato();
        Contrato(long int dni, Fecha f);
        Contrato(const Contrato &c);
        virtual ~Contrato();

        static int GetContratos() { return Contratos; }
        static void SetContratos(int val) { Contratos = val; }

        int getIdContrato() const { return idContrato; }
        void SetidContrato(int val) { idContrato = val; }
        long int getDniContrato() const { return dniContrato; }
        void setDniContrato(long int val) { dniContrato = val; }
        Fecha getFechaContrato() const { return fechaContrato; }
        void setFechaContrato(Fecha val) { fechaContrato = val; }

        virtual void ver();

    protected:

    private:
        int idContrato;
        static int Contratos;
        long int dniContrato;
        Fecha fechaContrato;
};

ostream& operator<<(ostream& o, Contrato &c);

#endif // CONTRATO_H
