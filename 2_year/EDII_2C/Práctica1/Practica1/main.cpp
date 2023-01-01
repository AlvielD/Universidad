#include <iostream>
#include "Multiconjunto.h"

using namespace std;

int main()
{
    cout << "//PARA CHAR//" << endl;
    Multiconjunto<char> m;
    if(m.esVacio())
        cout << "El conjunto es vacio" << endl;
    else
        cout << "El conjunto no es vacio" << endl;

    m.anade('a');
    m.anade('l');
    m.anade('v');
    m.anade('a');
    m.anade('r');
    m.anade('o');

    cout << "La letra \"a\" pertenece al conjunto?: " << endl;
    cout << m.pertenece('a') << endl;

    m.elimina('a');

    cout << "y ahora?: " << endl;
    cout << m.pertenece('a') << endl;

    cout << "La cardinalidad debe ser 4, no? --> " << m.cardinalidad() << endl << endl;

    //Probamos ahora con números

    cout << "//PARA INT//" << endl;
    Multiconjunto<int> n;
    if(n.esVacio())
        cout << "El conjunto es vacio" << endl;
    else
        cout << "El conjunto no es vacio" << endl;

    n.anade(1);
    n.anade(2);
    n.anade(3);
    n.anade(4);
    n.anade(5);
    n.anade(6);

    cout << "El numero \"4\" pertenece al conjunto?: " << endl;
    cout << n.pertenece(4) << endl;

    n.elimina(4);

    cout << "y ahora?: " << endl;
    cout << n.pertenece(4) << endl;

    cout << "La cardinalidad debe ser 5, no? --> " << n.cardinalidad() << endl;

    //Probamos ahora con la clase persona

    cout << "//PARA PERSONA//" << endl;
    Multiconjunto<Persona> p;
    if(m.esVacio())
        cout << "El conjunto es vacio" << endl;
    else
        cout << "El conjunto no es vacio" << endl;

    Persona a("alvaro", 19);
    Persona b("daniel", 21);
    Persona c("eduduardo", 21);
    Persona d("alvaro", 19);

    p.anade(a);
    p.anade(b);
    p.anade(c);
    p.anade(d);

    cout << "La persona de nombre alvaro y edad 19 pertenece al conjunto?: " << endl;
    cout << p.pertenece(a) << endl;

    p.elimina(a);

    cout << "y ahora?: " << endl;
    cout << p.pertenece(a) << endl;

    cout << "La cardinalidad debe ser 2, no? --> " << p.cardinalidad() << endl << endl;

    return 0;
}
