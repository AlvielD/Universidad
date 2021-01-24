#include "ContratoTP.h"

int ContratoTP::LimiteMinutos=300;
float ContratoTP::Precio=10;

ContratoTP::ContratoTP()
{
    //ctor
}

ContratoTP::ContratoTP(int dni, Fecha f, int mins): Contrato(dni, f)
{
    this->minutosHablados=mins;
}

ContratoTP::ContratoTP(const ContratoTP &c): Contrato(c)
{
    this->minutosHablados=c.getMinutosHablados();
}

ContratoTP::~ContratoTP()
{
    //dtor
}

void ContratoTP::ver()
{
    Contrato::ver(); //<----- Invoca al método del padre y luego se ejecuta lo del hijo
    cout << " " << this->minutosHablados << "m, " << ContratoTP::getLimiteMinutos() << "(" << ContratoTP::getPrecio() << ")";
}

float ContratoTP::factura()
{
    float total;
    total = this->getPrecio();
    if(this->minutosHablados > this->getLimiteMinutos())
        total+=((this->minutosHablados-this->getLimiteMinutos())*0.15);

    return total;
}

void ContratoTP::setTarifaPlana(int mins, int prec)
{
    ContratoTP::setLimiteMinutos(mins);
    ContratoTP::setPrecio(prec);
}

ostream& operator<<(ostream& o, ContratoTP &c)
{
    o << (Contrato&)c << " " << c.getMinutosHablados() << "m, " << ContratoTP::getLimiteMinutos() << "(" << ContratoTP::getPrecio() << ") - " << c.factura();

    return o;
}
