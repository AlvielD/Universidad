#ifndef EXCEP_EJERCICIO6_H
#define EXCEP_EJERCICIO6_H

#include "excepcion.h"

using namespace std;

class NoHaySiguienteMayor: public Excepcion {
    public:
        NoHaySiguienteMayor(): Excepcion("ERROR: No existe un valor que sea mayor al pasado por parametro"){};
};

#endif // EXCEP_EJERCICIO6_H
