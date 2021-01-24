#include <iostream>
#include <cstdlib>
#include <queue>
#include <string>
#include "arbin.h"
#include "abb.h"
#include "excepcion.h"
#include "excep_ejercicio6.h"

// Recorridos

template <typename T>
void inorden(const Arbin<T>& a, const typename Arbin<T>::Iterador& r) {
    if (!r.arbolVacio()) {
        inorden(a, a.subIzq(r));
        cout << r.observar() << " ";
        inorden(a, a.subDer(r));
    }
}

template <typename T>
void preorden(const Arbin<T>& a, const typename Arbin<T>::Iterador& r) {
    if (!r.arbolVacio()) {
        cout << r.observar() << " ";
        preorden(a, a.subIzq(r));
        preorden(a, a.subDer(r));
    }
}

template <typename T>
void postorden(const Arbin<T>& a, const typename Arbin<T>::Iterador& r) {
    if (!r.arbolVacio()) {
        postorden(a, a.subIzq(r));
        postorden(a, a.subDer(r));
        cout << r.observar() << " ";
    }
}

template <typename T>
void anchura(const Arbin<T>& a) {
    if (!a.esVacio()) {
        queue<typename Arbin<T>::Iterador> c;
        typename Arbin<T>::Iterador ic = a.getRaiz();
        c.push(ic);
        while (!c.empty()) {
             ic = c.front();
             c.pop();
             cout << ic.observar() << " ";
             if (!a.subIzq(ic).arbolVacio())
                c.push(a.subIzq(ic));
             if (!a.subDer(ic).arbolVacio())
                c.push(a.subDer(ic));
        }
    }
}


/***************************************************************************/
/****************************** EJERCICIOS *********************************/
/***************************************************************************/
//Ejercicio 1

template<typename T>
int numHojas(const Arbin<T>& a)
{
    return numHojas(a, a.getRaiz());
}

template<typename T>
int numHojas(const Arbin<T>& a, const typename Arbin<T>::Iterador& r)
{
    if(r.arbolVacio())
        return 0;
    else
        if(a.subIzq(r).arbolVacio() && a.subDer(r).arbolVacio())
            return 1;
        else
            return numHojas(a, a.subIzq(r)) + numHojas(a, a.subDer(r));
}



/****************************************************************************/
//Ejercicio 2

template<typename T>
const Arbin<T> simetrico(const Arbin<T>& a)
{
    return simetrico(a, a.getRaiz());
}

template<typename T>
const Arbin<T> simetrico(const Arbin<T>& a, const typename Arbin<T>::Iterador& r)
{
    if(r.arbolVacio())
        return Arbin<T>();
    else
        return Arbin<T>(r.observar(), simetrico(a, a.subDer(r)), simetrico(a, a.subIzq(r)));
}



/****************************************************************************/
//Ejercicio 3

template <typename T>
void recorridoZigzag(const Arbin<T>& a, char sentido)
{
    recorridoZigzag(a, a.getRaiz(), sentido);
}

template <typename T>
void recorridoZigzag(const Arbin<T>& a, const typename Arbin<T>::Iterador& r,char sentido)
{
    if(!r.arbolVacio())
    {
        cout << r.observar() << " ";
        if(sentido=='I')
            recorridoZigzag(a, a.subIzq(r), 'D');
        else if(sentido=='D')
                recorridoZigzag(a, a.subDer(r), 'I');
    }
}


/******************************************************************************/
//Ejercicio 4

template<typename T>
bool compensado(const Arbin<T>& a)
{
    return compensado(a, a.getRaiz());
}

template<typename T>
bool compensado(const Arbin<T>& a, const typename Arbin<T>::Iterador& r)
{
    if(r.arbolVacio() || (a.subIzq(r).arbolVacio() && a.subDer(r).arbolVacio()))
        return true;
    else
        if((abs(cuentaNodos(a, a.subIzq(r)) - cuentaNodos(a, a.subDer(r))) <= 1) && compensado(a, a.subIzq(r))==true && compensado(a, a.subDer(r))==true)
            return true;
        else
            return false;
}

template<typename T>
int cuentaNodos(const Arbin<T>& a, const typename Arbin<T>::Iterador)
{
    int nodos=0;
    cuentaNodos(a, a.getRaiz(), nodos);
    return nodos;
}

template<typename T>
int cuentaNodos(const Arbin<T>& a, const typename Arbin<T>::Iterador& r, int &nodos)
{
    if (!r.arbolVacio())
    {
        nodos++;
        cuentaNodos(a, a.subIzq(r), nodos);
        cuentaNodos(a, a.subDer(r), nodos);
    }

    return nodos;
}

/*****************************************************************************/
//Ejercicio 5

template<typename T>
void palabras(const Arbin<T>& a)
{
    string s="";
    palabras(a, a.getRaiz(), s);
}

template<typename T>
void palabras(const Arbin<T>& a, const typename Arbin<T>::Iterador& r, string s)
{
    if(r.arbolVacio())
        ;
    else
    {
        s = s + r.observar();
        if(a.subIzq(r).arbolVacio() && a.subDer(r).arbolVacio())
            cout << s << endl;
        else
        {
            palabras(a, a.subIzq(r), s);
            palabras(a, a.subDer(r), s);
        }
    }
}


/******************************************************************************/
//Ejercicio 6

template<typename T>
int siguienteMayor(const ABB<T>& a, const int& x) throw (NoHaySiguienteMayor)
{
    int m=maximo(a);

    if(m<=x)
        throw NoHaySiguienteMayor();

    return siguienteMayor(a, a.getRaiz(), x, m);
}

template<typename T>
int siguienteMayor(const ABB<T>& a, const typename ABB<T>::Iterador& r, const int& x, int& m)
{
    if(r.arbolVacio())
        return m;
    else
        if(r.observar()<=x)
            return siguienteMayor(a, a.subDer(r), x, m);
        else
            if(r.observar()>x)
            {
                if(r.observar()<m)
                    m=r.observar();

                return siguienteMayor(a, a.subIzq(r), x, m);
            }
}

template<typename T>
int maximo(const ABB<T>& a)
{
    int m=0;

    maximo(a, a.getRaiz(), m);

    return m;
}

template<typename T>
void maximo(const ABB<T>& a, const typename ABB<T>::Iterador& r, int& m)
{
    if(!r.arbolVacio())
    {
        m=r.observar();
        maximo(a, a.subDer(r), m);
    }
}
/******************************************************************************/
//Ejercicio 7

template<typename T>
int posicionInorden(const ABB<T>& a, const int& elem)
{
    int i=0;

    return posicionInorden(a, a.getRaiz(), i, elem);
}

//SI POS != 0 --> HEMOS ENCONTRADO EL ELEMENTO

template<typename T>
int posicionInorden(const ABB<T>& a, const typename Arbin<T>::Iterador& r, int& i, const int& elem)
{
    int pos = 0;

    if(!r.arbolVacio())
    {
        pos = posicionInorden(a, a.subIzq(r), i, elem);

        if(pos==0) //Si ya se ha encontrado el elemento no se sigue buscando
        {
            i++;
            if(r.observar()==elem)
                pos = i; //Cambiar el valor de pos significa que hemos encontrado el elemento
            else   //Si ya se ha encontrado el elemento no se sigue buscando
            {
                pos = posicionInorden(a, a.subDer(r), i, elem);
            }
        }
    }

    return pos;
}


/******************************************************************************/
//Ejercicio 8
template<typename T>
bool haySumaCamino(const Arbin<T>& a, int suma)
{
    bool existe=false;
    int acum=0;

    haySumaCamino(a, a.getRaiz(), suma, existe, acum);

    return existe;
}

template<typename T>
void haySumaCamino(const Arbin<T>& a, const typename Arbin<T>::Iterador& r, const int& suma, bool& existe, int acum)
{
    if(!r.arbolVacio() && existe==false)
    {
        acum+=r.observar();
        if(a.subIzq(r).arbolVacio() && a.subDer(r).arbolVacio()) // Si se cumple esta condición entonces estamos en una hoja
        {
            if(acum==suma)
            {
                existe=true;
            }
            acum=0;
        }

        if(existe==false)
        {
            haySumaCamino(a, a.subIzq(r), suma, existe, acum);
            if(existe==false)
                haySumaCamino(a, a.subDer(r), suma, existe, acum);
        }

    }
}

/****************************************************************************/
/****************************************************************************/
int main(int argc, char *argv[])
{
    Arbin<char> A('t', Arbin<char>('m', Arbin<char>(),
                                        Arbin<char>('f', Arbin<char>(), Arbin<char>())),
                       Arbin<char>('k', Arbin<char>('d', Arbin<char>(), Arbin<char>()),
                                        Arbin<char>()));

    Arbin<char> B('t', Arbin<char>('n', Arbin<char>(),
                                        Arbin<char>('d', Arbin<char>('e', Arbin<char>(), Arbin<char>()),
                                                         Arbin<char>())),
                       Arbin<char>('m', Arbin<char>('f', Arbin<char>(), Arbin<char>()),
                                        Arbin<char>('n', Arbin<char>(), Arbin<char>())));

    Arbin<char> D('t', Arbin<char>('k', Arbin<char>('d', Arbin<char>(), Arbin<char>()),
                                        Arbin<char>()),
                       Arbin<char>('m', Arbin<char>(),
                                        Arbin<char>('f', Arbin<char>(), Arbin<char>())));

    Arbin<char> E('o', Arbin<char>('r', Arbin<char>(),
                                        Arbin<char>('o', Arbin<char>(), Arbin<char>())),
                       Arbin<char>('l', Arbin<char>('a', Arbin<char>(), Arbin<char>()),
                                        Arbin<char>('e', Arbin<char>(), Arbin<char>())));

    Arbin<int> F(2, Arbin<int>(7, Arbin<int>(2, Arbin<int>(), Arbin<int>()),
                                  Arbin<int>(6, Arbin<int>(5, Arbin<int>(), Arbin<int>()),
                                                Arbin<int>(11, Arbin<int>(), Arbin<int>()))),
                    Arbin<int>(5, Arbin<int>(),
                                  Arbin<int>(9, Arbin<int>(),
                                                  Arbin<int>(4, Arbin<int>(), Arbin<int>()))));

    ABB<int> BB6, BB7;


    // NUMERO HOJAS //
    cout << "Num. hojas del arbol B: " << numHojas(B) << endl;
    cout << "Num. hojas del arbol E: " << numHojas(E) << endl << endl;



    // COPIA SIMETRICA //
    Arbin<char> C = simetrico(B);
    cout << "Recorrido en inorden del arbol B: " << endl;
    inorden(B, B.getRaiz());
    cout << endl << "Recorrido en inorden del arbol C: " << endl;
    inorden(C, C.getRaiz());
    cout << endl << endl;


    // RECORRIDO EN ZIG-ZAG //
    cout << "Recorrido en zigzag I de B:\n";
    recorridoZigzag(B, 'I');
    cout << endl;
    cout << "Recorrido en zigzag D de C:\n";
    recorridoZigzag(C, 'D');
    cout << endl << endl;


    // COMPENSADO //
    cout << "Esta A compensado?:";
    cout << (compensado(A) ? " SI" : " NO") << endl;
    cout << "Esta B compensado?:";
    cout << (compensado(B) ? " SI" : " NO") << endl << endl;

    // PALABRAS DE UN ARBOL //
    cout << "PALABRAS DE A:\n";
    palabras(E);
    cout << "PALABRAS DE B:\n";
    palabras(B);
    cout << endl;

    // SIGUIENTE MAYOR
    BB6.insertar(8); BB6.insertar(3); BB6.insertar(10); BB6.insertar(1); BB6.insertar(6);
    BB6.insertar(14); BB6.insertar(4); BB6.insertar(7); BB6.insertar(13);
    try
    {
        cout << "Siguiente mayor en BB6 de 5: " << siguienteMayor(BB6, 5) << endl;
        cout << "Siguiente mayor en BB6 de 8: " << siguienteMayor(BB6, 8) << endl;
        cout << "Siguiente mayor en BB6 de 13: " << siguienteMayor(BB6, 13) << endl;
        cout << "Siguiente mayor en BB6 de 14: " << siguienteMayor(BB6, 14) << endl;
    }
    catch(const NoHaySiguienteMayor& e)
    {
        cout << e.Mensaje() << endl << endl;
    }

    // POSICION INORDEN //
    BB7.insertar(5); BB7.insertar(1); BB7.insertar(3); BB7.insertar(8); BB7.insertar(6);
    cout << "Posicion Inorden en BB7 de 3: ";
    cout << posicionInorden(BB7, 3);
    cout << endl << "Posicion Inorden en BB7 de 8: ";
    cout << posicionInorden(BB7, 8);
    cout << endl << "Posicion Inorden en BB7 de 7: ";
    cout << posicionInorden(BB7, 7);
    cout << endl << endl;

    // SUMA CAMINO
    cout << "Hay un camino de suma 26 en F?:";
    cout << (haySumaCamino(F, 26) ? " SI" : " NO") << endl;
    cout << "Hay un camino de suma 9 en F?:";
    cout << (haySumaCamino(F, 9) ? " SI" : " NO") << endl;

    system("PAUSE");
    return 0;
}
