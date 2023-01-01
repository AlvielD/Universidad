#ifndef MULTICONJUNTO_H
#define MULTICONJUNTO_H

#include "Persona.h"

template<typename T>
class Multiconjunto
{
    public:
        Multiconjunto();
        bool esVacio() const;
        int cardinalidad() const;
        void anade(const T& objeto);
        void elimina(const T& objeto);
        bool pertenece(const T& objeto) const;

    private:
        T c[100];
        int num;
};

#endif // MULTICONJUNTO_H
