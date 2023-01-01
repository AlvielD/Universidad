#include "Fecha.h"
#include <iostream>
#include <string.h>

using namespace std;

Fecha::Fecha()
{
    //ctor
}

Fecha::Fecha(int dia, int mes, int agno){

    setFecha(dia, mes, agno);
}

Fecha::~Fecha()
{
    //dtor
}


void Fecha::setFecha(int dia, int mes, int agno)
{
//Checkea si el año es bisiesto o no
//-------------------------------------------------------------------------------------------
    bool bisiesto = false;
    if ((agno % 400 == 0) || (agno%4 == 0 && agno % 100 != 0))
        bisiesto = true;
//-------------------------------------------------------------------------------------------


//Corrige la fecha (Debe haber alguna manera más sencilla de hacerlo...)
//-------------------------------------------------------------------------------------------
    if(dia<1)
        dia=1;

    switch(mes)
    {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:{
                if(dia>31)
                    dia=31;
            }
            break;
    case 4:
    case 6:
    case 9:
    case 11:{
                if(dia>30)
                    dia=30;
            }
            break;
    case 2:{
                if(bisiesto==true && dia>29)
                    dia=29;
                else
                    if(bisiesto==false && dia>28)
                        dia=28;
            }
            break;
    default:{
                if(mes>12)
                        mes=12;
                else
                    if(mes<1)
                        mes=1;
            }
            break;
    }
    this->Anio=agno;
    this->Mes=mes;
    this->Dia=dia;

//-------------------------------------------------------------------------------------------

}

void Fecha::ver()const {

    if(this->Dia < 10)
        cout << "0";
    cout << this->Dia << "/";
    if(this->Mes < 10)
        cout << "0";
    cout << this->Mes << "/" << this->Anio;
}

void Fecha::ver(){

    if(this->Dia < 10)
        cout << "0";
    cout << this->Dia << "/";
    if(this->Mes < 10)
        cout << "0";
    cout << this->Mes << "/" << this->Anio;
}

bool Fecha::bisiesto() const{
    return ((this->Anio % 400 == 0) || (this->Anio%4 == 0 && this->Anio % 100 != 0));
}

Fecha Fecha::operator++(){
    *this+1;
    return *this;
}

Fecha Fecha::operator++(int flag){
    Fecha copia(*this);
    *this+1;
    return copia;
}

Fecha Fecha::operator+(int a) const
{
    Fecha cop(*this);
    bool bisiesto = false;
    if ((cop.Anio % 400 == 0) || (cop.Anio%4 == 0 && cop.Anio % 100 != 0))
        bisiesto = true;

    cop.Dia+=a;

    switch(cop.Mes)
    {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:{
                if(cop.Dia>31)
                {
                    cop.Dia-=31;
                    cop.Mes++;
                }
            }
            break;
    case 4:
    case 6:
    case 9:
    case 11:{
                if(cop.Dia>30)
                {
                    cop.Dia-=30;
                    cop.Mes++;
                }
            }
            break;
    case 2:{
                if(bisiesto==true && cop.Dia>29)
                {
                    cop.Dia-=29;
                    cop.Mes++;
                }
                else
                    if(bisiesto==false && cop.Dia>28)
                    {
                        cop.Dia-=28;
                        cop.Mes++;
                    }
            }
            break;
    }
    if(cop.Mes>12)
    {
        cop.Mes-=12;
        cop.Anio++;
    }

    return cop;
}

Fecha operator+(int a, const Fecha &F){
//Por propiedad conmutativa de la suma, le damos la vuelta y ya tenemos implementado el operador Fecha+int
    return F+a;
}

bool Fecha::operator!=(Fecha f) const
{
    bool diferentes=false;

    if(this->Anio!=f.getAnio())
        diferentes=true;
    if(this->Mes!=f.getMes())
        diferentes=true;
    if(this->Dia!=f.getDia())
        diferentes=true;

    return diferentes;
}

ostream& operator<<(ostream &o, const Fecha &f)
{
    char AbrMes[3];

    switch(f.getMes())
    {
        case 1: strcpy(AbrMes, "ene");
                break;
        case 2: strcpy(AbrMes, "feb");
                break;
        case 3: strcpy(AbrMes, "mar");
                break;
        case 4: strcpy(AbrMes, "abr");
                break;
        case 5: strcpy(AbrMes, "may");
                break;
        case 6: strcpy(AbrMes, "jun");
                break;
        case 7: strcpy(AbrMes, "jul");
                break;
        case 8: strcpy(AbrMes, "ago");
                break;
        case 9: strcpy(AbrMes, "sep");
                break;
        case 10:strcpy(AbrMes, "oct");
                break;
        case 11:strcpy(AbrMes, "nov");
                break;
        case 12:strcpy(AbrMes, "dic");
                break;
    }
    o << (f.getDia()<10?"0":"") << f.getDia() << " " << AbrMes << " " << f.getAnio();

    return o;
}
