#ifndef CONTRATOTP_H
#define CONTRATOTP_H

#include <Contrato.h>


class ContratoTP : public Contrato
{
    public:
        ContratoTP();
        ContratoTP(int dni, Fecha f, int mins);
        ContratoTP(const ContratoTP &c);
        virtual ~ContratoTP();

        void setMinutosHablados(int val) {minutosHablados=val;}
        int getMinutosHablados() const {return minutosHablados;}
        static int getLimiteMinutos() {return LimiteMinutos;}
        static void setLimiteMinutos(int val) { LimiteMinutos=val; }
        static float getPrecio() {return Precio;}
        static void setPrecio(int val) { Precio=val; }
        static void setTarifaPlana(int mins, int prec);

        void ver();
        float factura();

    protected:

    private:
        int minutosHablados;
        static int LimiteMinutos;
        static float Precio;
};

ostream& operator<<(ostream& o, ContratoTP &c);



#endif // CONTRATOTP_H
