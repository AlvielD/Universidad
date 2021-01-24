#ifndef PERSONA_H
#define PERSONA_H

#include<string>

using namespace std;

class Persona
{
    public:
        Persona(const string& n = "", int e = 0);
        const string& getNombre() const { return nombre; }
        void setNombre(const string& n) { nombre = n; }
        int getEdad() const { return edad; }
        void setEdad(int e) { edad = e; }
        bool operator==(const Persona& p) const;

    private:
        string nombre;
        int edad;
};

#endif // PERSONA_H
