#include "Cliente.h"
#include <string.h>

using namespace std;

Cliente::Cliente()
{
    //ctor
}

Cliente::Cliente(long dni, char *nombre, Fecha fechaAlta):fechaAlta(fechaAlta)
{
    this->dni=dni;
    //Antes de usar la zona de memoria hay que reservarla con el tamaño de la cadena que nos pasan por parámetro + 1(carácter de fin de cadena)
    this->nombre = new char[strlen(nombre)+1];
    strcpy(this->nombre, nombre);//Si *nombre fuese const, tendríamos que inicializarlo en la zona de inicializadores
}

Cliente::~Cliente()
{
    delete []nombre;
}

void Cliente::setNombre(char *nombre)
{
    strcpy(this->nombre, nombre);
}

void Cliente::setFecha(Fecha f)
{
    this->fechaAlta.setFecha(f.getDia(), f.getMes(), f.getAnio());
}

bool Cliente::operator==(Cliente c) const
{
    bool iguales=true;
    if(this->dni!=c.getDni())
        iguales=false;
    if(this->fechaAlta!=c.getFecha())
        iguales=false;
    if(strcmp(this->nombre, c.getNombre())!=0)
        iguales=false;

    return iguales;
}

ostream& operator<<(ostream& o, const Cliente &c)
{
    o << c.getNombre() << " (" << c.getDni() << " - " << c.getFecha() << ")";

    return o;
}

Cliente& Cliente::operator=(const Cliente& p)
{
    this->dni=p.dni;
    this->fechaAlta=p.fechaAlta;
    delete [] this->nombre;
    this->nombre = new char[strlen(p.nombre)+1];
    strcpy(this->nombre, p.nombre);
}
