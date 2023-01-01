#include "Fecha.h"
#ifndef CLIENTE_H
#define CLIENTE_H

using namespace std;


class Cliente
{
    public:
        Cliente();
        Cliente(long dni, char *nombre, Fecha fechaAlta);
        virtual ~Cliente();
        void setNombre(char *nombre);
        void setFecha(Fecha f);
        long getDni() const { return this->dni; }
        char* getNombre() const { return this->nombre; }
        Fecha getFecha() const { return this->fechaAlta; }

        bool operator==(Cliente c) const;
        Cliente& operator=(const Cliente& p);


    protected:

    private:
        long dni;
        char *nombre;
        Fecha fechaAlta;
};

ostream& operator<<(ostream& o, const Cliente &c);

#endif // CLIENTE_H
