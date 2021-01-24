#ifndef FECHA_H
#define FECHA_H
#include <iostream>

using namespace std;

class Fecha
{
    public:
        Fecha();
        Fecha(int dia, int mes, int agno);
        virtual ~Fecha();

        int getDia() const {return Dia; }
        int getMes() const {return Mes; }
        int getAnio() const {return Anio; }
        void setFecha(int Dia, int Mes, int Anio);
        void ver() const;
        void ver();
        bool bisiesto() const;

        Fecha operator++();
        Fecha operator++(int flag);
        Fecha operator+(int a) const;
        bool operator!=(Fecha f) const;


    protected:

    private:
        int Dia;
        int Mes;
        int Anio;
};

Fecha operator+(int a, const Fecha &F);
ostream& operator<<(ostream& o, const Fecha &f);

#endif // FECHA_H
