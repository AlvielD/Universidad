#include "ContratoMovil.h"

ContratoMovil::ContratoMovil()
{
    //ctor
}

ContratoMovil::ContratoMovil(long int dni, Fecha f, float precioMin, int mins, char* Nac): Contrato(dni, f)
{
    this->precioMin=precioMin;
    this->mins=mins;
    this->Nacionalidad = new char[strlen(Nac)+1];
    strcpy(this->Nacionalidad, Nac);
}

ContratoMovil::ContratoMovil(const ContratoMovil &c): Contrato(c)
{
    this->precioMin=c.getPrecioMinuto();
    this->mins=c.getMinutosHablados();
    this->Nacionalidad = new char[strlen(c.getNacionalidad()+1)];
    strcpy(this->Nacionalidad, c.getNacionalidad());
}

ContratoMovil::~ContratoMovil()
{
    delete []Nacionalidad;
}

void ContratoMovil::ver()
{
    Contrato::ver();
    cout << " " << this->mins << "m, " << this->Nacionalidad << " " << this->precioMin;
}

float ContratoMovil::factura()
{
    return (this->mins*this->precioMin);
}

ostream& operator<<(ostream& o, ContratoMovil &c)
{
    o << (Contrato&)c << " " << c.getMinutosHablados() << "m, " << c.getNacionalidad() << " " << c.getPrecioMinuto() << " - " << c.factura();
}
