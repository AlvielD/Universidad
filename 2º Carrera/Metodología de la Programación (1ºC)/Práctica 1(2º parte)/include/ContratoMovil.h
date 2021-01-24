#ifndef CONTRATOMOVIL_H
#define CONTRATOMOVIL_H

#include <Contrato.h>


class ContratoMovil : public Contrato
{
    public:
        ContratoMovil();
        ContratoMovil(long int dni, Fecha f, float preciMin, int mins, char* Nac);
        ContratoMovil(const ContratoMovil &c);
        virtual ~ContratoMovil();

        void setMinutosHablados(int val) {mins=val;}
        int getMinutosHablados() const {return mins;}
        void setNacionalidad(char* Nac) {strcpy(Nacionalidad, Nac);}
        char* getNacionalidad() const {return Nacionalidad;}
        void setPrecioMinuto( float val ) {precioMin = val;}
        float getPrecioMinuto() const {return precioMin;}

        void ver();
        float factura();

    protected:

    private:
        float precioMin;
        int mins;
        char* Nacionalidad;
};

ostream& operator<<(ostream& o, ContratoMovil &c);

#endif // CONTRATOMOVIL_H
