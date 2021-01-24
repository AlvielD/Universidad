#include "Persona.h"


Persona::Persona(const string& n , int e)
{
    this->nombre=n;
    this->edad=e;
}

bool Persona::operator==(const Persona& p) const
{
    return((this->nombre==p.nombre) && (p.edad==this->edad));
}
