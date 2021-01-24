#include "Contrato.h"

int Contrato::Contratos=0;

Contrato::Contrato()
{
    //ctor
}

Contrato::Contrato(long int dni, Fecha f): fechaContrato(f)
{
    this->SetContratos(this->GetContratos()+1); //Aumentamos la variable de clase que lleva la cuenta
    this->SetidContrato(this->GetContratos()); // Seteamos la id en base a la variable estática
    this->dniContrato = dni;
}

Contrato::Contrato(const Contrato &c)
{
    this->SetContratos(this->GetContratos()+1);
    this->dniContrato=c.getDniContrato();
    this->idContrato=this->GetContratos();
    this->fechaContrato=c.getFechaContrato();
}

Contrato::~Contrato()
{
    //dtor
}

void Contrato::ver()
{
    cout << this->getDniContrato() << " (" << this->getIdContrato() << " - ";
    this->getFechaContrato().ver();
    cout << ")";
}

ostream& operator<<(ostream& o, Contrato &c)
{
    o << c.getDniContrato() << " (" << c.getIdContrato() << " - " << c.getFechaContrato() << ")";

    return o;
}
