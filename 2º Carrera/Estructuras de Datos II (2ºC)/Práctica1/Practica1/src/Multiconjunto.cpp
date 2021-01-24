#include "Multiconjunto.h"

template<typename T>
Multiconjunto<T>::Multiconjunto()
{
    num=0;
}

template<typename T>
bool Multiconjunto<T>::esVacio() const
{
    return num==0;
}

template<typename T>
int Multiconjunto<T>::cardinalidad() const
{
    return num;
}

template<typename T>
void Multiconjunto<T>::anade(const T& objeto)
{
    //Comprobar si el array está lleno para agrandarlo
    c[num++]=objeto;
}

template<typename T>
void Multiconjunto<T>::elimina(const T& objeto)
{
    //Recorre el array buscando ocurrencias del objeto
    for(int i=0; i<num; i++)
    {
        if(c[i]==objeto)//Debemos reducir en 1 el número de elementos por cada repetición de este if
        {
            //Bucle que remplaza el elemento de la posición i por el elemento de la posición siguiente
            for(int j=i; j<num; j++)
            {
                c[j]=c[j+1];
            }

            num--;
        }
    }
}

template<typename T>
bool Multiconjunto<T>::pertenece(const T& objeto) const
{
    bool existe=false;
    int i=0;

    while(existe==false && i<num)
    {
        if(c[i]==objeto)
            existe=true;
        else
            i++;
    }

    return existe;
}

//INSTANCIAS

template class Multiconjunto<int>;
template class Multiconjunto<char>;
template class Multiconjunto<Persona>;
